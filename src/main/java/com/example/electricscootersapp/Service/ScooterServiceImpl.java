package com.example.electricscootersapp.Service;

import com.example.electricscootersapp.Entity.Scooter;
import com.example.electricscootersapp.Entity.ScooterDto;
import com.example.electricscootersapp.Repository.ScooterRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ScooterServiceImpl implements ScooterService {
    private final ScooterRepo scooterRepo;
    private final ModelMapper modelMapper;

    @Autowired
    private ScooterServiceImpl(ScooterRepo scooterRepo, ModelMapper modelMapper){
        this.scooterRepo =scooterRepo;
        this.modelMapper = modelMapper;
    }
    @Override
    public List<ScooterDto> showAllScooters() {
        return scooterRepo.showAllScooters().stream().map(scooter -> modelMapper.map(scooter, ScooterDto.class)).collect(Collectors.toList());
    }
    @Override
    public List<ScooterDto> showAllScootersAdmin() {
        return scooterRepo.showAllScootersAdmin().stream().map(scooter -> modelMapper.map(scooter, ScooterDto.class)).collect(Collectors.toList());
    }


    @Override
    public Scooter saveScooter(ScooterDto scooterDto) {
        Scooter scooterResponse = convertToEntity(scooterDto);
        return scooterRepo.save(scooterResponse);
    }

    @Override
    public Scooter updateScooter(Long scooterId, ScooterDto scooterDto) {
        Scooter scooterRequest = convertToEntity(scooterDto);
        Scooter scooterDb= scooterRepo.findById(scooterId).get();
        if(Objects.nonNull(scooterRequest.getScooterModel())&&!"".equalsIgnoreCase(scooterRequest.getScooterModel())){
            scooterDb.setScooterModel(scooterRequest.getScooterModel());
        }
        if(Objects.nonNull(scooterRequest.getStatus())&&!"".equalsIgnoreCase(scooterRequest.getStatus())){
            scooterDb.setStatus(scooterRequest.getStatus());
        }
        if(Objects.nonNull(scooterRequest.getPrice())){
            scooterDb.setPrice(scooterRequest.getPrice());
        }
        if(Objects.nonNull(scooterRequest.getPosition())&&!"".equalsIgnoreCase(scooterRequest.getPosition())){
            scooterDb.setPosition(scooterRequest.getPosition());
        }
        if(Objects.nonNull(scooterRequest.getBatteryPercentage())){
            scooterDb.setBatteryPercentage(scooterRequest.getBatteryPercentage());
        }


        return scooterRepo.save(scooterDb);
    }

    @Override
    public void deleteScooter(Long scooterId) {
        scooterRepo.deleteScooter(scooterId);
    }

    @Override
    public List<ScooterDto> showScootersByStatus(String status) {
        return scooterRepo.showScootersByStatus(status).stream().map(scooter -> modelMapper.map(scooter, ScooterDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<ScooterDto> showScootersByPosition(String position) {
        return scooterRepo.showScootersByPosition(position).stream().map(scooter -> modelMapper.map(scooter, ScooterDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<ScooterDto> showScootersByBattery(Integer battery) {
        return scooterRepo.showScootersByBattery(battery).stream().map(scooter -> modelMapper.map(scooter, ScooterDto.class)).collect(Collectors.toList());
    }

    @Override
    public void updateStatus(Long scooterId, String status) {
        scooterRepo.updateStatus(scooterId,status);


    }


    private ScooterDto convertToDto(Scooter scooter) {
        return (modelMapper.map(scooter, ScooterDto.class));
    }
    private Scooter convertToEntity(ScooterDto scooterDto) {
        return (modelMapper.map(scooterDto,Scooter.class));
    }
}

