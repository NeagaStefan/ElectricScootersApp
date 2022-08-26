package com.example.electricscootersapp.Service;

import com.example.electricscootersapp.Entity.HistoryDto;
import com.example.electricscootersapp.Repository.HistoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryServiceImpl implements  HistoryService {
    private final HistoryRepo historyRepo;
    private final ModelMapper modelMapper;

    @Autowired
    private HistoryServiceImpl(HistoryRepo historyRepo, ModelMapper modelMapper){
        this.historyRepo = historyRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<HistoryDto> showCustomerHistory(String userName) {
        return historyRepo.findAllByCustomerUserName(userName).stream().map(history -> modelMapper.map(history,HistoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<HistoryDto> showAllRecords() {
        return historyRepo.showAllRecords().stream().map(history -> modelMapper.map(history,HistoryDto.class)).collect(Collectors.toList());

    }

    @Override
    public List<HistoryDto> showRecordsBetweenDates(Timestamp startDate, Timestamp endDate) {
        return historyRepo.showRecordsBetweenDates(startDate,endDate).stream().map(history -> modelMapper.map(history,HistoryDto.class)).collect(Collectors.toList());
    }
}
