package com.swarmvision.drone.api.response;

import com.swarmvision.drone.domain.Status;
import java.time.Instant;
import java.util.UUID;

public record DroneResponse(
    UUID id,
    String name,
    double size,
    String color,
    Status status,
    Instant createdAt,
    Instant updatedAt) {}
