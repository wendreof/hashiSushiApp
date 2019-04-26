package com.example.hashisushi.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Serializable {

    private long idProd;
    private String name;
    private String description;
    private double price;
    private double costPrece;
    private double salePrice;
    private int quantityStok;
    private boolean isPromotion;
    private String type;
    private int discount;

    public Product() {

    }

    public long getIdProd() {
        return idProd;
    }

    public void setIdProd(long idProd) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCostPrece() {
        return costPrece;
    }

    public void setCostPrece(double costPrece) {
        this.costPrece = costPrece;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public int getQuantityStok() {
        return quantityStok;
    }

    public void setQuantityStok(int quantityStok) {
        this.quantityStok = quantityStok;
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


    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
