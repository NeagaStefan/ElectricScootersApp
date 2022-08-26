package com.example.electricscootersapp.Service;

import com.example.electricscootersapp.Entity.Customer;
import com.example.electricscootersapp.Entity.CustomerDto;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> showAllCustomers();

    Customer saveCustomer(CustomerDto customerDto);

    void deleteById(Long customerId);

    Customer updateCustomer(Long customerId, CustomerDto customerDto);

    void updatePassword(String userName, String newPass);

    void startRenting(String userName,Long scooterId);

    void stopRenting(String userName, Long scooterId, String newLocation);
}
