package com.app.transmaroc.dto;

import java.time.LocalDateTime;

public record ShipmentResponseDTO(
        Long id,
        String trackingNumber,
        String shipperCompanyName,
        Long tripId,
        String tripOriginCity,
        String tripDestinationCity,
        String cargoType,
        Double weightTons,
        String status,
        String cmrPdfUrl,
        LocalDateTime createdAt
) {}
