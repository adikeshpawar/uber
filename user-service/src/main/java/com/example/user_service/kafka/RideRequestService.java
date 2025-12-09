package com.example.user_service.kafka;

import com.example.user_service.dtos.RideRequestDto;
import com.example.user_service.event.RideRequestEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RideRequestService {

    private final UserProducer userProducer;

    public void requestRide(RideRequestDto rideRequestDto) {
        String rideId = UUID.randomUUID().toString();
        RideRequestEvent rideRequestEvent = new RideRequestEvent(
                rideId,
                rideRequestDto.getUserId(),
                rideRequestDto.getPickupLatitude(),
                rideRequestDto.getPickupLongitude(),
                rideRequestDto.getVehicleType(),
                System.currentTimeMillis()   // FIXED
        );
        System.out.println("✅ Sending ride request with rideId = " + rideRequestEvent.getRideId());


        userProducer.sendRideRequestEvent(rideRequestEvent);
    }
}
