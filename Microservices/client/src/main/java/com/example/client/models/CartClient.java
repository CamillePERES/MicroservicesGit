package com.example.client.models;

import java.util.List;

public class CartClient {

    private Long id;
    private List<CartItemClient> products;

    public CartClient(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CartItemClient> getProducts() {
        return products;
    }

    public void setProducts(List<CartItemClient> products) {
        this.products = products;
    }

}
