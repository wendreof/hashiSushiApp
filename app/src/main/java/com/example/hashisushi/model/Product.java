package com.example.hashisushi.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Serializable {

    private int idProd;
    private String name;
    private String description;
    private String costPrece;
    private String salePrice;
    private String isPromotion;
    private String type;


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

}
