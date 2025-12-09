package com.example.driver_service.services;

import com.example.driver_service.dtos.VehicleRegisterDto;
import com.example.driver_service.entity.Vehicle;

import java.util.UUID;

public interface VehicleService {

    Vehicle registerVehicle(UUID driverId, VehicleRegisterDto dto);

    Vehicle getVehicleByDriver(UUID driverId);
}
