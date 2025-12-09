    package com.example.user_service.event;

    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class RideRequestEvent {
        private String rideId;

        private String userId;
        private double pickupLatitude;
        private double pickupLongitude;
        private String vehicleType;
        private long timestamp = System.currentTimeMillis();

        // Getters and Setters (Omitted for brevity, but necessary)
        // ...
    }