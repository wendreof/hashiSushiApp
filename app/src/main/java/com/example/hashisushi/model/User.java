package com.example.hashisushi.model;

import com.example.hashisushi.dao.FirebaseConfig;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private String idUser;
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
    private boolean isAdmin;
    private int ponts;
    private String referencePoint;
    private String cpf;

    //firebade requer constructor
    public User() { }

    public void salvarUser()
    {
        DatabaseReference firebaseRef = FirebaseConfig.getFirebase();
        DatabaseReference usuarioRef = firebaseRef.child("users").child(getIdUser());
        usuarioRef.setValue(this);
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
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

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getPonts() {
        return ponts;
    }

    public void setPonts(int ponts) {
        this.ponts = ponts;
    }

    public String getReferencePoint() {
        return referencePoint;
    }

    public void setReferencePoint(String referencePoint) {
        this.referencePoint = referencePoint;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", name='" + name + '\'' +
                ", bornDate='" + bornDate + '\'' +
                ", address='" + address + '\'' +
                ", neigthborhood='" + neigthborhood + '\'' +
                ", numberHome='" + numberHome + '\'' +
                ", city='" + city + '\'' +
                ", cep='" + cep + '\'' +
                ", state='" + state + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin='" + isAdmin + '\'' +
                ", ponts=" + ponts +
                ", referencePoint='" + referencePoint + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getIdUser() == user.getIdUser() &&
                getPonts() == user.getPonts() &&
                Objects.equals(getName(), user.getName()) &&
                Objects.equals(getBornDate(), user.getBornDate()) &&
                Objects.equals(getAddress(), user.getAddress()) &&
                Objects.equals(getNeigthborhood(), user.getNeigthborhood()) &&
                Objects.equals(getNumberHome(), user.getNumberHome()) &&
                Objects.equals(getCity(), user.getCity()) &&
                Objects.equals(getCep(), user.getCep()) &&
                Objects.equals(getState(), user.getState()) &&
                Objects.equals(getPhone(), user.getPhone()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getIsAdmin(), user.getIsAdmin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdUser(), getName(), getBornDate(), getAddress(), getNeigthborhood(), getNumberHome(), getCity(), getCep(), getState(), getPhone(), getEmail(), getPassword(), getIsAdmin(), getPonts());
    }
}
