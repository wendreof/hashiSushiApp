package com.example.hashisushi.model;

import java.math.BigDecimal;

public class Product {

    private long idProd;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal costPrece;
    private BigDecimal salePrice;
    private int quantityStok;
    private boolean isPromotion;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCostPrece() {
        return costPrece;
    }

    public void setCostPrece(BigDecimal costPrece) {
        this.costPrece = costPrece;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
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

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
