package com.example.hashisushi.dao;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UserFirebase {

    public static String getIdUser(){
        FirebaseAuth auth = FirebaseConfig.getFirebaseAutentic();
        return auth.getCurrentUser().getUid();
    }

    public static FirebaseUser getUserCorrent(){
        FirebaseAuth usuario = FirebaseConfig.getFirebaseAutentic();
        return usuario.getCurrentUser();
    }

    public static boolean atualizarTipoUsuario(String tipo){

        try {

            FirebaseUser user = getUserCorrent();
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(tipo)
                    .build();
            user.updateProfile(profile);
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
