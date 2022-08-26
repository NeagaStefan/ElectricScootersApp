package com.example.electricscootersapp.Service;

import com.example.electricscootersapp.Entity.Scooter;
import com.example.electricscootersapp.Entity.ScooterDto;

import java.util.List;

public interface ScooterService {
    List<ScooterDto> showAllScooters();

    Scooter saveScooter(ScooterDto scooterDto);

    Scooter updateScooter(Long scooterId,ScooterDto scooterDto);

    void deleteScooter(Long scooterId);

    List<ScooterDto> showScootersByStatus(String status);

    List<ScooterDto> showScootersByPosition(String position);

    List<ScooterDto> showScootersByBattery(Integer battery);

    void updateStatusAndPosition(Long scooterId, String status, String location);

    void updateStatus(Long scooterId, String status);

    List<ScooterDto> showAllScootersAdmin();

}
