package com.example.order.controllers;

import com.example.order.entities.Order;
import com.example.order.entities.OrderItem;
import com.example.order.repositories.OrderItemRepository;
import com.example.order.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@RestController
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @PostMapping(value="/order")
    @Transactional
    public ResponseEntity<Order> createNewOrder(@RequestBody Order orderData){
        Order order = orderRepository.save(orderData);

        if (order.getId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't create a new order");

        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }

    @GetMapping(value="/order/{id}")
    public Optional<Order> getOrder(@PathVariable Long id){

        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get order");

        return order;
    }

    @PostMapping(value = "/order/{id}")
    @Transactional
    public ResponseEntity<OrderItem> addProductFromCartToOrder(@PathVariable Long id, @RequestBody OrderItem orderItem)
    {
        Order order;

        try {
            order = orderRepository.getById(id);

        }
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get order", e);

        }
        order.addProduct(orderItem);
        orderRepository.save(order);
        return new ResponseEntity<>(orderItem, HttpStatus.CREATED);
    }


}
