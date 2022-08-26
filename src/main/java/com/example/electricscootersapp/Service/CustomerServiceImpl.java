package com.example.electricscootersapp.Service;

import com.example.electricscootersapp.Entity.Customer;
import com.example.electricscootersapp.Entity.CustomerDto;
import com.example.electricscootersapp.Entity.History;
import com.example.electricscootersapp.Entity.LocationEnum;
import com.example.electricscootersapp.Error.*;
import com.example.electricscootersapp.Repository.CustomerRepo;
import com.example.electricscootersapp.Repository.HistoryRepo;
import com.example.electricscootersapp.Repository.ScooterRepo;
import org.apache.commons.lang3.EnumUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Clock;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepo customerRepo;
    private final HistoryRepo historyRepo;

    private final ScooterRepo scooterRepo;
    private final ModelMapper modelMapper;
    private Long rentalId;
    private Boolean hasAlreadyStarted = false;


    @Autowired
    private CustomerServiceImpl(CustomerRepo customerRepo, ModelMapper modelMapper, HistoryRepo historyRepo, ScooterRepo scooterRepo) {
        this.customerRepo = customerRepo;
        this.modelMapper = modelMapper;
        this.historyRepo = historyRepo;
        this.scooterRepo = scooterRepo;
    }

    @Override
    public List<CustomerDto> showAllCustomers() {

        return convertListToDto(customerRepo.findAllCustomers());
    }

    @Override
    public Customer saveCustomer(CustomerDto customerDto) {
        Customer customerSaver = convertToEntity(customerDto);
        return customerRepo.save(customerSaver);
    }

    @Override
    public void deleteById(Long customerId) {
        customerRepo.softDelete(customerId);
    }

    @Override
    public Customer updateCustomer(Long customerId, CustomerDto customerDto) {

        Customer customerDb = customerRepo.findById(customerId).get();
        Customer customerRequest = convertToEntity(customerDto);
        if (Objects.nonNull(customerRequest.getFirstName()) && !"".equalsIgnoreCase(customerRequest.getFirstName())) {
            customerDb.setFirstName(customerRequest.getFirstName());
        }
        if (Objects.nonNull(customerRequest.getLastName()) && !"".equalsIgnoreCase(customerRequest.getLastName())) {
            customerDb.setLastName(customerRequest.getLastName());
        }
        if (Objects.nonNull(customerRequest.getEmail()) && !"".equalsIgnoreCase(customerRequest.getEmail())) {
            customerDb.setEmail(customerRequest.getEmail());
        }
        if (Objects.nonNull(customerRequest.getUserName()) && !"".equalsIgnoreCase(customerRequest.getUserName())) {
            customerDb.setUserName(customerRequest.getUserName());
        }
        if (Objects.nonNull(customerRequest.getPassword()) && !"".equalsIgnoreCase(customerRequest.getPassword())) {
            customerDb.setPassword(customerRequest.getPassword());
        }

        return customerRepo.save(customerDb);
    }

    @Override
    public void updatePassword(String userName, String newPass) {
        customerRepo.updatePassword(userName, newPass);
    }

    //When the customer starts the time it creates a reqistry in history
    //Sets the time and the price for the ride
    //We inject the price this way in case we modify the price of the scooter when a customer has it rented
    @Override
    public void startRenting(String userName, Long scooterId) {
      //This check is necessary only in postman, in a normal app the user can rent only a scooter

       if (hasAlreadyStarted == false) {
            hasAlreadyStarted = true;
            checkUserName(userName);
            checkDisponibility(scooterId);
            Float scooterPrice;
            Timestamp startingTime = Timestamp.from(Clock.systemUTC().instant());
            try {
                scooterPrice = scooterRepo.findById(scooterId).get().getPrice();

            } catch (NoSuchElementException exception) {
                throw new ScooterNotFoundException("The scooter is not available");
            }

            History history = new History();
            try {
                customerRepo.findByUserName(userName);

            } catch (NoSuchElementException exception) {
                throw new CustomerNotFoundException("The user does not exist. Try create an account first");
            }

            history.setUserName(userName);
            history.setScooterId(scooterId);
            history.setStartDate(startingTime);
            history.setPrice(scooterPrice);
            history.setStartLocation(scooterRepo.findById(scooterId).get().getPosition());
            this.rentalId = historyRepo.save(history).getRentalId();

            customerRepo.startRenting(startingTime, scooterId);
            scooterRepo.updateStatus(scooterId, "Taken");
            hasAlreadyStarted = true;
        } else {
            throw new RentalAlreadyStartedException("The rental process has already started. You need to stop first.");
        }


    }

    //In theory, the user does not see and cannot scan other than available scooters
    //But for this we must be sure that the scooter just available
    private String checkDisponibility(Long scooterId) {

        try {
            scooterRepo.findById(scooterId).get();

        } catch (NoSuchElementException ex) {
            throw new ScooterNotFoundException("The scooter does not exist");
        }
        if (scooterRepo.findById(scooterId).get().getStatus().equalsIgnoreCase("Available")) {
            return "The scooter is available";
        } else {
            throw new ScooterNotFoundException("The scooter is already taken");
        }
    }

    private void checkUserName(String userName) {
        try {
            customerRepo.findByUserName(userName).getUserName();

        } catch (NullPointerException ex) {
            throw new CustomerNotFoundException("The customer does not exist");
        }
    }

    //When the customer stops checks the time and searches for the start date to determine the time
    //then calculates the total, and it injects it into the database history
    @Override
    public void stopRenting(String userName, Long scooterId, String newLocation) throws LocationNotFoundException {
        History history = new History();
        Float totalPrice;
        String endLocation;
        Timestamp stopTime = Timestamp.from(Clock.systemUTC().instant());
        Timestamp startTime;
        if (rentalId != null) {
            startTime = historyRepo.findById(rentalId).get().getStartDate();
        } else {
            throw new StartNotFoundException("You need to start the rent first");
        }
        Float price = historyRepo.findById(rentalId).get().getPrice();

        history.setStopDate(stopTime);
        if (EnumUtils.isValidEnumIgnoreCase(LocationEnum.class, newLocation)) {
            endLocation = newLocation;
        } else {
            throw new LocationNotFoundException("The location is not permitted, look to leave the scooter in permitted areas");
        }
        Integer timeSpent = getDateDiff(startTime, stopTime);
        if (timeSpent < 1) {
            totalPrice = price * 2;
        } else {
            totalPrice = price * timeSpent;
        }
        historyRepo.saveById(rentalId, timeSpent, stopTime, totalPrice, endLocation);
        scooterRepo.updateStatus(scooterId, "Available");
        scooterRepo.updatePosition(scooterId,newLocation);
        rentalId = null;
        hasAlreadyStarted = false;
    }


    private CustomerDto convertToDto(Customer customer) {
        return (modelMapper.map(customer, CustomerDto.class));
    }

    private Customer convertToEntity(CustomerDto customerDto) {
        return (modelMapper.map(customerDto, Customer.class));
    }

    private List<CustomerDto> convertListToDto(List<Customer> customers){
        return customers.stream().map(customer -> modelMapper.map(customer, CustomerDto.class)).collect(Collectors.toList());
    }

    public static int getDateDiff(Timestamp startDate, Timestamp stopDate) {
        long differenceInMinutes = stopDate.getTime() - startDate.getTime();
        return (int) TimeUnit.MICROSECONDS.toMinutes(differenceInMinutes);

    }
}
