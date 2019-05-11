package com.example.hashisushi.model;

import java.io.Serializable;

public class Product implements Serializable {

    private int idProd;
    private String name;
    private String description;
    private String salePrice;
    private boolean isPromotion;
    private String type;


    public Product() {

    }

    public Product(int idProd, String name, String description,
                   String salePrice, boolean isPromotion, String type) {
        this.idProd = idProd;
        this.name = name;
        this.description = description;
        this.salePrice = salePrice;
        this.isPromotion = isPromotion;
        this.type = type;
    }

    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
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


    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public boolean isPromotion() {
        return isPromotion;
    }

    public void setPromotion(boolean promotion) {
        isPromotion = promotion;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
