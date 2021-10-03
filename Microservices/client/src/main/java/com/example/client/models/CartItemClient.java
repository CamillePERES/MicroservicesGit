package com.example.client.models;

import com.example.client.bean.ProductBean;

public class CartItemClient {

    private Long id;
    private ProductBean product;
    private int quantity;

    public CartItemClient(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean productId) {
        this.product = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
