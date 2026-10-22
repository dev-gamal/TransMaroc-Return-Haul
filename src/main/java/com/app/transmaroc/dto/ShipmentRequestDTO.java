package com.app.transmaroc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ShipmentRequestDTO(
        @NotNull(message = "Trip ID is required")
        Long tripId,

        @NotBlank(message = "Cargo type must be specified")
        String cargoType,

        @Positive(message = "Weight must be greater than zero")
        @NotNull(message = "Weight is required")
        Double weightTons
) {}
