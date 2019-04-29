package com.example.hashisushi.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Serializable {

    private int idProd;
    private String name;
    private String description;
    private String price;
    private String costPrece;
    private String salePrice;
    private String quantityStok;
    private String isPromotion;
    private String type;
    private String discount;

    public Product() {

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCostPrece() {
        return costPrece;
    }

    public void setCostPrece(String costPrece) {
        this.costPrece = costPrece;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getQuantityStok() {
        return quantityStok;
    }

    public void setQuantityStok(String quantityStok) {
        this.quantityStok = quantityStok;
    }

    public String getIsPromotion() {
        return isPromotion;
    }

    public void setIsPromotion(String isPromotion) {
        this.isPromotion = isPromotion;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
