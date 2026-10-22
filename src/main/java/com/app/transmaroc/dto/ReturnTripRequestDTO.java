package com.app.transmaroc.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReturnTripRequestDTO (
        @NotBlank(message = "Origin city is required")
        String originCity,

        @NotBlank(message = "Destination city is required")
        String destinationCity,

        @FutureOrPresent(message = "Departure date cannot be in the past")
        @NotNull(message = "Departure date is required")
        LocalDateTime departureDate,

        @Positive(message = "Capacity must be greater than zero")
        @NotNull(message = "Available capacity is required")
        Double availableCapacityTons,

        @PositiveOrZero(message = "Price cannot be negative")
        @NotNull(message = "Price per ton is required")
        BigDecimal pricePerTonMad
){}
