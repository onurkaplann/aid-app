package com.example.mobileapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class KurumsalKaydol extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST=71;
    private StorageReference storageReferance;

    EditText edittextKurumsalKaydolKurumadi;
    String edittextKurumsalKaydolKurumaditut;

    EditText edittextKurumsalKaydolKurumno;
    String edittextKurumsalKaydolKurumnotut;

    EditText edittextKurumsalKaydolEmail;
    String edittextKurumsalKaydolEmailtut;

    EditText edittextKurumsalKaydolAdres;
    String edittextKurumsalKaydolAdrestut;

    EditText edittextKurumsalKaydolTelefon;
    String edittextKurumsalKaydolTelefontut;

    EditText edittextKurumsalKaydolSifre;
    String edittextKurumsalKaydolSifretut;

    EditText edittextKurumsalKaydolSifreDogrula;
    String edittextKurumsalKaydolSifreDogrulatut;

    EditText edittextKurumsalKaydolSosyalmedya;
    String edittextKurumsalKaydolSosyalmedyatut;

    Button buttonKurumsalKaydolKayitol;
    Button buttonKurumsalKaydolFotograf;;

    ImageView imageviewKurumsalKaydolFotograf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kurumsal_kaydol);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //mAuth.signOut();

        storageReferance = FirebaseStorage.getInstance().getReference();

        edittextKurumsalKaydolKurumadi = (EditText) findViewById(R.id.edittextKurumsalKaydolKurumadi);

        edittextKurumsalKaydolKurumno = (EditText) findViewById(R.id.edittextKurumsalKaydolKurumno);


        edittextKurumsalKaydolEmail = (EditText) findViewById(R.id.edittextKurumsalKaydolEmail);


        edittextKurumsalKaydolAdres = (EditText) findViewById(R.id.edittextKurumsalKaydolAdres);


        edittextKurumsalKaydolTelefon = (EditText) findViewById(R.id.edittextKurumsalKaydolTelefon);
        //edittextBireyselKaydolTelefon.addTextChangedListener(new PhoneNumberFormattingTextWatcher()); //Bunu bir dene


        edittextKurumsalKaydolSifre = (EditText) findViewById(R.id.edittextKurumsalKaydolSifre);

        edittextKurumsalKaydolSifreDogrula = (EditText) findViewById(R.id.edittextKurumsalKaydolSifreDogrula);


        edittextKurumsalKaydolSosyalmedya = (EditText) findViewById(R.id.edittextKurumsalKaydolSosyalmedya);

        imageviewKurumsalKaydolFotograf = (ImageView) findViewById(R.id.imageviewKurumsalKaydolFotograf);

        buttonKurumsalKaydolKayitol = (Button) findViewById(R.id.buttonKurumsalKaydolKayitol);
        buttonKurumsalKaydolKayitol.setOnClickListener(this);

        buttonKurumsalKaydolFotograf = (Button) findViewById(R.id.buttonKurumsalKaydolFotograf);
        buttonKurumsalKaydolFotograf.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonKurumsalKaydolKayitol.getId()) {

            edittextKurumsalKaydolEmailtut = edittextKurumsalKaydolEmail.getText().toString();//emaili tut
            edittextKurumsalKaydolSifretut = edittextKurumsalKaydolSifre.getText().toString(); //Şifreyi tut
            edittextKurumsalKaydolSifreDogrulatut = edittextKurumsalKaydolSifreDogrula.getText().toString();

            if(!(edittextKurumsalKaydolSifreDogrulatut.equals(edittextKurumsalKaydolSifretut))){
                Toast.makeText(getApplicationContext(),"Girilen Şifreler Eşleşmiyor!",Toast.LENGTH_LONG).show();
                return;
            }
            else{
                if(!isNetworkAvailable(this)) {
                    Toast.makeText(this, "Internet Baglantinizi Kontrol Edin", Toast.LENGTH_LONG).show();
                }
                else{
                    if(edittextKurumsalKaydolEmailtut.equals("") && edittextKurumsalKaydolSifretut.equals("")){
                        Toast.makeText(getApplicationContext(),"Email ve Şifre Giriniz.",Toast.LENGTH_LONG).show();
                    }else{
                        createAccount(edittextKurumsalKaydolEmailtut, edittextKurumsalKaydolSifretut);
                    }
                }
            }

        }

        else if(v.getId() == buttonKurumsalKaydolFotograf.getId()){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction((Intent.ACTION_GET_CONTENT));
            startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
        }

    }

    public void pushUser2DB(String Uid,String Email){

        edittextKurumsalKaydolKurumaditut = edittextKurumsalKaydolKurumadi.getText().toString();//Kurum adı tut
        edittextKurumsalKaydolKurumnotut = edittextKurumsalKaydolKurumno.getText().toString();//Kurum no tut
        edittextKurumsalKaydolAdrestut = edittextKurumsalKaydolAdres.getText().toString();//Adresi tut
        edittextKurumsalKaydolTelefontut = edittextKurumsalKaydolTelefon.getText().toString(); //Telefonu tut
        edittextKurumsalKaydolSosyalmedyatut = edittextKurumsalKaydolSosyalmedya.getText().toString();

        Kurum kurum;
        boolean returned = uploadFile(Uid);

        if(returned == true){
            kurum = new Kurum(edittextKurumsalKaydolKurumaditut, edittextKurumsalKaydolAdrestut, edittextKurumsalKaydolKurumnotut, edittextKurumsalKaydolTelefontut, edittextKurumsalKaydolSosyalmedyatut, edittextKurumsalKaydolEmailtut,(Uid+".jpg") , Uid);
        }
        else {
            kurum = new Kurum(edittextKurumsalKaydolKurumaditut, edittextKurumsalKaydolAdrestut, edittextKurumsalKaydolKurumnotut, edittextKurumsalKaydolTelefontut, edittextKurumsalKaydolSosyalmedyatut, edittextKurumsalKaydolEmailtut ," ", Uid);
        }
        DatabaseReference kullaniciEkle =db.getReference().child("Kullanicilar").child("Kurumsal");
        kullaniciEkle.child(Uid).setValue(kurum);

        Intent Anasayfa = new Intent(KurumsalKaydol.this,Anasayfa.class);
        com.example.mobileapp.Anasayfa.currentKurum = kurum;
        com.example.mobileapp.Anasayfa.kullaniciTipi = "Kurumsal";
        startActivity(Anasayfa);
    }



    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);

        //  showProgressDialog();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(getApplicationContext(), "Giriş Yapılıyor Lütfen Bekleyiniz.", Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            pushUser2DB(user.getUid(),user.getEmail());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            String Hata = ""+task.getException();

                            String[] kelime = null;
                            kelime = Hata.split(":");

                            if(kelime[1].equals(" The email address is badly formatted.")){
                                Toast.makeText(getApplicationContext(),"Email Formatı Hatalı.",Toast.LENGTH_LONG).show();
                            }

                            if(kelime[1].equals(" The given password is invalid. [ Password should be at least 6 characters ]")){
                                Toast.makeText(getApplicationContext(),"Şifre En Az 6 Haneli Olmalı.",Toast.LENGTH_LONG).show();
                            }

                            if(kelime[1].equals(" The email address is already in use by another account.")){
                                Toast.makeText(getApplicationContext(),"Bu Mail Adresi ile Başka Bir Kayıt Bulunmaktadır.",Toast.LENGTH_LONG).show();
                            }

                        }

                        // ...
                    }
                });
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }

    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imageviewKurumsalKaydolFotograf.setImageBitmap(bitmap);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    private boolean uploadFile(String Uid){
        boolean returned = false;
        if((BitmapDrawable) imageviewKurumsalKaydolFotograf.getDrawable() != null){


            imageviewKurumsalKaydolFotograf.setDrawingCacheEnabled(true);
            imageviewKurumsalKaydolFotograf.buildDrawingCache();
            Bitmap bitmap2 =((BitmapDrawable) imageviewKurumsalKaydolFotograf.getDrawable()).getBitmap();
            Bitmap bitmap= null;
            bitmap =  getResizedBitmap(bitmap2,500,500);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);



            byte[] data = baos.toByteArray();

            StorageReference riversRef = storageReferance.child("kullanicilar/"+Uid+".jpg");
            UploadTask uploadTask = riversRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                }
            });

            returned = true;
        }

        return returned;
    }
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {

        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

}
