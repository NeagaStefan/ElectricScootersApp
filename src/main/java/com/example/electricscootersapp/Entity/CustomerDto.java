package com.example.electricscootersapp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private Long customerId;

    @NotBlank(message = "The first name must be filled")
    private String firstName;

    @NotBlank(message = "The last name must be filled")
    private String lastName;


    @NotBlank(message = "The email must be filled")
    @Email(message = "The email must be valid")
    private String email;

    @NotBlank(message = "The username must be filled")
    private String userName;


    @NotBlank(message = "The password must be filled")
    private String password;
    private Boolean deleted;


    @NotBlank(message = "You must insert the card number to crate an account")
    private String cardNumber;


    @NotBlank(message = "You must insert the card csv to crate an account")
    private String csv;


    @NotBlank(message = "You must insert the card expire date to crate an account")
    private String expireDate;

}
