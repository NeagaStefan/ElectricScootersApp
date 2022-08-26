package com.example.electricscootersapp.Service;

import com.example.electricscootersapp.Entity.HistoryDto;

import java.sql.Timestamp;
import java.util.List;

public interface HistoryService {
    List<HistoryDto> showCustomerHistory(String userName);

    List<HistoryDto> showAllRecords();

    List<HistoryDto> showRecordsBetweenDates(Timestamp startDate, Timestamp endDate);
}
