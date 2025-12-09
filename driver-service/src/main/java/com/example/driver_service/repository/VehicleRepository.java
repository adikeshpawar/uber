package com.example.driver_service.repository;

import com.example.driver_service.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
    boolean existsByPlateNumber(String plateNumber);
}
