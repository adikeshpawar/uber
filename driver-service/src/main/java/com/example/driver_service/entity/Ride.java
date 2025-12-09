package com.example.driver_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rides")
public class Ride {

    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    // ✅ Who requested the ride
    @Column(nullable = false)
    private UUID userId;

    // ✅ Who accepted the ride (NULL until accepted)
    @Column
    private UUID driverId;

    // ✅ Ride lifecycle status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RideStatus status;

    // ✅ Vehicle requested (SUV, MINI, BIKE)
    @Column(nullable = false)
    private String vehicleType;

    // ✅ Pickup location
    @Column(nullable = false)
    private Double pickupLat;

    @Column(nullable = false)
    private Double pickupLng;

    // ✅ Drop location (can be null at request time)
    @Column
    private Double dropLat;

    @Column
    private Double dropLng;

    // ✅ Estimated / final fare
    @Column(precision = 10, scale = 2)
    private BigDecimal fare;

    // ✅ Timestamps for full ride lifecycle
    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column
    private OffsetDateTime acceptedAt;

    @Column
    private OffsetDateTime startedAt;

    @Column
    private OffsetDateTime completedAt;

    // ✅ Automatically handle timestamps
    @PrePersist
    public void prePersist() {
        this.createdAt = OffsetDateTime.now();
    }
}
