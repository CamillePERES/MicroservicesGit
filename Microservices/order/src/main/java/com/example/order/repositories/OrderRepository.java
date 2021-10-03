package com.example.order.repositories;

import com.example.order.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
