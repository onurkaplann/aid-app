package com.example.mobileapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static com.example.mobileapp.ResimIndir.bmp;

public class ObjeGorsel extends android.support.v7.widget.AppCompatImageView  {
    public ObjeGorsel(Context context, String resimKey) {
        super(context);
        if(resimKey == null){
            this.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_launcher_foreground));;
        }
        else {
            Bitmap bmp2;
            StorageReference storageReferance  = FirebaseStorage.getInstance().getReference();
            final StorageReference islandRef = storageReferance.child("images").child(resimKey);
            final long ONE_MEGABYTE = 1024 * 1024;
            this.setAdjustViewBounds(true);
            islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    ObjeGorsel.this.setImageBitmap(bmp);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });

        }
    }

    public ObjeGorsel(Context context, String resimKey,int i) {
        super(context);
        if(resimKey == null){
            this.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_launcher_foreground));;
        }
        else {
            Bitmap bmp2;
            StorageReference storageReferance  = FirebaseStorage.getInstance().getReference();
            final StorageReference islandRef = storageReferance.child("kullanicilar").child(resimKey);
            final long ONE_MEGABYTE = 1024 * 1024;
            this.setAdjustViewBounds(true);
            islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    ObjeGorsel.this.setImageBitmap(bmp);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });

        }
    }

}
