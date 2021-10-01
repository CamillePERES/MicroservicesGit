package com.example.product.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long  Id;
    private String name;
    private String description;
    private String illustration;
    private Double price;

    public Product() {

    }

    public Product(Long Id, String name, String description, String illustration,Double price ){
        this.Id = Id;
        this.name=name;
        this.description=description;
        this.illustration=illustration;
        this.price=price;
    }

    public Long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIllustration() {
        return illustration;
    }

    public Double getPrice() {
        return price;
    }

    public void setId(Long id) {
        Id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIllustration(String illustration) {
        this.illustration = illustration;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
