package com.example.driver_service.services.impl;

import com.example.driver_service.dtos.VehicleRegisterDto;
import com.example.driver_service.entity.Driver;
import com.example.driver_service.entity.Vehicle;
import com.example.driver_service.repository.DriverRepository;
import com.example.driver_service.repository.VehicleRepository;
import com.example.driver_service.services.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
@RequiredArgsConstructor
@Service
public class VehicleServiceImpl implements VehicleService {

    private  final DriverRepository driverRepository;
    private  final VehicleRepository vehicleRepository;
    @Override
    public Vehicle registerVehicle(UUID driverId, VehicleRegisterDto dto) {
        Driver driver = this.driverRepository.findById(driverId).orElseThrow(()-> new RuntimeException("driver doesnt exist"));

        if (vehicleRepository.existsByPlateNumber(dto.getPlateNumber())) {
            throw new RuntimeException("Vehicle plate already exists");
        }

        if (driver.getVehicle() != null) {
            throw new RuntimeException("Driver already has a vehicle assigned");
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setDriver(driver);
        vehicle.setModel(dto.getModel());
        vehicle.setPlateNumber(dto.getPlateNumber());
        vehicle.setColor(dto.getColor());
        vehicle.setVehicleType(dto.getVehicleType());

        driver.setVehicle(vehicle);  // bi-directional

        return vehicleRepository.save(vehicle);

    }


    @Override
    public Vehicle getVehicleByDriver(UUID driverId) {
        return driverRepository.findById(driverId)
                .map(Driver::getVehicle)
                .orElseThrow(() -> new RuntimeException("Driver or vehicle not found"));
    }
}
