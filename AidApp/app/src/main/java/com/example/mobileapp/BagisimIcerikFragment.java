package com.example.mobileapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BagisimIcerikFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BagisimIcerikFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BagisimIcerikFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Bagis bagis;
    int bagisTutari;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    private BagisimIcerikFragment.OnFragmentInteractionListener mListener;

    public BagisimIcerikFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BagisIcerikFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BagisimIcerikFragment newInstance(String param1, String param2) {
        BagisimIcerikFragment fragment = new BagisimIcerikFragment();
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
    TextView textViewBagisimIcerikBaslik,textViewBagisimIcerikBilgi,textViewBagisimIcerikKurum,textViewBagisimIcerikBagisTutari;
    ImageView imageViewBagisimIcerikResim;

    Button buttonBagisimIcerikBagiscilariGoster, buttonBagisimIcerikBagisSil, buttonBagisimIcerikBagisGuncelle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_bagisim_icerik, container, false);
        bagisTutariHesapla();
        textViewBagisimIcerikBaslik=(TextView)RootView.findViewById(R.id.textViewBagisimIcerikBaslik);
        textViewBagisimIcerikBilgi=(TextView)RootView.findViewById(R.id.textViewBagisimIcerikBilgi);
        textViewBagisimIcerikKurum=(TextView)RootView.findViewById(R.id.textViewBagisimIcerikKurum);
        textViewBagisimIcerikBaslik.setText(bagis.baslik);
        textViewBagisimIcerikBilgi.setText(bagis.bilgi);
        textViewBagisimIcerikKurum.setText(bagis.kurum);
        textViewBagisimIcerikBagisTutari = (TextView) RootView.findViewById(R.id.textViewBagisimIcerikBagisTutari);

        buttonBagisimIcerikBagiscilariGoster = (Button) RootView.findViewById(R.id.buttonBagisimIcerikBagiscilariGoster);
        buttonBagisimIcerikBagiscilariGoster.setOnClickListener(this);
        buttonBagisimIcerikBagisSil = (Button) RootView.findViewById(R.id.buttonBagisimIcerikBagisSil);
        buttonBagisimIcerikBagisSil.setOnClickListener(this);

        buttonBagisimIcerikBagisGuncelle = (Button) RootView.findViewById(R.id.buttonBagisimIcerikBagisGuncelle);
        buttonBagisimIcerikBagisGuncelle.setOnClickListener(this);
        imageViewBagisimIcerikResim = (ImageView) RootView.findViewById(R.id.imageViewBagisimIcerikResim);

        if(bagis.resimKey != null){
            StorageReference storageReferance  = FirebaseStorage.getInstance().getReference();
            final StorageReference islandRef = storageReferance.child("images").child(bagis.getResimKey());
            final long ONE_MEGABYTE = 1024 * 1024;
            islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    BagisimIcerikFragment.this.imageViewBagisimIcerikResim.setImageBitmap(bmp);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }
        return RootView;
    }
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }
    @Override
    public void onClick(View v) {
        if(!isNetworkAvailable(getContext())) {
            Toast.makeText(getContext(),"Internet Baglantinizi Kontrol Edin",Toast.LENGTH_LONG).show();
        }else{
            if(v.getId() == buttonBagisimIcerikBagiscilariGoster.getId()){
                final BagiscilariGosterFragment bagiscilariGosterFragment = new BagiscilariGosterFragment();
                bagiscilariGosterFragment.bagis = bagis;
                bagisTutari = 0;
                setFragment(bagiscilariGosterFragment);
            }
            else if(v.getId() == buttonBagisimIcerikBagisSil.getId()){
                DatabaseReference ref = db.getReference().child("Bagislar").child(this.bagis.getBagisid());
                ref.removeValue();
                final  BagislarimFragment bagislarimFragment = new BagislarimFragment();
                DatabaseReference ref2 = db.getReference().child("Kullanicilar").child(Anasayfa.kullaniciTipi).child(user.getUid()).child("Bagislarim").child(this.bagis.getBagisid());
                ref2.removeValue();
                Toast.makeText(getContext(),"Bağış Başarıyla Silindi",Toast.LENGTH_LONG).show();
                setFragment(bagislarimFragment);
            }
            else if(v.getId() == buttonBagisimIcerikBagisGuncelle.getId()){
                final BagisGuncelleFragment bagisGuncelleFragment = new BagisGuncelleFragment();
                bagisGuncelleFragment.bagis = bagis;
                setFragment(bagisGuncelleFragment);
            }
        }


    }
    private void bagisTutariHesapla(){
        DatabaseReference ref = db.getReference().child("Bagislar").child(this.bagis.getBagisid()).child("bagiscilar");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BagisimIcerikFragment.this.bagisTutari += Integer.parseInt(dataSnapshot.getValue().toString());
                BagisimIcerikFragment.this.textViewBagisimIcerikBagisTutari.setText("" + (BagisimIcerikFragment.this.bagisTutari));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
}
