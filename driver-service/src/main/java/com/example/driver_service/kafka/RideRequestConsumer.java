package com.example.driver_service.kafka;
import com.example.driver_service.entity.Ride;
import com.example.driver_service.entity.RideStatus;
import com.example.driver_service.event.RideRequestEvent;
import com.example.driver_service.repository.RideRepository;
import com.example.driver_service.services.impl.DriverAssignmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RideRequestConsumer {

    private final RideRepository rideRepository;
    private final DriverAssignmentService assignmentService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(
            topics = "RIDEREQUESTED",
            groupId = "driver-service"
    )
    public void consume(String message) {
        try {
            RideRequestEvent event =
                    objectMapper.readValue(message, RideRequestEvent.class);

            System.out.println("🚖 Driver-Service received ride request: " + event.getRideId());

            try {
                Ride ride = new Ride();
                ride.setId(UUID.fromString(event.getRideId()));
                ride.setUserId(UUID.fromString(event.getUserId()));
                ride.setPickupLat(event.getPickupLatitude());
                ride.setPickupLng(event.getPickupLongitude());
                ride.setVehicleType(event.getVehicleType());
                ride.setStatus(RideStatus.SEARCHING);

                rideRepository.save(ride);

                System.out.println("✅ Ride created in DB with status SEARCHING");

            } catch (Exception ex) {
                System.out.println("⚠️ Ride already exists, skipping creation");
            }

            assignmentService.assignDriver(event);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
