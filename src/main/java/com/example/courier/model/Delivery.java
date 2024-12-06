package com.example.courier.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "deliveries")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private String location;
    private Double latitude;
    private Double longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "user_delivery_number")
    private Long userDeliveryNumber;
}

