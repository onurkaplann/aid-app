package com.example.mobileapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Giris extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    String ad,soyad,adres,telefon,kullaniciAdi,sosyalMedya,email,resimKey;
    String kurumAdi,kurumNo;
    ArrayList valuesList = new ArrayList();

    EditText edittextGirisKullaniciadi;
    String edittextGirisKullaniciaditut;

    EditText edittextGirisSifre;
    String edittextGirisSifretut;

    Button buttonGirisGiris;

    TextView textViewGirisSifremiUnuttum;
    String text;
    SpannableString ss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        mAuth = FirebaseAuth.getInstance();

        edittextGirisKullaniciadi = (EditText) findViewById(R.id.edittextGirisKullaniciadi);

        edittextGirisSifre = (EditText) findViewById(R.id.edittextGirisSifre);

        textViewGirisSifremiUnuttum = (TextView) findViewById(R.id.textViewGirisSifremiUnuttum);
        text = "Şifremi Unuttum";
        ss = new SpannableString(text);

        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent SifremiUnuttum = new Intent(Giris.this,SifremiUnuttum.class);
                startActivity(SifremiUnuttum);
            }
        };

        ss.setSpan(span,0,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewGirisSifremiUnuttum.setText(ss);
        textViewGirisSifremiUnuttum.setMovementMethod(LinkMovementMethod.getInstance());

        buttonGirisGiris = (Button) findViewById(R.id.buttonGirisGiris);
        buttonGirisGiris.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == buttonGirisGiris.getId()){

            if(!isNetworkAvailable(this)) {
                Toast.makeText(this,"Internet Baglantinizi Kontrol Edin",Toast.LENGTH_LONG).show();
            }else {

                edittextGirisKullaniciaditut = edittextGirisKullaniciadi.getText().toString(); //Kullanıcı adını Tut
                edittextGirisSifretut = edittextGirisSifre.getText().toString(); //Şifreyi Tut
                try {
                    signIn(edittextGirisKullaniciaditut, edittextGirisSifretut);
                    FirebaseUser user = mAuth.getCurrentUser();

                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }

        }

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
                Intent Anasayfa = new Intent(Giris.this,Anasayfa.class);
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
                Intent Anasayfa = new Intent(Giris.this,Anasayfa.class);
                startActivity(Anasayfa);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void signIn(final String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            final FirebaseUser user = mAuth.getCurrentUser();
                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                            final DatabaseReference ref = db.getReference();
                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    boolean check = dataSnapshot.child("Kullanicilar").child("Bireysel").child(user.getUid()).exists();
                                    boolean check2 = dataSnapshot.child("Kullanicilar").child("Kurumsal").child(user.getUid()).exists();
                                    if(check){
                                        System.out.println("aaaa");
                                        verileriCekBireysel(user.getUid());
                                    }
                                    if(check2){
                                        verileriCekKurumsal(user.getUid());
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });




                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            String Hata = ""+task.getException();
                            String[] kelime = null;
                            kelime = Hata.split(":");

                            if(kelime[1].equals(" The password is invalid or the user does not have a password.")){
                                Toast.makeText(getApplicationContext(),"Şifre Geçersiz.",Toast.LENGTH_LONG).show();
                            }

                            if(kelime[1].equals(" There is no user record corresponding to this identifier. The user may have been deleted.")){
                                Toast.makeText(getApplicationContext(),"Kullanıcı Bulunamadı.Kayıt Olabilirsiniz.",Toast.LENGTH_LONG).show();
                            }

                            if(kelime[1].equals(" The email address is badly formatted.")){
                                Toast.makeText(getApplicationContext(),"Email Formatı Hatalı.",Toast.LENGTH_LONG).show();
                            }
                        }

                        // ...
                    }
                });
    }

}
