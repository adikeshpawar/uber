package com.example.driver_service.controllers;

import com.example.driver_service.dtos.VehicleRegisterDto;
import com.example.driver_service.dtos.VehicleResponseDto;
import com.example.driver_service.entity.Vehicle;
import com.example.driver_service.services.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping("/register")
    public ResponseEntity<VehicleResponseDto> register(
            Authentication auth,
            @RequestBody VehicleRegisterDto dto
    ) {
        UUID driverId = UUID.fromString(auth.getName());

        Vehicle v = vehicleService.registerVehicle(driverId, dto);

        VehicleResponseDto response = new VehicleResponseDto(
                v.getId(),
                v.getModel(),
                v.getPlateNumber(),
                v.getColor(),
                v.getVehicleType(),
                v.getCreatedAt(),
                v.getUpdatedAt()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-vehicle")
    public ResponseEntity<VehicleResponseDto> getMyVehicle(Authentication auth) {

        UUID driverId = UUID.fromString(auth.getName());

        Vehicle v = vehicleService.getVehicleByDriver(driverId);

        return ResponseEntity.ok(
                new VehicleResponseDto(
                        v.getId(),
                        v.getModel(),
                        v.getPlateNumber(),
                        v.getColor(),
                        v.getVehicleType(),
                        v.getCreatedAt(),
                        v.getUpdatedAt()
                ));
    }
}
