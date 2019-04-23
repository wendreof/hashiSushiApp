package com.example.hashisushi.model;

import java.math.BigDecimal;

public class Order {

    private long idOrder;
    private Product Production;
    private User user;
    private int dateOrder;
    private int hour;
    private String qrCode;
    private int quantProd;
    private int discont;
    private BigDecimal totalPrince;

    public Order() { }

    public long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(long idOrder) {
        this.idOrder = idOrder;
    }

    public Product getProduction() {
        return Production;
    }

    public void setProduction(Product production) {
        Production = production;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(int dateOrder) {
        this.dateOrder = dateOrder;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public int getQuantProd() {
        return quantProd;
    }

    public void setQuantProd(int quantProd) {
        this.quantProd = quantProd;
    }

    public int getDiscont() {
        return discont;
    }

    public void setDiscont(int discont) {
        this.discont = discont;
    }

    public BigDecimal getTotalPrince() {
        return totalPrince;
    }

    public void setTotalPrince(BigDecimal totalPrince) {
        this.totalPrince = totalPrince;
    }
}
