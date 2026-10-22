package com.app.transmaroc.repository;

import com.app.transmaroc.entities.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    List<Shipment> findByTrackingNumber(String trackingNumber);

    List<Shipment> findByShipperId(Long id);

    List<Shipment> findByTripId(Long id);
}
