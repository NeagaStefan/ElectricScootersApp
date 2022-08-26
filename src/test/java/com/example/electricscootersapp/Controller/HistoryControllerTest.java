package com.example.electricscootersapp.Controller;

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
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class HistoryControllerTest {
    @Autowired
    private MockMvc mvc;

   @MockBean
   private HistoryService historyService;
   @MockBean
   private CustomerService customerService;
   @MockBean
   private ScooterService scooterService;

   private HistoryDto historyDto;

   @BeforeEach
   void setUp() {
       historyDto = new HistoryDto(2L,2L,"neagaStefan",2,2.34F,null,7.35F,null);
   }

    @Test
    void showAllRecords() throws Exception{
        Mockito.when(historyService.showAllRecords()).thenReturn(new ArrayList<>(List.of(historyDto)));
        String mvcResultAsString =mvc.perform(MockMvcRequestBuilders.get("/history")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List response = new ObjectMapper().readValue(mvcResultAsString,List.class);
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
    }

    @Test
    void showRecordsBetweenDates() throws Exception {
        Mockito.when(historyService.showRecordsBetweenDates(ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(new ArrayList<>(List.of(historyDto)));
        String mvcResultAsString =mvc.perform(MockMvcRequestBuilders.get("/history/").param("startDate","2022-08-07 00:00:00").param("endDate","2022-08-08 00:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List response = new ObjectMapper().readValue(mvcResultAsString,List.class);
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
    }
}