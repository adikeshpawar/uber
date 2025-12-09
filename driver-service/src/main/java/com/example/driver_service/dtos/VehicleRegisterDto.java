package com.example.driver_service.dtos;

import com.example.driver_service.entity.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VehicleRegisterDto {

    @NotBlank
    private String model;

    @NotBlank
    private String plateNumber;

    @NotBlank
    private String color;

    @NotNull
    private VehicleType vehicleType;
}
