package com.app.transmaroc.mapper;

import com.app.transmaroc.dto.ShipmentRequestDTO;
import com.app.transmaroc.dto.ShipmentResponseDTO;
import com.app.transmaroc.entities.Shipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShipmentMapper {

    @Mapping(source = "shipper.companyName", target = "shipperCompanyName")
    @Mapping(source = "trip.id", target = "tripId")
    @Mapping(source = "trip.originCity", target = "tripOriginCity")
    @Mapping(source = "trip.destinationCity", target = "tripDestinationCity")
    ShipmentResponseDTO toResponseDTO(Shipment shipment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trackingNumber", ignore = true)
    @Mapping(target = "shipper", ignore = true)
    @Mapping(target = "trip", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "cmrPdfUrl", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "checkpoints", ignore = true)
    Shipment toEntity(ShipmentRequestDTO requestDTO);
}
