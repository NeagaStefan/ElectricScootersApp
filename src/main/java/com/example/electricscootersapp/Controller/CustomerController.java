package com.example.electricscootersapp.Controller;

import com.example.electricscootersapp.Entity.Customer;
import com.example.electricscootersapp.Entity.CustomerDto;
import com.example.electricscootersapp.Entity.HistoryDto;
import com.example.electricscootersapp.Service.CustomerService;
import com.example.electricscootersapp.Service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CustomerController {
    private final CustomerService customerService;
    private final HistoryService historyService;

    @Autowired
    private CustomerController(CustomerService customerService, HistoryService historyService){
        this.customerService = customerService;
        this.historyService = historyService;
    }

    //Get methods

    @GetMapping("/customers")
        public List<CustomerDto> showAllCustomers(){
            return customerService.showAllCustomers();
        }

        @GetMapping("/customer/")
        public List<HistoryDto> showCustomerHistory(@RequestParam("userName") String userName){
        return  historyService.showCustomerHistory(userName);
        }

        //Post methods

    @PostMapping("/customers")
    public Customer saveCustomer(@Valid @RequestBody CustomerDto customerDto){
        return customerService.saveCustomer(customerDto);
    }

    @PostMapping("/customers/")
    public Customer updateCustomer(@Valid @RequestParam("customerId") Long customerId,@RequestBody CustomerDto customerDto){
        return customerService.updateCustomer(customerId,customerDto);
    }

    @PutMapping("/customers/")
    public void updatePassword(@RequestParam("userName")String userName,@RequestParam("newPass")String newPass){
        customerService.updatePassword(userName,newPass);
    }
    @PutMapping("/customers/start")
    public String startRenting(@RequestParam("userName")String userName,@RequestParam("scooterId") Long scooterId){
        customerService.startRenting(userName,scooterId);
        return "The rental has started";

    }
    @PutMapping("/customers/stop")
    public String stopRenting(@RequestParam("userName")String userName,@RequestParam("scooterId") Long scooterId,@RequestParam("newLocation")String newLocation){
        customerService.stopRenting(userName,scooterId,newLocation);
        return "The rental has stopped";
    }

    @DeleteMapping("/customers/")
    public void deleteCustomer(@RequestParam ("customerId") Long customerId){
        customerService.deleteById(customerId);
    }
}
