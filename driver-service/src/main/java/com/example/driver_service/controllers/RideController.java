package com.example.driver_service.controllers;

import com.example.driver_service.services.RideService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    // ✅ Driver accepts ride (SECURE)
    @PostMapping("/{rideId}/accept")
    public ResponseEntity<?> acceptRide(@PathVariable UUID rideId,
                                        Authentication authentication) {

        UUID driverId = UUID.fromString(authentication.getName());

        rideService.acceptRide(rideId, driverId);
        return ResponseEntity.ok("Ride accepted successfully");
    }

    // ✅ Driver starts ride (SECURE)
    @PostMapping("/{rideId}/start")
    public ResponseEntity<?> startRide(@PathVariable UUID rideId,
                                       Authentication authentication) {

        UUID driverId = UUID.fromString(authentication.getName());

        rideService.startRide(rideId, driverId);
        return ResponseEntity.ok("Ride started successfully");
    }

    // ✅ Driver completes ride (SECURE)
    @PostMapping("/{rideId}/complete")
    public ResponseEntity<?> completeRide(@PathVariable UUID rideId,
                                          Authentication authentication) {

        UUID driverId = UUID.fromString(authentication.getName());

        rideService.completeRide(rideId, driverId);
        return ResponseEntity.ok("Ride completed successfully");
    }
}
