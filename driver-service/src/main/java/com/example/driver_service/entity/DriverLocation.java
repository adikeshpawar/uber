package com.example.driver_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverLocation {
    private String driverId;
    private double latitude;
    private double longitude;
}
