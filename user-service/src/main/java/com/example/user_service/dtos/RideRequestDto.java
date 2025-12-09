package com.example.user_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestDto {
    private String rideId;

    private String userId;
    private double pickupLatitude;
    private double pickupLongitude;
    private String vehicleType;


    // Getters and Setters (Omitted for brevity)
    // ...
}