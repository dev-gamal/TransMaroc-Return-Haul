package com.app.transmaroc.mapper;

import com.app.transmaroc.dto.ReturnTripRequestDTO;
import com.app.transmaroc.dto.ReturnTripResponseDTO;
import com.app.transmaroc.entities.ReturnTrip;
import org.mapstruct.Mapping;

public interface ReturnTripMapper {

    @Mapping(source = "carrier.companyName", target = "carrierCompanyName")
    ReturnTripResponseDTO toResponseDTO(ReturnTrip trip);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "carrier", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "shipments", ignore = true)
    ReturnTrip toEntity(ReturnTripRequestDTO requestDTO);
}
