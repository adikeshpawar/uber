package com.example.user_service.kafka;

import com.example.user_service.event.RideRequestEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class UserProducer {

    private final KafkaTemplate<String,String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendRideRequestEvent(RideRequestEvent rideRequestEvent){
        try{
            String json = objectMapper.writeValueAsString(rideRequestEvent);
            kafkaTemplate.send("RIDEREQUESTED",json);
            System.out.println("Produced RIDE_REQUESTED event: " + json);
        } catch (Exception e) {
                throw  new RuntimeException("Failed to serialize event", e);
        }
    }
}
