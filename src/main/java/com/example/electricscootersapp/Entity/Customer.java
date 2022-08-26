package com.example.electricscootersapp.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @SequenceGenerator(
            name="customer_sequence",
            sequenceName ="customer_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_sequence"
    )
    private Long customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private Boolean deleted;
    private String cardNumber;
    private String csv;
    private String expireDate;


}
