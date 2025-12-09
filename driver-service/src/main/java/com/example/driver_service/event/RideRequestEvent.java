package com.example.driver_service.event;

import lombok.Data;

@Data
public class RideRequestEvent {
    private  String rideId;
    private String userId;
    private double pickupLatitude;
    private double pickupLongitude;
    private String vehicleType;
    private long timestamp;
}
