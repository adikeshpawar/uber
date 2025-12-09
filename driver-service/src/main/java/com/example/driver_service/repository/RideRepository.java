package com.example.driver_service.repository;

import com.example.driver_service.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface RideRepository extends JpaRepository<Ride, UUID> {

    Optional<Ride> findByIdAndStatus(UUID id, String status);

    @Modifying
    @Query("""
        UPDATE Ride r
        SET r.status = 'ACCEPTED',
            r.driverId = :driverId,
            r.acceptedAt = CURRENT_TIMESTAMP
        WHERE r.id = :rideId
        AND r.status = 'SEARCHING'
    """)
    int acceptRide(UUID rideId, UUID driverId);

    Optional<Ride> findByDriverIdAndStatus(UUID driverId, String status);
}
