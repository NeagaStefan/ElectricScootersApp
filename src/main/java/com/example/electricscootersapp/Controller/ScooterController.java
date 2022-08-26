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
    //Shows all scooters available for customers
    @GetMapping("/scooters")
    public List<ScooterDto> showAllScooters(){
        return scooterService.showAllScooters();
    }
    //Show all scooters in the company

    @GetMapping("/scooters/admin")
    public List<ScooterDto> showAllScootersAdmin(){
        return scooterService.showAllScootersAdmin();
    }

    //Shows scooters by status for checking
    @GetMapping("/scooters/status")
    public List<ScooterDto> showScootersByStatus(@RequestParam ("status") String status){
        return scooterService.showScootersByStatus(status);
    }

    //Shows scooters by position to know where are the closest ones
    @GetMapping("/scooters/position")
    public List<ScooterDto> showScootersByPosition(@RequestParam ("position") String position){
        return scooterService.showScootersByPosition(position);
    }

    //Show scooters under some percent of battery, good to know witch ones to take to charge
    @GetMapping("/scooters/battery")
    public List<ScooterDto> showScootersByBattery(@RequestParam ("battery") Integer battery){
        return scooterService.showScootersByBattery(battery);
    }


    //Introducing a new Scooter in the database
    @PostMapping("/scooters")
    public Scooter saveScooter(@Valid @RequestBody ScooterDto scooterDto){
        return scooterService.saveScooter(scooterDto);
    }

    //Updating existing scooter
    @PostMapping("/scooters/")
    public Scooter updateScooter(@Valid @RequestParam("scooterId") Long scooterId,@RequestBody ScooterDto scooterDto){
        return scooterService.updateScooter(scooterId,scooterDto);
    }

    //Changing the status of the scooter
    //ToDo change the position too
    @PutMapping("/scooters/")
    public void updateStatus(@RequestParam ("scooterId") Long scooterId,@RequestParam("status") String status){
        scooterService.updateStatus(scooterId,status);
    }

    //Soft deletes a scooter
    @DeleteMapping("/scooters/")
    public void deleteScooter(@RequestParam("scooterId")Long scooterId){
        scooterService.deleteScooter(scooterId);
    }


}
