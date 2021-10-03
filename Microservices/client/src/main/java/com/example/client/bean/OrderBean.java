package com.example.client.bean;

import java.util.List;

public class OrderBean {

    private Long id;
    private List<OrderItemBean> items;

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
}
