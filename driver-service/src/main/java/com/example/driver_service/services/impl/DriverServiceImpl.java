package com.example.driver_service.services.impl;

import com.example.driver_service.dtos.DriverLoginDto;
import com.example.driver_service.dtos.DriverRegisterDto;
import com.example.driver_service.dtos.DriverStatusUpdateDto;
import com.example.driver_service.entity.Driver;
import com.example.driver_service.repository.DriverRepository;
import com.example.driver_service.security.JwtUtil;
import com.example.driver_service.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public Driver register(DriverRegisterDto dto) {

        if (driverRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        if (driverRepository.findByPhone(dto.getPhone()).isPresent()) {
            throw new RuntimeException("Phone already exists");
        }

        Driver driver = new Driver();
        driver.setFullName(dto.getFullName());
        driver.setPhone(dto.getPhone());
        driver.setEmail(dto.getEmail());
        driver.setStatus(com.example.driver_service.entity.DriverStatus.OFFLINE);
        driver.setPasswordHash(passwordEncoder.encode(dto.getPassword()));

        return driverRepository.save(driver);
    }

    @Override
    public String login(DriverLoginDto dto) {
        Driver driver = driverRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(dto.getPassword(), driver.getPasswordHash())) {
            throw new RuntimeException("Invalid email or password");
        }

        return jwtUtil.generateToken(driver);
    }

    @Override
    public Optional<Driver> getById(UUID driverId) {
        return driverRepository.findById(driverId);
    }

    @Override
    public Driver updateStatus(UUID driverId, DriverStatusUpdateDto dto) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        driver.setStatus(dto.getStatus());
        return driverRepository.save(driver);
    }

    @Override
    public void updateLocation(UUID driverId, Double latitude, Double longitude) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        driver.setLatitude(latitude);
        driver.setLongitude(longitude);

        driverRepository.save(driver);
    }

    @Override
    public List<Driver> findNearestDriver(double lat, double lon) {
        return driverRepository.findAvailableDriversWithinFiveKm(lat, lon);
    }

    @Override
    @Transactional
    public Optional<Driver> reserveDriver(UUID driverId) {

        int updated = driverRepository.reserveDriver(driverId);

        if (updated == 0) {
            // No driver reserved – either not found or not AVAILABLE
            return Optional.empty();
        }

        // Now fetch the updated driver
        return driverRepository.findById(driverId);
    }
}
