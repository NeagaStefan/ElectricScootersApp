package com.example.electricscootersapp.Controller;

import com.example.electricscootersapp.Entity.HistoryDto;
import com.example.electricscootersapp.Service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
public class HistoryController {
    private final  HistoryService historyService;

    @Autowired
    private HistoryController(HistoryService historyService){
        this.historyService = historyService;
    }
    @GetMapping("/history")
    public List<HistoryDto> showAllRecords() {
        return historyService.showAllRecords();
    }
    @GetMapping("/history/")
    public List<HistoryDto> showRecordsBetweenDates(@RequestParam("startDate")Timestamp startDate,@RequestParam("endDate") Timestamp endDate){
        return historyService.showRecordsBetweenDates(startDate,endDate);

    }
}
