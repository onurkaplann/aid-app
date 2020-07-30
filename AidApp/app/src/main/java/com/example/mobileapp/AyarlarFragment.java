package com.example.mobileapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AyarlarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AyarlarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AyarlarFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AyarlarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AyarlarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AyarlarFragment newInstance(String param1, String param2) {
        AyarlarFragment fragment = new AyarlarFragment();
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
    Button buttonAyarlarBildirimler,buttonAyarlarDestekOl,buttonAyarlarHakkinda;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_ayarlar, container, false);
        buttonAyarlarBildirimler=(Button)RootView.findViewById(R.id.buttonAyarlarBildirimler);
        buttonAyarlarBildirimler.setOnClickListener(this);
        buttonAyarlarDestekOl=(Button)RootView.findViewById(R.id.buttonAyarlarDestekOl);
        buttonAyarlarDestekOl.setOnClickListener(this);
        buttonAyarlarHakkinda=(Button)RootView.findViewById(R.id.buttonAyarlarHakkinda);
        buttonAyarlarHakkinda.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        if(view.getId()==buttonAyarlarHakkinda.getId()){
//            final HakkindaFragment hakkindaFragment=new HakkindaFragment();
//            setFragment(hakkindaFragment);
            Toast.makeText(getContext(),"Bu Özellik Şimdilik Kullanım Dışı",Toast.LENGTH_SHORT).show();

        }else if(view.getId()==buttonAyarlarDestekOl.getId()){
//            final DestekOlFragment destekOlFragment=new DestekOlFragment();
//            setFragment(destekOlFragment);
            Toast.makeText(getContext(),"Bu Özellik Şimdilik Kullanım Dışı",Toast.LENGTH_SHORT).show();

        }else if(view.getId()==buttonAyarlarBildirimler.getId()){
//            final BildirimlerFragment bildirimlerFragment=new BildirimlerFragment();
//            setFragment(bildirimlerFragment);
            Toast.makeText(getContext(),"Bu Özellik Şimdilik Kullanım Dışı",Toast.LENGTH_SHORT).show();

        }

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
