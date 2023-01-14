package com.example.main;

import com.example.beans.Person;
import com.example.beans.Vehicle;
import com.example.config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Example1 {

    public static void main(String[] args) {

        var context  = new AnnotationConfigApplicationContext(ProjectConfig.class);
        Person person = context.getBean(Person.class);
        Vehicle vehicle = context.getBean(Vehicle.class);

        String[] vehiclenames = context.getBeanNamesForType(Vehicle.class);

        System.out.println(vehiclenames);

        vehicle.getVehicleservies().moveVehicle();
        vehicle.getVehicleservies().playMusic();


    }
}