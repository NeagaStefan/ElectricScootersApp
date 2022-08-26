package com.example.electricscootersapp.Service;

import com.example.electricscootersapp.Entity.*;
import com.example.electricscootersapp.Error.LocationNotFoundException;
import com.example.electricscootersapp.Error.StatusNotFoundException;
import com.example.electricscootersapp.Repository.ScooterRepo;
import org.apache.commons.lang3.EnumUtils;
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
        return convertListToDto(scooterRepo.showAllScooters());
    }

    @Override
    public List<ScooterDto> showAllScootersAdmin() {
        return convertListToDto(scooterRepo.showAllScootersAdmin());
    }

    @Override
    public List<ScooterDto> showAllAvailableScooters() {
        return convertListToDto(scooterRepo.showAllAvailableScooters());
    }

    @Override
    public Scooter saveScooter(ScooterDto scooterDto) {
        Scooter scooterResponse = convertToEntity(scooterDto);
        verifyStatus(scooterResponse.getStatus());
        return scooterRepo.save(scooterResponse);
    }

    @Override
    public Scooter updateScooter(Long scooterId, ScooterDto scooterDto) {
        Scooter scooterRequest = convertToEntity(scooterDto);
        Scooter scooterDb= scooterRepo.findById(scooterId).get();
        verifyStatus(scooterRequest.getStatus());

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
        verifyStatus(status);
        return convertListToDto(scooterRepo.showScootersByStatus(status));
    }

    @Override
    public List<ScooterDto> showScootersByPosition(String position) {
        return convertListToDto(scooterRepo.showScootersByPosition(position));
    }

    @Override
    public List<ScooterDto> showScootersByBattery(Integer battery) {
        return convertListToDto(scooterRepo.showScootersByBattery(battery));
    }

    @Override
    public void updateStatusAndPosition(Long scooterId, String status, String location) {
        verifyStatus(status);
        String newLocation;
        if (EnumUtils.isValidEnumIgnoreCase(LocationEnum.class, location)) {
            newLocation = location;
        } else {
            throw new StatusNotFoundException("The status is incorrect, try again");
        }
        scooterRepo.updateStatusAndPosition(scooterId,status,newLocation);


    }

    @Override
    public void updateStatus(Long scooterId, String status) {
        scooterRepo.updateStatus(scooterId,status);
    }
    private void verifyStatus(String status){
        if(EnumUtils.isValidEnumIgnoreCase(StatusEnum.class, status)){

        }else{
            throw new LocationNotFoundException("The location is not permitted, look to leave the scooter in permitted areas");
        }
    }


    private List<ScooterDto> convertListToDto(List<Scooter> scooters){
        return scooters.stream().map(scooter -> modelMapper.map(scooter, ScooterDto.class)).collect(Collectors.toList());
    }
    private Scooter convertToEntity(ScooterDto scooterDto) {
        return (modelMapper.map(scooterDto,Scooter.class));
    }
}

