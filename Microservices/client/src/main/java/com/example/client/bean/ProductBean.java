package com.example.client.bean;

public class ProductBean {

    private Long  Id;
    private String name;
    private String description;
    private String illustration;
    private Double price;

    public ProductBean() {

    }

    public ProductBean(Long Id, String name, String description, String illustration,Double price ){
        this.Id = Id;
        this.name=name;
        this.description=description;
        this.illustration=illustration;
        this.price=price;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIllustration() {
        return illustration;
    }

    public void setIllustration(String illustration) {
        this.illustration = illustration;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
