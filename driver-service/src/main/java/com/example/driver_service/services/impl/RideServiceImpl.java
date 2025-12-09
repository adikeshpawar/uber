package com.example.driver_service.services.impl;

import com.example.driver_service.entity.Ride;
import com.example.driver_service.entity.RideStatus;
import com.example.driver_service.repository.RideRepository;
import com.example.driver_service.services.RideService;
import jakarta.transaction.Transactional;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@Transactional
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public RideServiceImpl(RideRepository rideRepository,
                           KafkaTemplate<String, String> kafkaTemplate) {
        this.rideRepository = rideRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void acceptRide(UUID rideId, UUID driverId) {

        int updated = rideRepository.acceptRide(rideId, driverId);

        if (updated == 0) {
            throw new IllegalStateException("Ride already accepted by another driver");
        }

        // ✅ Publish Kafka event ONLY after DB success
        kafkaTemplate.send(
                "ride.accepted",
                rideId.toString(),
                "RIDE_ACCEPTED:" + rideId + ":" + driverId
        );
    }

    @Override
    public void startRide(UUID rideId, UUID driverId) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new IllegalStateException("Ride not found"));

        if (!ride.getDriverId().equals(driverId)) {
            throw new IllegalStateException("Driver not assigned to this ride");
        }

        if (ride.getStatus() != RideStatus.ACCEPTED) {
            throw new IllegalStateException("Ride cannot be started");
        }

        ride.setStatus(RideStatus.STARTED);
        ride.setStartedAt(OffsetDateTime.now());

        rideRepository.save(ride);

        kafkaTemplate.send(
                "ride.started",
                rideId.toString(),
                "RIDE_STARTED:" + rideId
        );
    }

    @Override
    public void completeRide(UUID rideId, UUID driverId) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new IllegalStateException("Ride not found"));

        if (!ride.getDriverId().equals(driverId)) {
            throw new IllegalStateException("Driver not assigned to this ride");
        }

        if (ride.getStatus() != RideStatus.STARTED) {
            throw new IllegalStateException("Ride is not in STARTED state");
        }

        ride.setStatus(RideStatus.COMPLETED);
        ride.setCompletedAt(OffsetDateTime.now());

        rideRepository.save(ride);

        kafkaTemplate.send(
                "ride.completed",
                rideId.toString(),
                "RIDE_COMPLETED:" + rideId
        );
    }
}
