package com.example.mobileapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link KurumlarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link KurumlarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KurumlarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseDatabase db = FirebaseDatabase.getInstance();

    private OnFragmentInteractionListener mListener;

    public KurumlarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KurumlarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KurumlarFragment newInstance(String param1, String param2) {
        KurumlarFragment fragment = new KurumlarFragment();
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


    private void verileriCek(){
        DatabaseReference okuKurumlar =db.getReference().child("Kullanicilar").child("Kurumsal");

        okuKurumlar.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                try {
                    String adres = dataSnapshot.child("adres").getValue().toString();
                    String email=dataSnapshot.child("email").getValue().toString();
                    String kurumNo = dataSnapshot.child("kurumNo").getValue().toString();
                    String kurumAdi = dataSnapshot.child("kurumAdi").getValue().toString();
                    String telefon = dataSnapshot.child("telefon").getValue().toString();
                    String sosyalMedya = dataSnapshot.child("sosyalMedya").getValue().toString();
                    String resimKey = dataSnapshot.child("resimKey").getValue().toString();
                    String uid = dataSnapshot.child("uid").getValue().toString();

                    Kurum kurum = new Kurum(kurumAdi,adres,kurumNo,telefon,sosyalMedya,email,resimKey,uid);
                    ObjeKart nesne = new ObjeKart(getContext(), kurum);


                    gridlayoutKurumlarFragment.addView(nesne);
                    nesne.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            System.out.println(((ObjeKart)view).kurum.kurumAdi);
                            final KurumDetayFragment kurumDetayFragment= new KurumDetayFragment();
                            kurumDetayFragment.kurum=((ObjeKart)view).kurum;
                            setFragment(kurumDetayFragment);
                        }
                    });
                }
                catch (Exception ex){
                    System.out.println("Kurumlar Bulunamadı.");
                };
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                gridlayoutKurumlarFragment.removeViewAt(1);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    GridLayout gridlayoutKurumlarFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_kurumlar,container,false);
        gridlayoutKurumlarFragment= (GridLayout) RootView.findViewById(R.id.gridlayoutKurumlarFragment);
        verileriCek();
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
