package com.example.mobileapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ResimIndir {
    static Bitmap bmp;

    public static Bitmap getImage(final String resimKey){
        StorageReference storageReferance = FirebaseStorage.getInstance().getReference();
        final StorageReference islandRef = storageReferance.child("images").child(resimKey+".jpg");
        System.out.println("island:" + islandRef.toString());
        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap tempBmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                bmp = tempBmp;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        return bmp;
    }
}
