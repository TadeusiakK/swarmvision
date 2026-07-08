package com.swarmvision.drone.api.request;

import com.swarmvision.drone.domain.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateDroneRequest(
    @NotBlank String name, @Positive double size, @NotBlank String color, @NotNull Status status) {}
