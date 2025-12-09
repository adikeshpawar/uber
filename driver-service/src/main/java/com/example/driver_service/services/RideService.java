package com.example.driver_service.services;

import java.util.UUID;

public interface RideService {

    void acceptRide(UUID rideId, UUID driverId);

    void startRide(UUID rideId, UUID driverId);

    void completeRide(UUID rideId, UUID driverId);
}
