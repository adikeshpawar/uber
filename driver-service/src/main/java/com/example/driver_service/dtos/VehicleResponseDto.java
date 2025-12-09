package com.example.driver_service.dtos;

import com.example.driver_service.entity.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class VehicleResponseDto {

    private UUID id;
    private String model;
    private String plateNumber;
    private String color;
    private VehicleType vehicleType;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
