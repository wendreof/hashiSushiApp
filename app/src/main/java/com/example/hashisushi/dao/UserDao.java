package com.example.hashisushi.dao;

import android.support.annotation.NonNull;
import android.util.Log;
import com.example.hashisushi.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserDao {

    private DatabaseReference reference ;

    public UserDao() {
        this.reference = FirebaseDatabase.getInstance().getReference();
    }
    public void addUser(User user) {
        DatabaseReference users = reference.child("users");
        try {
            // push cria um novo no
            users.push().setValue(user);
            System.out.printf("ERRO-Usuario não foi salvo !");
        } catch (Exception erro) {
            System.out.printf("Atenção Usuario não foi salvo ! ERRO :"+erro);
        }
    }

    public void searchUser(){
        //retorna usuarios
        DatabaseReference users = reference.child("users");
        //retorna o no setado
       // DatabaseReference usersSearch = users.child("0001");
        Query queryUser = users.orderByChild("name").equalTo("Speeder Man");

        //cria um ouvinte
        queryUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.i("USUARIO ----->",dataSnapshot.getValue().toString());
                //User dadosUser = dataSnapshot.getValue(User.class);
                //Log.i("Dados usuario","Nume:" + dadosUser.getName() +
                   //     "Pontos :" + dadosUser.getPonts());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
