package com.example.hashisushi.model;

import com.example.hashisushi.dao.FirebaseConfig;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.UUID;

import static com.example.hashisushi.dao.FirebaseConfig.getIdUsuario;

public class Notification implements Serializable {

    private String idNot;
    private String title;
    private String body;
    private boolean isSend;

    public Notification() { }

    public void salvar()
    {

        DatabaseReference firebaseRef = FirebaseConfig.getFirebase();
        DatabaseReference notificationRef = firebaseRef
                .child("notification")
                .child( getIdUsuario());

        UUID uuid = UUID.randomUUID();
        String strUuid = uuid.toString();

        setIdNot(strUuid);
        notificationRef.setValue(this);

    }

    public void remover(String idNotification)
    {
        DatabaseReference firebaseRef = FirebaseConfig.getFirebase();
        DatabaseReference notificationRef = firebaseRef
                .child("notification")
                .child( idNotification );
        notificationRef.removeValue();
    }

    public String getIdNot() {
        return idNot;
    }

    public void setIdNot(String idNot) {
        this.idNot = idNot;
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
