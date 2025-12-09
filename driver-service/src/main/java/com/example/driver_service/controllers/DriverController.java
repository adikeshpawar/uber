package com.example.driver_service.controllers;

import com.example.driver_service.dtos.*;
import com.example.driver_service.entity.Driver;
import com.example.driver_service.entity.DriverLocation;
import com.example.driver_service.services.DriverService;
import com.example.driver_service.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/driver")
public class DriverController {



    private final DriverService driverService;
    private final JwtUtil jwtUtil;

    // ==========================
    // REGISTER
    // ==========================
    @PostMapping("/register")
    public ResponseEntity<DriverResponseDto> register(
            @Valid @RequestBody DriverRegisterDto dto
    ) {
        Driver driver = driverService.register(dto);

        DriverResponseDto response = new DriverResponseDto(
                driver.getId(),
                driver.getFullName(),
                driver.getEmail(),
                driver.getPhone(),
                driver.getStatus(),
                driver.getCreatedAt(),
                driver.getUpdatedAt(),
                driver.getLatitude(),
                driver.getLongitude()
        );

        return ResponseEntity.ok(response);
    }

    // ==========================
    // LOGIN
    // ==========================
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody DriverLoginDto dto
    ) {
        String token = driverService.login(dto);
        return ResponseEntity.ok(token);
    }

    // ==========================
    // GET PROFILE
    // ==========================
    @GetMapping("/me")
    public ResponseEntity<DriverResponseDto> me(Authentication authentication) {

        String driverId = authentication.getName();
        Driver driver = driverService
                .getById(UUID.fromString(driverId))
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        DriverResponseDto response = new DriverResponseDto(
                driver.getId(),
                driver.getFullName(),
                driver.getEmail(),
                driver.getPhone(),
                driver.getStatus(),
                driver.getCreatedAt(),
                driver.getUpdatedAt(),
                driver.getLatitude(),
                driver.getLongitude()
        );

        return ResponseEntity.ok(response);
    }

    // ==========================
    // UPDATE STATUS (ONLINE / OFFLINE / ON_TRIP)
    // ==========================
    @PostMapping("/status")
    public ResponseEntity<DriverResponseDto> updateStatus(
            Authentication authentication,
            @Valid @RequestBody DriverStatusUpdateDto dto
    ) {

        String driverId = authentication.getName();

        Driver driver = driverService.updateStatus(
                UUID.fromString(driverId), dto
        );

        DriverResponseDto response = new DriverResponseDto(
                driver.getId(),
                driver.getFullName(),
                driver.getEmail(),
                driver.getPhone(),
                driver.getStatus(),
                driver.getCreatedAt(),
                driver.getUpdatedAt(),
                driver.getLatitude(),
                driver.getLongitude()
        );

        return ResponseEntity.ok(response);
    }
    @GetMapping("/nearest")
    public ResponseEntity<?> nearestDriver(
            @RequestParam double lat,
            @RequestParam double lon
    ) {
        List<Driver> drivers = driverService.findNearestDriver(lat, lon);

        if (drivers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(drivers);
    }

    @PostMapping("/{driverId}/reserve")
    public ResponseEntity<Object> reserveDriver(@PathVariable UUID driverId) {
        return driverService.reserveDriver(driverId)
                .<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(409).body("Driver not available"));

    }
    @PostMapping("/location")
    public ResponseEntity<?> updateDriverLocation(
            @RequestBody @Valid DriverLocationUpdateDto request
    ) {

        // ✅ Extract driverId from JWT (set by JwtFilter)
        String driverIdStr = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        UUID driverId = UUID.fromString(driverIdStr);

        driverService.updateLocation(
                driverId,
                request.getLatitude(),
                request.getLongitude()
        );

        return ResponseEntity.ok("✅ Driver location updated successfully");
    }

}
