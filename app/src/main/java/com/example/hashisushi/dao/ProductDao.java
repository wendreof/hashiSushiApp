package com.example.hashisushi.dao;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.hashisushi.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private DatabaseReference reference ;
    private List<Product> products = new ArrayList<Product>();

    public ProductDao() {
        this.reference = FirebaseDatabase.getInstance().getReference();
       //FirebaseApp.initializeApp(Act);
    }
    
    public void addProduct(Product product) {

        DatabaseReference productsDB = reference.child("product");

        try {
            // push cria um novo no
            productsDB.push().setValue(product);
            System.out.printf("ERRO-produto não foi salvo !");
        } catch (Exception erro) {
            System.out.printf("Atenção produto não foi salvo ! ERRO :"+erro);
        }
    }

    public void searchProduct(){
        //retorna usuarios
        DatabaseReference product = reference.child("product");
        //retorna o no setado
        // DatabaseReference usersSearch = users.child("0001");
        Query querySearch = product.orderByChild("type").equalTo("Entrada");


        //cria um ouvinte
        querySearch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.i("PRODUTOS ----->",dataSnapshot.getValue().toString());
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