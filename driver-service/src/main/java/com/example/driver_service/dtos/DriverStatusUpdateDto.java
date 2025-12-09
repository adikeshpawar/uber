package com.example.driver_service.dtos;

import com.example.driver_service.entity.DriverStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverStatusUpdateDto {

    @NotNull(message = "Driver status is required")
    private DriverStatus status;
}
