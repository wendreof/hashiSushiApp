package com.example.hashisushi.model;

import java.io.Serializable;

public class Product implements Serializable {

    private String description;
    private String idProd;
    private Boolean isPromotion;
    private String name;
    private String salePrice;
    private String type;


    public Product() {

    }

    public Product(String description,String idProd,Boolean isPromotion ,
                   String name, String salePrice, String type) {
        this.description = description;
        this.idProd = idProd;
        this.isPromotion = isPromotion;
        this.name = name;
        this.salePrice = salePrice;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdProd() {
        return idProd;
    }

    public void setIdProd(String idProd) {
        this.idProd = idProd;
    }

    public Boolean getPromotion() {
        return isPromotion;
    }

    public void setPromotion(Boolean promotion) {
        isPromotion = promotion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Product{" +
                "description='" + description + '\'' +
                ", idProd='" + idProd + '\'' +
                ", isPromotion=" + isPromotion +
                ", name='" + name + '\'' +
                ", salePrice='" + salePrice + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
