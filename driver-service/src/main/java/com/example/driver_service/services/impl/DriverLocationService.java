package com.example.driver_service.services.impl;
import com.example.driver_service.entity.DriverLocation;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DriverLocationService {

    private final Map<String, DriverLocation> driverLocations = new ConcurrentHashMap<>();

    public void updateLocation(String driverId, double lat, double lon) {
        driverLocations.put(driverId, new DriverLocation(driverId, lat, lon));
    }

    public Map<String, DriverLocation> getAllLocations() {
        return driverLocations;
    }
}
