package com.example.mobileapp;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UygulamaGiris extends AppCompatActivity implements View.OnClickListener {

    Button buttonUygulamaGirisGiris;
    Button buttonUygulamaGirisKayitol;
    Button buttonAdmin;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    FirebaseUser user;
    DatabaseReference ref;
    String ad,soyad,adres,telefon,kullaniciAdi,sosyalMedya,email,resimKey;
    String kurumAdi,kurumNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uygulama_giris);

        if(!isNetworkAvailable(this)) {
            Toast.makeText(this,"Internet Baglantinizi Kontrol Edin",Toast.LENGTH_LONG).show();
        }

        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = db.getReference();






        DatabaseReference okuBagislar = db.getReference().child("version");

        okuBagislar.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String app_version = dataSnapshot.getValue().toString();
                String versionName = BuildConfig.VERSION_NAME;
                if(!(app_version.equals(versionName))){
                    alertDialog();
                }
                else{
                    if(user != null) {

                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                boolean check = dataSnapshot.child("Kullanicilar").child("Bireysel").child(user.getUid()).exists();
                                boolean check2 = dataSnapshot.child("Kullanicilar").child("Kurumsal").child(user.getUid()).exists();
                                if (check) {
                                    verileriCekBireysel(user.getUid());
                                    alertDialogGecis();
                                } if(check2){
                                    verileriCekKurumsal(user.getUid());
                                    alertDialogGecis();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                    buttonUygulamaGirisGiris = (Button) findViewById(R.id.buttonUygulamaGirisGiris);
                    buttonUygulamaGirisGiris.setOnClickListener(UygulamaGiris.this);
                    buttonUygulamaGirisKayitol = (Button) findViewById(R.id.buttonUygulamaGirisKayitol);
                    buttonUygulamaGirisKayitol.setOnClickListener(UygulamaGiris.this);
                    buttonAdmin=(Button)findViewById(R.id.adminGiris);
                    buttonAdmin.setOnClickListener(UygulamaGiris.this);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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


    private void verileriCekBireysel(final String Uid){
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        DatabaseReference okuBagislar =db.getReference().child("Kullanicilar").child("Bireysel").child(Uid);
        okuBagislar.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ad = dataSnapshot.child("ad").getValue().toString();
                soyad = dataSnapshot.child("soyad").getValue().toString();

                adres = dataSnapshot.child("adres").getValue().toString();

                telefon = dataSnapshot.child("telefon").getValue().toString();

                kullaniciAdi = dataSnapshot.child("kullaniciAdi").getValue().toString();

                sosyalMedya = dataSnapshot.child("sosyalMedya").getValue().toString();

                email = dataSnapshot.child("email").getValue().toString();

                resimKey = " ";
                try {
                    resimKey = dataSnapshot.child("resimKey").getValue().toString();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                Birey birey = new Birey(ad,soyad,adres,telefon,kullaniciAdi,sosyalMedya, email, resimKey, Uid);
                Anasayfa.currentBirey = birey;
                Anasayfa.kullaniciTipi = "Bireysel";
                Intent Anasayfa = new Intent(UygulamaGiris.this,Anasayfa.class);
                startActivity(Anasayfa);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void verileriCekKurumsal(final String Uid){
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        DatabaseReference okuBagislar =db.getReference().child("Kullanicilar").child("Kurumsal").child(Uid);
        okuBagislar.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kurumAdi = dataSnapshot.child("kurumAdi").getValue().toString();

                adres = dataSnapshot.child("adres").getValue().toString();

                kurumNo = dataSnapshot.child("kurumNo").getValue().toString();

                telefon = dataSnapshot.child("telefon").getValue().toString();

                sosyalMedya = dataSnapshot.child("sosyalMedya").getValue().toString();

                email = dataSnapshot.child("email").getValue().toString();


                resimKey = " ";
                try {
                    resimKey = dataSnapshot.child("resimKey").getValue().toString();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                Kurum kurum = new Kurum(kurumAdi,adres,kurumNo,telefon,sosyalMedya, email, resimKey, Uid);
                Anasayfa.currentKurum= kurum;
                Anasayfa.kullaniciTipi = "Kurumsal";
                Intent Anasayfa = new Intent(UygulamaGiris.this,Anasayfa.class);
                startActivity(Anasayfa);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonAdmin.getId()){
            //       Intent Anasayfa=new Intent(UygulamaGiris.this, com.example.mobileapp.Anasayfa.class);
            //     startActivity(Anasayfa);
            Toast.makeText(getApplicationContext(),"Bu Özellik Şimdilik Kullanım Dışı",Toast.LENGTH_SHORT).show();

        }
        else if(v.getId() == buttonUygulamaGirisGiris.getId()){
            Intent Giris = new Intent(UygulamaGiris.this,Giris.class);
            startActivity(Giris);
        }
        else if(v.getId() == buttonUygulamaGirisKayitol.getId()){
            Intent KayitOl = new Intent(UygulamaGiris.this,KayitOl.class);
            startActivity(KayitOl);
        }
    }
    private void alertDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Üzgünüz Bu Sürüm Artık Desteklenmiyor");
        builder1.setCancelable(false);

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void alertDialogGecis() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Giriş Yapılıyor.");
        builder1.setCancelable(false);

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
