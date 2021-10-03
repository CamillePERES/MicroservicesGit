package com.example.order.service;

import com.example.order.entities.Order;
import com.example.order.entities.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private Product product;
    @Autowired
    private OrderItem orderItem;


    public int totalOrder (){
        int total = orderItem.getQuantity() * product.getPrice();
        return total;
    }

}
