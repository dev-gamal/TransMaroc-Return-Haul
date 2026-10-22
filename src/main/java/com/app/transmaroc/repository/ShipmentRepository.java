package com.app.transmaroc.repository;

import com.app.transmaroc.entities.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    List<Shipment> findByTrackingNumber(String trackingNumber);

    List<Shipment> findByShipperId(Long id);

    List<Shipment> findByTripId(Long id);
}
