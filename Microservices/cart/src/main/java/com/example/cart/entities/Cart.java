package com.example.cart.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CartItem> products;

    public Cart(){

    }

    public Cart(Long id, List<CartItem> products){
        this.id=id;
        this.products=products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CartItem> getProducts() {
        return products;
    }

    public void setProducts(List<CartItem> products) {
        this.products = products;
    }

    public void addProduct(CartItem cartItem){
        this.products.add(cartItem);
    }
}
