package com.example.driver_service.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DriverLocationUpdateDto {

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;
}
