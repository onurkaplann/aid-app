package com.example.mobileapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class SifremiUnuttum extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    EditText edittextSifremiUnuttumEmail;
    String edittextSifremiUnuttumEmailtut;

    Button buttonSifremiUnuttumGonder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifremi_unuttum);


        Spinner spinnerSifremiUnuttum = (Spinner) findViewById(R.id.spinnerSifremiUnuttum);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(SifremiUnuttum.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.spinnerSifremiUnuttum));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSifremiUnuttum.setAdapter(myAdapter);

        String spinnerSifremiUnuttumtut = spinnerSifremiUnuttum.getSelectedItem().toString(); //Bireysel-Kurumsal Değerini Tut

         edittextSifremiUnuttumEmail = (EditText) findViewById(R.id.editTextSifremiUnuttumEmail);

        buttonSifremiUnuttumGonder = (Button) findViewById(R.id.buttonSifremiUnuttumGonder);

        buttonSifremiUnuttumGonder.setOnClickListener(new View.OnClickListener() { //Gönder Butonu Etkinliği
            @Override
            public void onClick(View v) {

                edittextSifremiUnuttumEmailtut = edittextSifremiUnuttumEmail.getText().toString();//Kullanıcı adını tut
                reset(edittextSifremiUnuttumEmailtut);
                Toast.makeText(getApplicationContext(),"Lütfen Emailini Adresinizi Kontrol Ediniz.",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void reset(String tut){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = tut;

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
    }
}
