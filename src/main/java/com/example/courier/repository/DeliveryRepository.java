package com.example.courier.repository;

import com.example.courier.model.Delivery;
import com.example.courier.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByStatus(String status);
    List<Delivery> findByLocation(String location);

    @Query("SELECT d FROM Delivery d WHERE d.user = :user")
    List<Delivery> findByUser(@Param("user") User user);

    @Query("SELECT d FROM Delivery d WHERE d.id = :id AND d.user = :user")
    Optional<Delivery> findByIdAndUser(@Param("id") Long id, @Param("user") User user);

    @Query("SELECT COUNT(d) FROM Delivery d WHERE d.user = :user")
    Long countByUser(@Param("user") User user);
}
