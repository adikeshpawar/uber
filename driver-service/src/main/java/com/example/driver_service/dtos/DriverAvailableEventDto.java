package com.example.driver_service.dtos;

import com.example.driver_service.entity.DriverStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverAvailableEventDto {
    private String driverId;
    private DriverStatus status;
    private double latitude;
    private double longitude;
    private Long timestamp ;

}
