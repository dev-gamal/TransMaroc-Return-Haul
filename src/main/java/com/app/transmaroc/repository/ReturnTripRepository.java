package com.app.transmaroc.repository;

import com.app.transmaroc.entities.ReturnTrip;
import com.app.transmaroc.enums.TripStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReturnTripRepository extends JpaRepository<ReturnTrip, Long> {

    @Query("SELECT r FROM ReturnTrip r WHERE r.status = :status " +
            "AND r.originCity = :origin " +
            "AND r.destinationCity = :destination " +
            "AND r.departureDate >= :minDate " +
            "AND r.availableCapacityTons >= :requiredTons")
    List<ReturnTrip> findMatchingTrips(
            @Param("status") TripStatus status,
            @Param("origin") String origin,
            @Param("destination") String destination,
            @Param("minDate") LocalDateTime minDate,
            @Param("requiredTons") Double requiredTons
    );

    List<ReturnTrip> findByCarrierId(Long carrierId);
}
