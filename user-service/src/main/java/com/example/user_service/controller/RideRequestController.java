package com.example.user_service.controller;

import com.example.user_service.dtos.RideRequestDto;
import com.example.user_service.kafka.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rides")
@RequiredArgsConstructor
public class RideRequestController {

    private final RideRequestService rideRequestService;

    @PostMapping("/request")
    public String requestRide(@RequestBody RideRequestDto rideRequestDto) {
        rideRequestService.requestRide(rideRequestDto);
        return "Ride request event sent to Kafka!";
    }
}
