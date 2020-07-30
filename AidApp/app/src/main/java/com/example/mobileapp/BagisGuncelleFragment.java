package com.example.mobileapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
 * {@link BagisGuncelleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BagisGuncelleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BagisGuncelleFragment extends Fragment implements  View.OnClickListener{

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST=71;
    private StorageReference storageReferance;

    Bagis bagis;
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BagisGuncelleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BagisGuncelleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BagisGuncelleFragment newInstance(String param1, String param2) {
        BagisGuncelleFragment fragment = new BagisGuncelleFragment();
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

    EditText editTextBagisGuncelleBaslik;
    EditText editTextBagisGuncelleBilgi;
    EditText editTextBagisGuncelleSmsAdres;
    EditText editTextBagisGuncelleSmsMetin;
    EditText editTextBagisGuncelleOzet;
    Button buttonBagisGuncelle;
    Button buttonBagisGuncelleFotograf;
    ImageView imageviewBagisGuncelle;

    String editTextBagisGuncelleBasliktut;
    String editTextBagisGuncelleBilgitut;
    String editTextBagisGuncelleSmsAdrestut;
    String editTextBagisGuncelleSmsMetintut;
    String editTextBagisGuncelleOzettut;

    String baslik, bilgi,ozet, smsAdres, smsMetin,resimKey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View RootView = inflater.inflate(R.layout.fragment_bagis_guncelle, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        storageReferance = FirebaseStorage.getInstance().getReference();

        editTextBagisGuncelleBaslik = (EditText)RootView.findViewById(R.id.editTextBagisGuncelleBaslik);
        editTextBagisGuncelleBilgi = (EditText)RootView.findViewById(R.id.editTextBagisGuncelleBilgi);
        editTextBagisGuncelleSmsAdres = (EditText)RootView.findViewById(R.id.editTextBagisGuncelleSmsAdresi);
        editTextBagisGuncelleSmsMetin = (EditText)RootView.findViewById(R.id.editTextBagisGuncelleSmsMetni);
        editTextBagisGuncelleOzet = (EditText)RootView.findViewById(R.id.editTextBagisGuncelleOzet);
        buttonBagisGuncelle = (Button)RootView.findViewById(R.id.buttonBagisGuncelle);
        buttonBagisGuncelle.setOnClickListener(this);
        buttonBagisGuncelleFotograf = (Button)RootView.findViewById(R.id.buttonBagisGuncelleFotograf);
        buttonBagisGuncelleFotograf.setOnClickListener(this);
        imageviewBagisGuncelle = (ImageView)RootView.findViewById(R.id.imageViewBagisGuncelle);

        verileriCekBagis();

        return RootView;
    }

    private void verileriCekBagis(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        DatabaseReference okuBagislar =db.getReference().child("Bagislar").child(bagis.getBagisid());
        okuBagislar.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                baslik = dataSnapshot.child("baslik").getValue().toString();
                editTextBagisGuncelleBaslik.setText(baslik);

                bilgi = dataSnapshot.child("bilgi").getValue().toString();
                editTextBagisGuncelleBilgi.setText(bilgi);

                ozet = dataSnapshot.child("ozet").getValue().toString();
                editTextBagisGuncelleOzet.setText(ozet);

                smsAdres = dataSnapshot.child("smsAdres").getValue().toString();
                editTextBagisGuncelleSmsAdres.setText(smsAdres);

                smsMetin = dataSnapshot.child("smsMetin").getValue().toString();
                editTextBagisGuncelleSmsMetin.setText(smsMetin);

                resimKey = " ";

                try {
                    resimKey = dataSnapshot.child("resimKey").getValue().toString();
                }catch (Exception e){
                    e.printStackTrace();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == buttonBagisGuncelle.getId()) {
            editTextBagisGuncelleBasliktut = editTextBagisGuncelleBaslik.getText().toString();
            editTextBagisGuncelleBilgitut = editTextBagisGuncelleBilgi.getText().toString();
            editTextBagisGuncelleSmsAdrestut = editTextBagisGuncelleSmsAdres.getText().toString();
            editTextBagisGuncelleSmsMetintut = editTextBagisGuncelleSmsMetin.getText().toString();
            editTextBagisGuncelleOzettut = editTextBagisGuncelleOzet.getText().toString();

            DatabaseReference data1 = db.getReference().child("Bagislar").child(bagis.getBagisid());

            data1.child("baslik").setValue(editTextBagisGuncelleBasliktut);
            data1.child("bilgi").setValue(editTextBagisGuncelleBilgitut);
            data1.child("smsAdres").setValue(editTextBagisGuncelleSmsAdrestut);
            data1.child("smsMetin").setValue(editTextBagisGuncelleSmsMetintut);
            data1.child("ozet").setValue(editTextBagisGuncelleOzettut);

            if (imageviewBagisGuncelle.getDrawable() != null || resimKey != " ") {
                data1.child("resimKey").setValue(bagis.bagisid + ".jpg");
            }

            if(uploadFile()){
                Toast.makeText(getContext(),"Verileriniz Güncellenmiştir.",Toast.LENGTH_LONG).show();
            }

        }
        if(v.getId() == buttonBagisGuncelleFotograf.getId()){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction((Intent.ACTION_GET_CONTENT));
            startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
        }
    }




    public void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),filePath);
                imageviewBagisGuncelle.setImageBitmap(bitmap);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    private boolean uploadFile(){
        boolean returned = false;
        if((BitmapDrawable) imageviewBagisGuncelle.getDrawable() != null){


            imageviewBagisGuncelle.setDrawingCacheEnabled(true);
            imageviewBagisGuncelle.buildDrawingCache();
            Bitmap bitmap2 =((BitmapDrawable) imageviewBagisGuncelle.getDrawable()).getBitmap();
            Bitmap bitmap= null;
            bitmap =  getResizedBitmap(bitmap2,500,500);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);



            byte[] data = baos.toByteArray();

            StorageReference riversRef = storageReferance.child("images/"+bagis.getBagisid()+".jpg");
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
