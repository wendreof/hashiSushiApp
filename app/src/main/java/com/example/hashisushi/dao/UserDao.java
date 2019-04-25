package com.example.hashisushi.dao;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.hashisushi.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDao {

    private DatabaseReference reference ;


    public UserDao() {
        this.reference = FirebaseDatabase.getInstance().getReference();
    }
    public void addUser(User user) {
        DatabaseReference users = reference.child("users");
        try {
            users.child("0003").setValue(user);
            System.out.printf("ERRO-Usuario não foi salvo !");
        } catch (Exception erro) {
            System.out.printf("Atenção Usuario não foi salvo ! ERRO :"+erro);
        }
    }

    public void searchUser(){
        DatabaseReference users = reference.child("users");

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("USUARIOS----->",dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
