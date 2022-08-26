package com.example.electricscootersapp.Entity;

public enum StatusEnum {
    AVAILABLE("available"),
    TAKEN("Taken"),
    CHARGING("Charging"),
    Deposit("Deposit"),
    DELETED("Deleted");

    private final String val;

    StatusEnum(String val) {
        this.val = val;
    }
    }