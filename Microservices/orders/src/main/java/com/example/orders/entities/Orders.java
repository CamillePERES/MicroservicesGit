package com.example.orders.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Orders {

    @Id
    @GeneratedValue
    private Long id;
    private double total;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> items;

    public Orders(){

    }

    public Orders(Long id, Double total, List<OrderItem> items) {
        this.id = id;
        this.items = items;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void addProduct(OrderItem orderItem){
        this.items.add(orderItem);
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
