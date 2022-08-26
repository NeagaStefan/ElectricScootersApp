package com.example.electricscootersapp.Controller;

import com.example.electricscootersapp.Entity.Scooter;
import com.example.electricscootersapp.Entity.ScooterDto;
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
class ScooterControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private ScooterService scooterService;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private HistoryService historyService;
    private ScooterDto scooterDto;
    private Scooter scooter;
    @BeforeEach
    void setUp() {
        scooterDto= new ScooterDto(2L, "F350","taken","Astra",75,  3.64F,null,null, 345.2F);
        scooter = new Scooter(2L, "F350","taken","Astra",75, (float) 3.64,null,null,(float) 345.2);    }

    @Test
    void showAllScooters() throws Exception{
        Mockito.when(scooterService.showAllScooters()).thenReturn(new ArrayList<>(List.of(scooterDto)));
        String mvcResultAsString = mvc.perform(MockMvcRequestBuilders.get("/scooters")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List response = new ObjectMapper().readValue(mvcResultAsString,List.class);
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
    }

    @Test
    void showScootersByStatus() throws Exception {
        Mockito.when(scooterService.showScootersByStatus(ArgumentMatchers.anyString())).thenReturn(new ArrayList<>(List.of(scooterDto)));
        String mvcResultAsString =mvc.perform(MockMvcRequestBuilders.get("/scooters/status").param("status", "taken")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List response = new ObjectMapper().readValue(mvcResultAsString,List.class);
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
    }

    @Test
    void showScootersByPosition() throws Exception {
        Mockito.when(scooterService.showScootersByPosition(ArgumentMatchers.anyString())).thenReturn(new ArrayList<>(List.of(scooterDto)));
        String mvcResultAsString =mvc.perform(MockMvcRequestBuilders.get("/scooters/position").param("position", "Bartolomeu")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List response = new ObjectMapper().readValue(mvcResultAsString,List.class);
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
    }

    @Test
    void showScootersByBattery() throws Exception {
        Mockito.when(scooterService.showScootersByBattery(ArgumentMatchers.any())).thenReturn(new ArrayList<>(List.of(scooterDto)));
        String mvcResultAsString =mvc.perform(MockMvcRequestBuilders.get("/scooters/battery").param("battery", "75")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List response = new ObjectMapper().readValue(mvcResultAsString,List.class);
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
    }

    @Test
    void saveScooter() throws Exception{
        Mockito.when(scooterService.saveScooter(Mockito.any(ScooterDto.class))).thenReturn(scooter);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/scooters")
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(scooter)
                                .getBytes(StandardCharsets.UTF_8))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assertions.assertNotNull(result);
    }

    @Test
    void updateScooter() throws Exception{
        Mockito.when(scooterService.updateScooter(ArgumentMatchers.any(),Mockito.any(ScooterDto.class))).thenReturn(scooter);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/scooters/").param("scooterId","3")
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(scooter)
                                .getBytes(StandardCharsets.UTF_8))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assertions.assertNotNull(result);
    }

    @Test
    void updateStatus() throws Exception {
        Mockito.doNothing().when(scooterService).updateStatus(ArgumentMatchers.any(),ArgumentMatchers.anyString());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/scooters/")
                        .param("scooterId","3").param("status","Taken")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deleteScooter() throws Exception{
        Mockito.doNothing().when(scooterService).deleteScooter(ArgumentMatchers.any());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/scooters/")
                        .param("scooterId","3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}