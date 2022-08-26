package com.example.electricscootersapp.Controller;

import com.example.electricscootersapp.Entity.Scooter;
import com.example.electricscootersapp.Entity.ScooterDto;
import com.example.electricscootersapp.Service.ScooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ScooterController {
    private final  ScooterService scooterService;

    @Autowired
    private ScooterController(ScooterService scooterService){
        this.scooterService  =scooterService;
    }

    @GetMapping("/scooters")
    public List<ScooterDto> showAllScooters(){
        return scooterService.showAllScooters();
    }
    @GetMapping("/scooters/status")
    public List<ScooterDto> showScootersByStatus(@RequestParam ("status") String status){
        return scooterService.showScootersByStatus(status);
    }
    @GetMapping("/scooters/position")
    public List<ScooterDto> showScootersByPosition(@RequestParam ("position") String position){
        return scooterService.showScootersByPosition(position);
    }
    @GetMapping("/scooters/battery")
    public List<ScooterDto> showScootersByBattery(@RequestParam ("battery") Integer battery){
        return scooterService.showScootersByBattery(battery);
    }


    @PostMapping("/scooters")
    public Scooter saveScooter(@Valid @RequestBody ScooterDto scooterDto){
        return scooterService.saveScooter(scooterDto);
    }
    @PostMapping("/scooters/")
    public Scooter updateScooter(@Valid @RequestParam("scooterId") Long scooterId,@RequestBody ScooterDto scooterDto){
        return scooterService.updateScooter(scooterId,scooterDto);
    }

    @PutMapping("/scooters/")
    public void updateStatus(@RequestParam ("scooterId") Long scooterId,@RequestParam("status") String status){
        scooterService.updateStatus(scooterId,status);
    }

    @DeleteMapping("/scooters/")
    public void deleteScooter(@RequestParam("scooterId")Long scooterId){
        scooterService.deleteScooter(scooterId);
    }


}
