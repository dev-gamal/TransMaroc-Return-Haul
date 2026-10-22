package com.app.transmaroc.service;

import com.app.transmaroc.dto.ShipmentRequestDTO;
import com.app.transmaroc.dto.ShipmentResponseDTO;
import com.app.transmaroc.entities.ReturnTrip;
import com.app.transmaroc.entities.Shipment;
import com.app.transmaroc.entities.User;
import com.app.transmaroc.enums.ShipmentStatus;
import com.app.transmaroc.enums.TripStatus;
import com.app.transmaroc.exception.CapacityExceededException;
import com.app.transmaroc.exception.ConcurrentBookingException;
import com.app.transmaroc.exception.ResourceNotFoundException;
import com.app.transmaroc.mapper.ShipmentMapper;
import com.app.transmaroc.repository.ReturnTripRepository;
import com.app.transmaroc.repository.ShipmentRepository;
import com.app.transmaroc.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ReturnTripRepository tripRepository;
    private final ShipmentRepository shipmentRepository;
    private final UserRepository userRepository;
    private final ShipmentMapper shipmentMapper;

    @Transactional
    public ShipmentResponseDTO bookShipment(ShipmentRequestDTO request, String authenticatedUserEmail) {
        try {
            User shipper = userRepository.findByEmail(authenticatedUserEmail)
                    .orElseThrow(() -> new ResourceNotFoundException("Shipper not found"));

            ReturnTrip trip = tripRepository.findById(request.tripId())
                    .orElseThrow(() -> new ResourceNotFoundException("Return Trip not found"));

            if (trip.getStatus() != TripStatus.AVAILABLE) {
                throw new CapacityExceededException("This trip is no longer available for booking.");
            }

            if (trip.getAvailableCapacityTons() < request.weightTons()) {
                throw new CapacityExceededException(
                        String.format("Not enough capacity. Requested: %s, Available: %s",
                                request.weightTons(), trip.getAvailableCapacityTons())
                );
            }

            double newCapacity = trip.getAvailableCapacityTons() - request.weightTons();
            trip.setAvailableCapacityTons(newCapacity);

            if (newCapacity <= 0.1) {
                trip.setStatus(TripStatus.FULL);
            }

            tripRepository.saveAndFlush(trip);

            Shipment shipment = shipmentMapper.toEntity(request);
            shipment.setTrackingNumber(generateTrackingNumber());
            shipment.setStatus(ShipmentStatus.CREATED);
            shipment.setShipper(shipper);
            shipment.setTrip(trip);

            Shipment savedShipment = shipmentRepository.save(shipment);

            log.info("Shipment {} successfully booked by {} on Trip {}",
                    savedShipment.getTrackingNumber(), shipper.getEmail(), trip.getId());

            return shipmentMapper.toResponseDTO(savedShipment);

        } catch (ObjectOptimisticLockingFailureException ex) {
            log.warn("Concurrent booking detected for trip ID: {}. Rejecting transaction.", request.tripId());
            throw new ConcurrentBookingException(
                    "Another booking for this trip has just been confirmed simultaneously. " +
                            "The available capacity has changed. Please refresh and try again."
            );
        }
    }

    private String generateTrackingNumber() {
        return "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
