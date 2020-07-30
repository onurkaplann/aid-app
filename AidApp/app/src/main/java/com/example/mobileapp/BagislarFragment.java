package com.example.mobileapp;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BagislarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BagislarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BagislarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Database connector
    FirebaseDatabase db = FirebaseDatabase.getInstance();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BagislarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BagislarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BagislarFragment newInstance(String param1, String param2) {
        BagislarFragment fragment = new BagislarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    LinearLayout linearLayoutBagislar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    private void verileriCek(){
        DatabaseReference okuBagislar =db.getReference().child("Bagislar");
        okuBagislar.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                try {
                    String bagisID = dataSnapshot.getKey().toString();
                    String baslik = dataSnapshot.child("baslik").getValue().toString();
                    String bilgi = dataSnapshot.child("bilgi").getValue().toString();
                    String kurum = dataSnapshot.child("kurum").getValue().toString();
                    String ozet = dataSnapshot.child("ozet").getValue().toString();
                    String smsAdres = dataSnapshot.child("smsAdres").getValue().toString();
                    String smsMetin = dataSnapshot.child("smsMetin").getValue().toString();
                    String resimKey = dataSnapshot.child("resimKey").getValue().toString();

                    Bagis deneme = new Bagis(baslik,bilgi,ozet, kurum, resimKey,bagisID,smsAdres,smsMetin);
                    ObjeKart nesne = new ObjeKart(getContext(), deneme);
                    linearLayoutBagislar.addView(nesne);
                    linearLayoutBagislar.addView(new ObjeBilgi(getContext(),1));

                    nesne.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            System.out.println(((ObjeKart)view).bagis.baslik);
                            final BagisIcerikFragment bagisIcerikFragment = new BagisIcerikFragment();
                            bagisIcerikFragment.bagis=((ObjeKart)view).bagis;
                            setFragment(bagisIcerikFragment);
                        }
                    });

                }
                catch (Exception ex){
                    System.out.println("Bağışlar Bulunamadı.");
                }
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        verileriCek();
        View RootView = inflater.inflate(R.layout.fragment_bagislar, container, false);
        //Bagis verileri listeye çekilecek ardından this,List.get(i) gönderilecek
        linearLayoutBagislar= (LinearLayout) RootView.findViewById(R.id.linearlayoutBagislar);
        /*for (int i=0;i<5;i++){
           // linearLayoutBagislar.addView(new ObjeLayout(getContext()));
            linearLayoutBagislar.addView(new ObjeLayout(getContext(),"a","b","c","d"));
        }*/

        FloatingActionButton fab = (FloatingActionButton) RootView.findViewById(R.id.floatingActionButtonBagislarEkle);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast= Toast.makeText(getContext(),
                        "YENİ BAĞIŞ EKLE", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
        });
        if(true){
            fab.hide();
        }


        return RootView;
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
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction= getFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
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
