package com.example.mobileapp;

import android.content.Context;


public class ButonOlustur extends android.support.v7.widget.AppCompatButton {
    public ButonOlustur(Context context, String veri){
        super(context);
        //Buton tasarımı vs için super(context,attributes) ekle attributes.xml yaz
        this.setText(veri);


    }

}
