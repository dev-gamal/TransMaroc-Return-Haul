package com.app.transmaroc.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReturnTripResponseDTO(
        Long id,
        String carrierCompanyName,
        String originCity,
        String destinationCity,
        LocalDateTime departureDate,
        Double availableCapacityTons,
        BigDecimal pricePerTonMad,
        String status
) {}
