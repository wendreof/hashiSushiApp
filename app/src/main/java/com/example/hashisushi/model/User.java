package com.example.hashisushi.model;

import java.io.Serializable;

public class User implements Serializable {

    private long idUser;
    private String name;
    private String bornDate;
    private String address;
    private String neigthborhood;
    private String numberHome;
    private String city;
    private String cep;
    private String state;
    private String phone;
    private String email;
    private String password;
    private String isAdmin;
    private int ponts;

    //firebade requer constructor
    public User() { }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBornDate() {
        return bornDate;
    }

    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNeigthborhood() {
        return neigthborhood;
    }

    public void setNeigthborhood(String neigthborhood) {
        this.neigthborhood = neigthborhood;
    }

    public String getNumberHome() {
        return numberHome;
    }

    public void setNumberHome(String numberHome) {
        this.numberHome = numberHome;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getPonts() {
        return ponts;
    }

    public void setPonts(int ponts) {
        this.ponts = ponts;
    }
}
