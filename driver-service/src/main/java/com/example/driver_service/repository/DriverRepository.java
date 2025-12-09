package com.example.driver_service.repository;

import com.example.driver_service.entity.Driver;
import com.example.driver_service.entity.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DriverRepository extends JpaRepository<Driver, UUID> {

    List<Driver> findByStatus(DriverStatus status);

    Optional<Driver> findByEmail(String email);
    Optional<Driver> findByPhone(String phone);
    @Query(value = """
    SELECT *
    FROM drivers
    WHERE status = 'ONLINE'
      AND latitude IS NOT NULL
      AND longitude IS NOT NULL
      AND (
          6371 * acos(
              cos(radians(:lat)) *
              cos(radians(latitude)) *
              cos(radians(longitude) - radians(:lon)) +
              sin(radians(:lat)) *
              sin(radians(latitude))
          )
      ) <= 5
    """, nativeQuery = true)
    List<Driver> findAvailableDriversWithinFiveKm(
            @Param("lat") double lat,
            @Param("lon") double lon
    );


    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = """
        UPDATE drivers
        SET status = 'ON_TRIP'
        WHERE id = :driverId AND status = 'AVAILABLE'
        """, nativeQuery = true)
    int reserveDriver(@Param("driverId") UUID driverId);
}
