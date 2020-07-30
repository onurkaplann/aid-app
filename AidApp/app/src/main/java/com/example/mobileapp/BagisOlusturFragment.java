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
import android.support.v4.app.FragmentTransaction;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
 * {@link BagisOlusturFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BagisOlusturFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BagisOlusturFragment extends Fragment implements  View.OnClickListener{

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST=71;
    private StorageReference storageReferance;

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference yazBagislar =db.getReference().child("Bagislar");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText editTextBagisOlusturBaslik;
    EditText editTextBagisOlusturBilgi;
    EditText editTextBagisOlusturSmsAdres;
    EditText editTextBagisOlusturSmsMetin;
    EditText editTextBagisOlusturOzet;
    ImageView imageviewBagisOlusturFotograf;
    Button buttonBagisOlusturOlustur;
    Button buttonBagisOlusturFotograf;
    String bagisKey = yazBagislar.push().getKey();


    private OnFragmentInteractionListener mListener;

    public BagisOlusturFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BagisOlusturFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BagisOlusturFragment newInstance(String param1, String param2) {
        BagisOlusturFragment fragment = new BagisOlusturFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_bagis_olustur, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        storageReferance = FirebaseStorage.getInstance().getReference();

        editTextBagisOlusturBaslik = RootView.findViewById(R.id.editTextBagisOlusturBaslik);
        editTextBagisOlusturBilgi = RootView.findViewById(R.id.editTextBagisOlusturBilgi);
        editTextBagisOlusturSmsAdres = RootView.findViewById(R.id.editTextBagisOlusturSmsAdres);
        editTextBagisOlusturSmsMetin = RootView.findViewById(R.id.editTextBagisOlusturSmsMetin);
        editTextBagisOlusturOzet = RootView.findViewById(R.id.editTextBagisOlusturOzet);
        imageviewBagisOlusturFotograf = RootView.findViewById(R.id.imageviewBagisOlusturFotograf);


        buttonBagisOlusturOlustur = RootView.findViewById(R.id.buttonBagisOlusturEkle);

        buttonBagisOlusturOlustur.setOnClickListener(this);

        buttonBagisOlusturFotograf = RootView.findViewById(R.id.buttonBagisOlusturFotograf);

        buttonBagisOlusturFotograf.setOnClickListener(this);


        // Inflate the layout for this fragment
        return RootView;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == buttonBagisOlusturOlustur.getId()) {
            if(!isNetworkAvailable(getContext())) {
                Toast.makeText(getContext(),"Internet Baglantinizi Kontrol Edin",Toast.LENGTH_LONG).show();
            }else {
                uploadFile();
            }
        }
        if(v.getId() == buttonBagisOlusturFotograf.getId()){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction((Intent.ACTION_GET_CONTENT));
            startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
        }
    }

    public void dbBagisEkleBireysel(boolean resimCheck){
        try{
            final DatabaseReference kullanici = db.getReference().child("Kullanicilar").child("Bireysel").child(user.getUid()).child("Bagislarim");
            String baslik = "" + editTextBagisOlusturBaslik.getText();
            String bilgi = "" + editTextBagisOlusturBilgi.getText();
            String ozet = "" + editTextBagisOlusturOzet.getText();
            String smsAdres = "" + editTextBagisOlusturSmsAdres.getText();
            String smsMetin = "" + editTextBagisOlusturSmsMetin.getText();
            String resimKey = " ";
            if(resimCheck){
                resimKey = (bagisKey.toString()+".jpg");
            }
            System.out.println("resim key = " + resimKey);
            Bagis bagisOlustur = new Bagis(baslik, bilgi, ozet, " ", user.getUid(), resimKey,bagisKey,smsAdres,smsMetin);
            yazBagislar.child(bagisKey).setValue(bagisOlustur);
            kullanici.child(bagisKey).setValue("0");
            Toast.makeText(getContext(),"Bağış başarıyla eklendi",Toast.LENGTH_SHORT).show();
            final BagislarimFragment bagislarimFragment = new BagislarimFragment();
            setFragment(bagislarimFragment);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getContext(),"Bağış eklenemedi lütfen yeniden deneyin.",Toast.LENGTH_SHORT).show();
        }



    }
    public void dbBagisEkleKurumsal(boolean resimCheck){
        try {
            final DatabaseReference kullanici = db.getReference().child("Kullanicilar").child("Kurumsal").child(user.getUid()).child("Bagislarim");
            String baslik = "" + editTextBagisOlusturBaslik.getText();
            String bilgi = "" + editTextBagisOlusturBilgi.getText();
            String ozet = "" + editTextBagisOlusturOzet.getText();
            String kurum = Anasayfa.currentKurum.getKurumAdi();
            String smsAdres = "" + editTextBagisOlusturSmsAdres.getText();
            String smsMetin = "" + editTextBagisOlusturSmsMetin.getText();
            String resimKey = " ";
            if(resimCheck){
                resimKey = (bagisKey+".jpg");
            }
            Bagis bagisOlustur = new Bagis(baslik, bilgi, ozet, kurum, user.getUid(), resimKey, bagisKey, smsAdres, smsMetin);
            yazBagislar.child(bagisKey).setValue(bagisOlustur);
            kullanici.child(bagisKey).setValue("0");
            Toast.makeText(getContext(),"Bağış başarıyla eklendi",Toast.LENGTH_SHORT).show();
            final BagislarimFragment bagislarimFragment = new BagislarimFragment();
            setFragment(bagislarimFragment);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getContext(),"Bağış eklenemedi lütfen yeniden deneyin.",Toast.LENGTH_SHORT).show();
        }
    }
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction= getFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
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



    public void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),filePath);
                imageviewBagisOlusturFotograf.setImageBitmap(bitmap);
            }catch(IOException e){
                e.printStackTrace();
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

    private void uploadFile(){

        if((BitmapDrawable) imageviewBagisOlusturFotograf.getDrawable() != null){


            imageviewBagisOlusturFotograf.setDrawingCacheEnabled(true);
            imageviewBagisOlusturFotograf.buildDrawingCache();
            Bitmap bitmap2 =((BitmapDrawable) imageviewBagisOlusturFotograf.getDrawable()).getBitmap();
            Bitmap bitmap= null;
            bitmap =  getResizedBitmap(bitmap2,500,500);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);



            byte[] data = baos.toByteArray();

            StorageReference riversRef = storageReferance.child("images/"+bagisKey+".jpg");
            UploadTask uploadTask = riversRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(),"File Uploaded", Toast.LENGTH_LONG).show();
                    if(Anasayfa.kullaniciTipi.equals("Kurumsal")){
                        if(!isNetworkAvailable(getContext())) {
                            Toast.makeText(getContext(),"Internet Baglantinizi Kontrol Edin",Toast.LENGTH_LONG).show();
                        }else{
                            dbBagisEkleKurumsal(true);}
                    }else{
                        if(!isNetworkAvailable(getContext())) {
                            Toast.makeText(getContext(),"Internet Baglantinizi Kontrol Edin",Toast.LENGTH_LONG).show();
                        }else{
                            dbBagisEkleBireysel(true);}
                    }
                }
            });


        }

        else{
            if(Anasayfa.kullaniciTipi.equals("Kurumsal")){
                dbBagisEkleKurumsal(false);
            }else{
                dbBagisEkleBireysel(false);
            }
            //filepath bos hata kısmı
        }
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
