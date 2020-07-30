package com.example.mobileapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfiliDuzenleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfiliDuzenleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfiliDuzenleFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfiliDuzenleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfiliDuzenleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfiliDuzenleFragment newInstance(String param1, String param2) {
        ProfiliDuzenleFragment fragment = new ProfiliDuzenleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    EditText editTextProfiliDuzenleAd,editTextProfiliDuzenleSoyad,editTextProfiliDuzenleEmail,
            editTextProfiliDuzenleTelefon,editTextProfiliDuzenleSifre,editTextProfiliDuzenleKullaniciadi,
            editTextProfiliDuzenleAdres,editTextProfiliDuzenleSosyalmedya, editTextProfiliDuzenleSifreDogrula;

    ImageView imageViewProfiliDuzenle;

    String editTextProfiliDuzenleAdtut,editTextProfiliDuzenleSoyadtut,editTextProfiliDuzenleEmailtut,
            editTextProfiliDuzenleTelefontut,editTextProfiliDuzenleSifretut,editTextProfiliDuzenleKullaniciaditut,
            editTextProfiliDuzenleAdrestut,editTextProfiliDuzenleSosyalmedyatut,editTextProfiliDuzenleSifreDogrulatut;

    Button buttonProfiliDuzenleGuncelle,buttonProfiliDuzenleFotograf;

    String ad,soyad,adres,telefon,kullaniciAdi,sosyalMedya,email,resimKey;

    public static Birey newBirey = new Birey();
    String resimKey2;

    FirebaseDatabase db = FirebaseDatabase.getInstance();

    private static final String TAG = "EmailPassword";

    private FirebaseAuth mAuth;
    FirebaseUser user;

    private StorageReference storageReferance;
    private final int PICK_IMAGE_REQUEST=71;
    private Uri filePath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_profili_duzenle, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();

        editTextProfiliDuzenleAd=(EditText)RootView.findViewById(R.id.editTextProfiliDuzenleAd);
        editTextProfiliDuzenleEmail=(EditText)RootView.findViewById(R.id.editTextProfiliDuzenleEmail);
        editTextProfiliDuzenleSoyad=(EditText)RootView.findViewById(R.id.editTextProfiliDuzenleSoyad);
        editTextProfiliDuzenleTelefon=(EditText)RootView.findViewById(R.id.editTextProfiliDuzenleTelefon);
        editTextProfiliDuzenleKullaniciadi=(EditText)RootView.findViewById(R.id.editTextProfiliDuzenleKullaniciadi);
        editTextProfiliDuzenleAdres=(EditText)RootView.findViewById(R.id.editTextProfiliDuzenleAdres);
        editTextProfiliDuzenleSosyalmedya=(EditText)RootView.findViewById(R.id.editTextProfiliDuzenleSosyalmedya);
        editTextProfiliDuzenleSifre=(EditText)RootView.findViewById(R.id.editTextProfiliDuzenleSifre);
        editTextProfiliDuzenleSifreDogrula = (EditText) RootView.findViewById(R.id.editTextProfiliDuzenleSifreDogrula);
        imageViewProfiliDuzenle=(ImageView)RootView.findViewById(R.id.imageViewProfiliDuzenle);
        buttonProfiliDuzenleGuncelle=(Button)RootView.findViewById(R.id.buttonProfiliDuzenleGuncelle);
        buttonProfiliDuzenleGuncelle.setOnClickListener(this);
        buttonProfiliDuzenleFotograf=(Button)RootView.findViewById(R.id.buttonProfiliDuzenleFotograf);
        buttonProfiliDuzenleFotograf.setOnClickListener(this);

        verileriCekBireysel(user.getUid());

        user = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();


        storageReferance = FirebaseStorage.getInstance().getReference();

        return RootView;
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==buttonProfiliDuzenleGuncelle.getId()){
            editTextProfiliDuzenleAdtut = editTextProfiliDuzenleAd.getText().toString();
            editTextProfiliDuzenleSoyadtut = editTextProfiliDuzenleSoyad.getText().toString();
            editTextProfiliDuzenleAdrestut = editTextProfiliDuzenleAdres.getText().toString();
            editTextProfiliDuzenleTelefontut = editTextProfiliDuzenleTelefon.getText().toString();
            editTextProfiliDuzenleKullaniciaditut = editTextProfiliDuzenleKullaniciadi.getText().toString();
            editTextProfiliDuzenleSosyalmedyatut = editTextProfiliDuzenleSosyalmedya.getText().toString();
            editTextProfiliDuzenleEmailtut = editTextProfiliDuzenleEmail.getText().toString();
            editTextProfiliDuzenleSifretut = editTextProfiliDuzenleSifre.getText().toString();
            editTextProfiliDuzenleSifreDogrulatut = editTextProfiliDuzenleSifreDogrula.getText().toString();

            if (!(editTextProfiliDuzenleSifreDogrulatut.equals(editTextProfiliDuzenleSifretut))){
                Toast.makeText(getContext(),"Girilen Şifreler Eşleşmiyor!",Toast.LENGTH_LONG).show();
                return;
            }

            DatabaseReference data1 = db.getReference().child("Kullanicilar").child("Bireysel").child(user.getUid());

            data1.child("ad").setValue(editTextProfiliDuzenleAdtut);

            data1.child("soyad").setValue(editTextProfiliDuzenleSoyadtut);

            data1.child("adres").setValue(editTextProfiliDuzenleAdrestut);

            data1.child("telefon").setValue(editTextProfiliDuzenleTelefontut);

            data1.child("kullaniciAdi").setValue(editTextProfiliDuzenleKullaniciaditut);

            data1.child("sosyalMedya").setValue(editTextProfiliDuzenleSosyalmedyatut);

            System.out.println("aaa"+resimKey);

                if (imageViewProfiliDuzenle.getDrawable() != null || resimKey != " ") {

                    data1.child("resimKey").setValue(user.getUid() + ".jpg");
                    resimKey2 = (user.getUid() + ".jpg");

                } else {
                    resimKey2 = " ";
                }


            newBirey = new Birey(editTextProfiliDuzenleAdtut,editTextProfiliDuzenleSoyadtut,editTextProfiliDuzenleAdrestut,editTextProfiliDuzenleTelefontut,editTextProfiliDuzenleKullaniciaditut,editTextProfiliDuzenleSosyalmedyatut,editTextProfiliDuzenleEmailtut,resimKey2,user.getUid());

            Anasayfa.currentBirey = newBirey;

            user.updateEmail(editTextProfiliDuzenleEmailtut)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User email address updated.");
                            }
                        }
                    });


            String newPassword = editTextProfiliDuzenleSifretut;
            if(!editTextProfiliDuzenleSifretut.equals("")) {
                user.updatePassword(newPassword)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User password updated.");
                                } else {
                                    String Hata = "" + task.getException();

                                    String[] kelime = null;
                                    kelime = Hata.split(":");
                                    if (kelime[1].equals(" The given password is invalid. [ Password should be at least 6 characters ]")) {
                                        Toast.makeText(getContext(), "Şifre En Az 6 Haneli Olmalı.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });
            }
            uploadFile(user.getUid());

            Toast.makeText(getContext(),"Verileriniz Güncellenmiştir.",Toast.LENGTH_LONG).show();
        }else if(view.getId() == buttonProfiliDuzenleFotograf.getId()){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction((Intent.ACTION_GET_CONTENT));
            startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
        }
    }


    private Boolean uploadFile(String Uid){

        boolean returned = false;

        if((BitmapDrawable) imageViewProfiliDuzenle.getDrawable() != null){


            imageViewProfiliDuzenle.setDrawingCacheEnabled(true);
            imageViewProfiliDuzenle.buildDrawingCache();
            Bitmap bitmap2 =((BitmapDrawable) imageViewProfiliDuzenle.getDrawable()).getBitmap();
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

    };
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

    public void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),filePath);
                imageViewProfiliDuzenle.setImageBitmap(bitmap);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    private void verileriCekBireysel(String Uid){
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        DatabaseReference okuBagislar =db.getReference().child("Kullanicilar").child("Bireysel").child(Uid);
        okuBagislar.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ad = dataSnapshot.child("ad").getValue().toString();
                editTextProfiliDuzenleAd.setText(ad);

                soyad = dataSnapshot.child("soyad").getValue().toString();
                editTextProfiliDuzenleSoyad.setText(soyad);

                adres = dataSnapshot.child("adres").getValue().toString();
                editTextProfiliDuzenleAdres.setText(adres);

                telefon = dataSnapshot.child("telefon").getValue().toString();
                editTextProfiliDuzenleTelefon.setText(telefon);

                kullaniciAdi = dataSnapshot.child("kullaniciAdi").getValue().toString();
                editTextProfiliDuzenleKullaniciadi.setText(kullaniciAdi);

                sosyalMedya = dataSnapshot.child("sosyalMedya").getValue().toString();
                editTextProfiliDuzenleSosyalmedya.setText(sosyalMedya);

                email = dataSnapshot.child("email").getValue().toString();
                editTextProfiliDuzenleEmail.setText(email);



                resimKey = " ";
                try {
                    resimKey = dataSnapshot.child("resimKey").getValue().toString();
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
