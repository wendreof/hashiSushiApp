package com.example.hashisushi.model;

public class Promotion {

    private Product product;
    private long idPromotion;
    private String description;
    private int dataInit;
    private int dataFinal;

    public Promotion(){}

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getIdPromotion() {
        return idPromotion;
    }

    public void setIdPromotion(long idPromotion) {
        this.idPromotion = idPromotion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDataInit() {
        return dataInit;
    }

    public void setDataInit(int dataInit) {
        this.dataInit = dataInit;
    }

    public int getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(int dataFinal) {
        this.dataFinal = dataFinal;
    }
}
