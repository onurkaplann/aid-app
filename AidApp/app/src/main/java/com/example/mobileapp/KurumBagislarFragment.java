package com.example.mobileapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link KurumBagislarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link KurumBagislarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KurumBagislarFragment extends Fragment {
    Bagis bagis;
    Kurum kurum;
    FirebaseDatabase db = FirebaseDatabase.getInstance();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public KurumBagislarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KurumBagislarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KurumBagislarFragment newInstance(String param1, String param2) {
        KurumBagislarFragment fragment = new KurumBagislarFragment();
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

    LinearLayout linearlayoutKurumBagislar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View RootView = inflater.inflate(R.layout.fragment_kurum_bagislar,container,false);
        linearlayoutKurumBagislar=(LinearLayout)RootView.findViewById(R.id.linearlayoutKurumBagislar);
        verileriCek();
        return RootView;
    }
    public void verileriCek(){
        DatabaseReference okuBagislarim;
        try {
            okuBagislarim = db.getReference().child("Kullanicilar").child("Kurumsal").child(kurum.getUid()).child("Bagislarim");
        }catch (Exception e){
            e.printStackTrace();
            return;
        }


        okuBagislarim.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap myMap = (HashMap) dataSnapshot.getValue();
                if(myMap == null){
                    return;
                }
                String[] strings = (String[]) myMap.keySet().toArray(new String[myMap.size()]);



                for (int i = 0; i < strings.length ; i++) {
                    DatabaseReference okuBagislar = db.getReference().child("Bagislar").child(strings[i]);
                    okuBagislar.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
                                linearlayoutKurumBagislar.addView(nesne);
                                linearlayoutKurumBagislar.addView(new ObjeBilgi(getContext(),1));

                                nesne.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        System.out.println(((ObjeKart)view).bagis.baslik);
                                        final BagisIcerikFragment bagisIcerikFragment = new BagisIcerikFragment();
                                        bagisIcerikFragment.bagis=((ObjeKart)view).bagis;
                                        setFragment(bagisIcerikFragment);
                                    }
                                });

                            }catch (Exception e){
                                System.out.println("Bu olmadi: " + dataSnapshot.getValue());
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
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
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction= getFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
    }
}
