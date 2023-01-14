package com.example.beans;

import com.example.services.VehicleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("vehicleBean")
public class Vehicle {
    private String name = "Honda";

    private final VehicleServices vehicleservies;

    @Autowired
    public Vehicle(VehicleServices vs) {

        this.vehicleservies = vs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VehicleServices getVehicleservies() {
        return vehicleservies;
    }
}
