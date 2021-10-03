package com.example.client.bean;

import java.util.List;

public class OrderBean {

    private Long id;
    private List<OrderItemBean> items;
    private double total;

    public OrderBean(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItemBean> getItems() {
        return items;
    }

    public void setItems(List<OrderItemBean> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
