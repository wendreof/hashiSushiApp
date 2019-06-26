package com.example.hashisushi.model;

import com.example.hashisushi.dao.FirebaseConfig;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

import static com.example.hashisushi.dao.FirebaseConfig.getIdUsuario;

public class Notification implements Serializable {

    private String title;
    private String body;
    private boolean isSend;

    public Notification() { }

    public void salvar(){

        DatabaseReference firebaseRef = FirebaseConfig.getFirebase();
        DatabaseReference produtoRef = firebaseRef
                .child("notification")
                .child( getIdUsuario() );
        produtoRef.setValue(this);

    }

    public void remover(){
        DatabaseReference firebaseRef = FirebaseConfig.getFirebase();
        DatabaseReference produtoRef = firebaseRef
                .child("produtos")
                .child( getIdUsuario() );
        produtoRef.removeValue();
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }
}
