package design.wendreo.hashisushi.dao;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseConfig {
	
	private static DatabaseReference databaseReference;
	private static FirebaseAuth authFire;
	private static StorageReference storageReference;
	
	public static String getIdUsuario ( ) {
		FirebaseAuth autenticacao = getFirebaseAutentic ( );
		return autenticacao.getCurrentUser ( ).getUid ( );
	}
	
	//retorna a referencia do database
	public static DatabaseReference getFirebase ( ) {
		if ( databaseReference == null ) {
			databaseReference = FirebaseDatabase.getInstance ( ).getReference ( );
		}
		return databaseReference;
	}
	
	//retorna a instancia do FirebaseAuth
	public static FirebaseAuth getFirebaseAutentic ( ) {
		if ( authFire == null ) {
			authFire = FirebaseAuth.getInstance ( );
		}
		return authFire;
	}
	
	//Retorna instancia do FirebaseStorage
	public static StorageReference getFirebaseStorage ( ) {
		if ( storageReference == null ) {
			storageReference = FirebaseStorage.getInstance ( ).getReference ( );
			
		}
		return storageReference;
	}
}
