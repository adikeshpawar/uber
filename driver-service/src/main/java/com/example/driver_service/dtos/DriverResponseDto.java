package com.example.driver_service.dtos;

import com.example.driver_service.entity.DriverStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverResponseDto {
    private UUID id;
    private String fullName;
    private String email;
    private String phone;
    private DriverStatus status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private double latitude;
    private  double longitude;
}
