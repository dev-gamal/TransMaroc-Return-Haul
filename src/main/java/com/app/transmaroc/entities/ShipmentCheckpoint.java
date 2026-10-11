package com.app.transmaroc.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipment_checkpoints")
@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class ShipmentCheckpoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_id", nullable = false)
    private Shipment shipment;

    @Column(nullable = false)
    private String locationCity;

    private String statusNote;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime timestamp;

}
