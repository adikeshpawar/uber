package com.example.driver_service.kafka;

import com.example.driver_service.dtos.DriverNotificationMessage;
import com.example.driver_service.entity.Driver;
import com.example.driver_service.event.RideRequestEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String TOPIC = "DRIVER_NOTIFICATIONS";

    public void notifyDriver(Driver driver, RideRequestEvent event) {
        try {
            // Build notification message
            DriverNotificationMessage notification = new DriverNotificationMessage(
                    driver.getId(),
                    driver.getFullName(),
                    driver.getLatitude(),
                    driver.getLongitude(),
                    event.getUserId(),
                    event.getPickupLatitude(),
                    event.getPickupLongitude(),
                    event.getVehicleType()
            );

            // Convert to JSON
            String json = objectMapper.writeValueAsString(notification);

            // Send one message per driver
            kafkaTemplate.send(TOPIC, json);

            System.out.println("📨 Sent notification to driver " + driver.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
