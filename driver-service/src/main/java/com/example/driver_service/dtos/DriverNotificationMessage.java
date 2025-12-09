package com.example.driver_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverNotificationMessage {

    private UUID driverId;
    private String driverName;
    private Double driverLatitude;     // Wrapper type to accept nullable values
    private Double driverLongitude;    // Wrapper type

    private String userId;
    private double pickupLatitude;
    private double pickupLongitude;
    private String vehicleType;
}
