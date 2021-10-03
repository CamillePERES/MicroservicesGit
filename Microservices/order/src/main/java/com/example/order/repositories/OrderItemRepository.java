package com.example.order.repositories;

import com.example.order.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

public interface OrderItemRepository extends JpaRepository<Order, Long> {
}
