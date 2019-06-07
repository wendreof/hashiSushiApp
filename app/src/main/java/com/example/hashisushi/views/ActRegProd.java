package com.example.hashisushi.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hashisushi.R;
import com.example.hashisushi.dao.FirebaseConfig;
import com.example.hashisushi.dao.ProductDao;
import com.example.hashisushi.model.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.UUID;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActRegProd extends AppCompatActivity implements View.OnClickListener{

    private String[] type = { "Entrada","Pratos_Quentes","Pratos_Frios",
            "Temakis","Bebidas","Combo"};
    private String[] isPromotion = { "Não","Sim"};
    private Spinner spnType;
    private Spinner spnIsPrmotion;

    private EditText edtDiscriptionProd;
    private EditText edtNumberPro;
    private EditText edtNameProd;
    private EditText edtValProd;
    private EditText edtUrl;

    private ImageView imgVwProduction;

    private FloatingActionButton flotBntHomeReg;
    private FloatingActionButton flotBntSaveReg;
    private FloatingActionButton flotBntNewReg;
    private FloatingActionButton flotBntExitReg;

    private TextView txtTitleReg;
    private TextView txtRegisterProd;

    private static final int SELECAO_GALERIA = 200;
    private StorageReference storageReference;
    private DatabaseReference firebaseReference;
    private String idUsuarioLogado;
    private String urlImagemSelecionada = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_reg_prod);
        getSupportActionBar().hide();

        startCompnent();

        isPromotionSpn();
        typeSpn();
        fontLogo();
        startDB();

        flotBntHomeReg.setOnClickListener(this);
        flotBntSaveReg.setOnClickListener(this);
        flotBntNewReg.setOnClickListener(this);
        flotBntExitReg.setOnClickListener(this);

        imgVwProduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVibrate(90);
                getImage();
            }
        });

    }

    private void recoverDataProduction(String id){


        //retorna usuarios
        final DatabaseReference productDB = firebaseReference.child("product");

        Query querySearch = productDB.orderByChild("idProd").equalTo(id);

        //cria um ouvinte
        querySearch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if( dataSnapshot.getValue() != null ){
                    Product product = dataSnapshot.getValue(Product.class);

                    edtNameProd.setText(product.getName());
                    edtDiscriptionProd.setText(product.getDescription());
                    String srtSalePrice = String.valueOf(product.getSalePrice());
                    edtValProd.setText(srtSalePrice);
                    edtNumberPro.setText(product.getIdProd());
                  //  spnIsPrmotion.setSelected(product.getPromotion());
                    // spnType.setSelection();
                    edtUrl.setText(product.getImgUrl());

                    urlImagemSelecionada = product.getImgUrl();
                    if( urlImagemSelecionada != "" ){
                        Picasso.get()
                                .load(urlImagemSelecionada)
                                .into(imgVwProduction);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    private void getImage(){

        String idProd = edtNumberPro.getText().toString();
        if(idProd.equals("")){

            msgShort("Defina um valor para Nº do produto.");

        }else {
            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            );
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, SELECAO_GALERIA);
            }
        }
    }

    private void startDB(){
        storageReference = FirebaseConfig.getFirebaseStorage();
        firebaseReference = FirebaseConfig.getFirebase();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    //Altera fonte do txtLogo
    private void fontLogo(){

        Typeface font = Typeface.createFromAsset(getAssets(), "RagingRedLotusBB.ttf");
        txtRegisterProd.setTypeface(font);
    }

    private void startCompnent(){

        spnType = findViewById(R.id.spnType);
        spnIsPrmotion = findViewById(R.id.spnIsPrmotion);

        edtDiscriptionProd = findViewById(R.id.edtDiscriptionProd);
        edtNumberPro = findViewById(R.id.edtNumberPro);
        edtNameProd = findViewById(R.id.edtNameProd);
        edtValProd = findViewById(R.id.edtValProd);
        edtUrl = findViewById(R.id.edtUrl);

        txtTitleReg = findViewById(R.id.txtTitleReg);
        txtRegisterProd = findViewById(R.id.txtRegisterProd);

        imgVwProduction = findViewById(R.id.imgVwProduction);

        flotBntHomeReg = findViewById(R.id.flotBntHomeReg);
        flotBntSaveReg = findViewById(R.id.flotBntSaveReg);
        flotBntNewReg = findViewById(R.id.flotBntNewReg);
        flotBntExitReg = findViewById(R.id.flotBntExitReg);
    }

    private void isPromotionSpn(){
        try {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,isPromotion);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnIsPrmotion.setAdapter(adapter);
        }catch (Exception ex){
            msgShort("Erro:-->"+ex.getMessage());
        }
    }

    private void typeSpn(){
        try {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,type);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnType.setAdapter(adapter);
        }catch (Exception ex){
            msgShort("Erro:-->"+ex.getMessage());
        }
    }

    private void msgShort(String msg) {

        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

    //Metudo que ativa vibração
    public void startVibrate(long time) {
        // cria um obj atvib que recebe seu valor de context
        Vibrator atvib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        atvib.vibrate(time);
    }

    @Override
    public void onClick(View v) {


        if ( v.getId() == R.id.flotBntHomeReg) {

            startVibrate(90);
            Intent it = new Intent( this, ActPromotion.class );
            startActivity( it );
        }
        if ( v.getId() == R.id.flotBntNewReg ) {

            startVibrate(90);


        }if(v.getId() == R.id.flotBntSaveReg){

            startVibrate(90);
            addProd();
        } if(v.getId() == R.id.flotBntExitReg) {

            startVibrate(90);
            finish();

        }
    }

    private void addProd() {

          String descricao =  edtDiscriptionProd.getText().toString();
          String numberProd =  edtNumberPro.getText().toString();
          String name = edtNameProd.getText().toString();
          String valProd  = edtValProd.getText().toString();

        if(descricao.equals("") && numberProd.equals("") && name.equals("") && valProd.equals("")){
            msgShort("Há campos sem valor;preencha todos os campos !");
        }else {
            try {
                Product p = new Product();

                p.setName(edtNameProd.getText().toString());
                p.setDescription(edtDiscriptionProd.getText().toString());
                p.setSalePrice(edtValProd.getText().toString());
                p.setIdProd(edtNumberPro.getText().toString());

                String strProm = spnIsPrmotion.getSelectedItem().toString();

                boolean bolProm  = false;
                if(strProm.equals("Sim")){
                    bolProm = true;
                }else {
                    bolProm = false;
                }
                p.setPromotion(bolProm);

                String strType = spnType.getSelectedItem().toString();
                p.setType(strType);
                p.setImgUrl(edtUrl.getText().toString());

                ProductDao productDao = new ProductDao();
                productDao.addProduct(p);
                msgShort("Produto salvo com sucesso!..");

                //Snackbar.make(ActRegProd, R.string.registration_completed, Snackbar.LENGTH_LONG).show();
            } catch (Exception erro) {
                msgShort("Erro na gravação de produto ERRO : " + erro);
                // Snackbar.make(ActRegProd, R.string.registration_error , Snackbar.LENGTH_LONG).show();

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( resultCode == RESULT_OK){
            Bitmap imagem = null;

            try {

                switch (requestCode) {
                    case SELECAO_GALERIA:
                        Uri localImagem = data.getData();
                        imagem = MediaStore.Images
                                .Media
                                .getBitmap(
                                        getContentResolver(),
                                        localImagem
                                );
                        break;
                }

                if( imagem != null){

                    imgVwProduction.setImageBitmap( imagem );

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    byte[] dadosImagem = baos.toByteArray();

                    String nameImagem = UUID.randomUUID().toString();
                    StorageReference imagemRef = storageReference
                            .child("produtos")
                            .child("img")
                            .child(nameImagem + "jpeg");

                    final UploadTask uploadTask = imagemRef.putBytes( dadosImagem );
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            msgShort( "Erro ao fazer upload da imagem !");
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                           urlImagemSelecionada = taskSnapshot.getDownloadUrl().toString();

                           edtUrl.setText(urlImagemSelecionada);
                            msgShort("Sucesso ao fazer upload da imagem !");
                        }
                    });

                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

}
