package com.swarmvision.drone.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateDroneRequest(
    @NotBlank String name, @Positive double size, @NotBlank String color) {}
