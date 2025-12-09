package com.example.driver_service.event;

import com.example.driver_service.entity.DriverStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverAvailableEvent {
    private String driverId;
    private DriverStatus status;
    private double latitude;
    private double longitude;
    private Long timestamp ;

}
