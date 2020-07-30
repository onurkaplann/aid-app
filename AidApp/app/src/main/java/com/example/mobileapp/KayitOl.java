package com.example.mobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class KayitOl extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);

        Button buttonKayitOlBireyselkayitol = (Button) findViewById(R.id.buttonKayitOlBireyselkayitol);
        buttonKayitOlBireyselkayitol.setOnClickListener(new View.OnClickListener() { //Bireysel Kaydol Etkinliği
            @Override
            public void onClick(View v) {
                Intent BireyselKaydol = new Intent(KayitOl.this,BireyselKaydol.class);
                startActivity(BireyselKaydol);
            }
        });

        Button buttonKayitOlKurumsalkayitol = (Button) findViewById(R.id.buttonKayitOlKurumsalkayitol);
        buttonKayitOlKurumsalkayitol.setOnClickListener(new View.OnClickListener() { // Kurumsal Kaydol Etkinliği
            @Override
            public void onClick(View v) {
                Intent KurumsalKaydol = new Intent(KayitOl.this,KurumsalKaydol.class);
                startActivity(KurumsalKaydol);
//                Toast.makeText(getApplicationContext(),"Bu Özellik Şimdilik Kullanım Dışı",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
