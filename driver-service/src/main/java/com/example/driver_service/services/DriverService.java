package com.example.driver_service.services;

import com.example.driver_service.dtos.DriverLoginDto;
import com.example.driver_service.dtos.DriverRegisterDto;
import com.example.driver_service.dtos.DriverStatusUpdateDto;
import com.example.driver_service.entity.Driver;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DriverService {

    Driver register(DriverRegisterDto dto);

    String login(DriverLoginDto dto);

    Optional<Driver> getById(UUID driverId);

    Driver updateStatus(UUID driverId, DriverStatusUpdateDto dto);
    void updateLocation(UUID driverId, Double latitude, Double longitude);
    public List<Driver> findNearestDriver(double lat, double lon);
    public Optional<Driver> reserveDriver(UUID driverId);

}
