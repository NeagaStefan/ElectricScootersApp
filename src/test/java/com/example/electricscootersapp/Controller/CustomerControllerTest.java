package com.example.electricscootersapp.Controller;

import com.example.electricscootersapp.Entity.Customer;
import com.example.electricscootersapp.Entity.CustomerDto;
import com.example.electricscootersapp.Entity.HistoryDto;
import com.example.electricscootersapp.Service.CustomerService;
import com.example.electricscootersapp.Service.HistoryService;
import com.example.electricscootersapp.Service.ScooterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class CustomerControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    ObjectMapper mapper;
    private CustomerDto customerDto;
    private Customer customer;
    private HistoryDto historyDto;

    @MockBean
    private CustomerService customerService;
    @MockBean
    private HistoryService historyService;
    @MockBean
    private ScooterService scooterService;
    @BeforeEach
    void setUp() {
        customerDto= new CustomerDto(2L, "Stefan","Neaga","neagstefan99@yahoo.com","neagaStefan","parolaSimpla",false,"4217-0213-5464-4888","125","02/23");
        customer = new Customer(2L, "Stefan","Neaga","neagstefan99@yahoo.com","neagaStefan","parolaSimpla",false,"4217-0213-5464-4888","125","02/23");
        historyDto = new HistoryDto(2L,2L,"neagaStefan",0,3.26f,null,3.76f,null);
    }


    @Test
    void showAllCustomers() throws Exception{
        Mockito.when(customerService.showAllCustomers()).thenReturn(new ArrayList<>(List.of(customerDto)));
        String mvcResultAsString =mvc.perform(MockMvcRequestBuilders.get("/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List response = new ObjectMapper().readValue(mvcResultAsString,List.class);
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
    }

    @Test
    void showCustomerHistory() throws Exception{
        Mockito.when(historyService.showCustomerHistory(ArgumentMatchers.anyString())).thenReturn(new ArrayList<>(List.of(historyDto)));
        String mvcResultAsString =mvc.perform(MockMvcRequestBuilders.get("/customer/").param("userName","neagaStefan")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List response = new ObjectMapper().readValue(mvcResultAsString,List.class);
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
    }

    @Test
    void saveCustomer() throws Exception {
        Mockito.when(customerService.saveCustomer(Mockito.any(CustomerDto.class))).thenReturn(customer);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(customer)
                                .getBytes(StandardCharsets.UTF_8))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assertions.assertNotNull(result);
    }

    @Test
    void updateCustomer() throws Exception{
        Mockito.when(customerService.updateCustomer(ArgumentMatchers.any(),Mockito.any(CustomerDto.class))).thenReturn(customer);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/customers/").param("customerId","2")
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(customer)
                                .getBytes(StandardCharsets.UTF_8))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assertions.assertNotNull(result);
    }

    @Test
    void updatePassword() throws Exception{
        Mockito.doNothing().when(customerService).updatePassword(ArgumentMatchers.anyString(),ArgumentMatchers.anyString());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/customers/")
                        .param("userName","neagaStefan").param("newPass","parolaNoua")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void startRenting() throws Exception{
        Mockito.doNothing().when(customerService).startRenting(ArgumentMatchers.anyString(),ArgumentMatchers.anyLong());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/customers/start")
                        .param("userName","neagaStefan").param("scooterId","3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void stopRenting() throws Exception {
        Mockito.doNothing().when(customerService).stopRenting(ArgumentMatchers.anyString(),ArgumentMatchers.anyLong(),ArgumentMatchers.anyString());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/customers/start")
                        .param("userName","neagaStefan").param("scooterId","3").param("newLocation","Bartolomeu")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deleteCustomer() throws Exception {
        Mockito.doNothing().when(customerService).deleteById(ArgumentMatchers.any());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/customers/")
                        .param("customerId","2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}