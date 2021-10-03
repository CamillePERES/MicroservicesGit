package com.example.orders.repositories;


import com.example.orders.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<Orders, Long> {
}
