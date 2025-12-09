package com.example.driver_service.services.impl;

import com.example.driver_service.utils.DistanceUtil;
import com.example.driver_service.entity.Driver;
import com.example.driver_service.entity.DriverStatus;
import com.example.driver_service.event.RideRequestEvent;
import com.example.driver_service.repository.DriverRepository;
import com.example.driver_service.kafka.NotificationProducer;   // <-- make sure you import this
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DriverAssignmentService {

    private final DriverRepository driverRepository;
    private final DistanceUtil distanceUtil;
    private final NotificationProducer notificationProducer;   // <-- inject producer
    public void assignDriver(RideRequestEvent event) {

        double reqLat = event.getPickupLatitude();
        double reqLon = event.getPickupLongitude();

        System.out.println("🚖 Ride pickup location: lat=" + reqLat + ", lon=" + reqLon);

        List<Driver> availableDrivers = driverRepository.findByStatus(DriverStatus.ONLINE);

        System.out.println("✅ Found " + availableDrivers.size() + " ONLINE drivers");

        for (Driver driver : availableDrivers) {

            System.out.println(
                    "👤 Checking driver " + driver.getId() +
                            " lat=" + driver.getLatitude() +
                            " lon=" + driver.getLongitude()
            );

            double distance = distanceUtil.distanceInKm(
                    reqLat,
                    reqLon,
                    driver.getLatitude(),
                    driver.getLongitude()
            );

            System.out.println(
                    "📏 Distance to driver " + driver.getId() + " = " + distance + " km"
            );

            if (distance <= 5.0) {
                System.out.println("✅✅ NOTIFYING DRIVER " + driver.getId());
                notificationProducer.notifyDriver(driver, event);
            } else {
                System.out.println("❌ Driver too far, skipping...");
            }
        }

        System.out.println(
                "🚗 Completed assignment check for user " + event.getUserId() +
                        " requesting " + event.getVehicleType()
        );
    }

}
