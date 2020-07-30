package com.example.mobileapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfilFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseAuth mAuth;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilFragment newInstance(String param1, String param2) {
        ProfilFragment fragment = new ProfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    Button buttonProfilIstatistikler,buttonProfilHatirlatici,buttonProfilAyarlar,buttonProfilDuzenle,buttonProfilBagisOlustur,buttonProfilBagislarim,buttonProfilCikisYap;
    TextView textViewProfilAdSoyad;
    ImageView imageViewProfilResim;
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
        View RootView = inflater.inflate(R.layout.fragment_profil, container, false);
        //Bagis verileri listeye çekilecek ardından this,List.get(i) gönderilecek

        mAuth = FirebaseAuth.getInstance();

        buttonProfilIstatistikler= (Button) RootView.findViewById(R.id.buttonProfilIstatistikler);
        buttonProfilIstatistikler.setOnClickListener(this);
        buttonProfilAyarlar= (Button) RootView.findViewById(R.id.buttonProfilAyarlar);
        buttonProfilAyarlar.setOnClickListener(this);
        buttonProfilDuzenle= (Button) RootView.findViewById(R.id.buttonProfilDuzenle);
        buttonProfilDuzenle.setOnClickListener(this);
        buttonProfilHatirlatici= (Button) RootView.findViewById(R.id.buttonProfilHatirlatici);
        buttonProfilHatirlatici.setOnClickListener(this);
        buttonProfilBagisOlustur= (Button) RootView.findViewById(R.id.buttonProfilBagisOlustur);
        buttonProfilBagisOlustur.setOnClickListener(this);
        buttonProfilBagislarim= (Button)RootView.findViewById(R.id.buttonProfilBagislarim);
        buttonProfilBagislarim.setOnClickListener(this);
        buttonProfilCikisYap = (Button) RootView.findViewById(R.id.buttonProfilCikisYap);
        buttonProfilCikisYap.setOnClickListener(this);
        textViewProfilAdSoyad = (TextView) RootView.findViewById(R.id.textviewProfilAdSoyad);
        ImageView imageViewProfilResim = (ImageView) RootView.findViewById(R.id.imageViewProfilResim);

        if(Anasayfa.kullaniciTipi.equals("Kurumsal") ){
            textViewProfilAdSoyad.setText(Anasayfa.currentKurum.getKurumAdi());
            ppadd(Anasayfa.currentKurum.resimKey,imageViewProfilResim);
        }else {
            textViewProfilAdSoyad.setText(Anasayfa.currentBirey.getAd() + "  " + Anasayfa.currentBirey.getSoyad());
            ppadd(Anasayfa.currentBirey.resimKey, imageViewProfilResim);
        }
        // Inflate the layout for this fragment
        return RootView;
    }
    public void ppadd(String resimKey, final ImageView imageView){
        if(resimKey == " "){
            imageView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_launcher_foreground));;
        }
        else {
            StorageReference storageReferance  = FirebaseStorage.getInstance().getReference();
            final StorageReference islandRef = storageReferance.child("kullanicilar").child(resimKey);
            final long ONE_MEGABYTE = 1024 * 1024;
            islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageView.setImageBitmap(bmp);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });

        }
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
        if(view.getId()==buttonProfilIstatistikler.getId()){
//            final IstatistikFragment istatistikFragment=new IstatistikFragment();
//            setFragment(istatistikFragment);
            Toast.makeText(getContext(),"Bu Özellik Şimdilik Kullanım Dışı",Toast.LENGTH_SHORT).show();

        }else if(view.getId()==buttonProfilAyarlar.getId()){
            final AyarlarFragment ayarlarFragment=new AyarlarFragment();
            setFragment(ayarlarFragment);

        }else if(view.getId()==buttonProfilDuzenle.getId()){
            final ProfiliDuzenleFragment profiliDuzenleFragment=new ProfiliDuzenleFragment();
            final ProfiliDuzenleKurumsalFragment profiliDuzenleKurumsalFragment = new ProfiliDuzenleKurumsalFragment();
            if(Anasayfa.kullaniciTipi.equals("Bireysel")){
            setFragment(profiliDuzenleFragment);
            }else{
                setFragment(profiliDuzenleKurumsalFragment);
            }

        }else if(view.getId()==buttonProfilHatirlatici.getId()){
//            final HatirlaticiFragment hatirlaticiFragment=new HatirlaticiFragment();
//            setFragment(hatirlaticiFragment);
            Toast.makeText(getContext(),"Bu Özellik Şimdilik Kullanım Dışı",Toast.LENGTH_SHORT).show();

        }else if(view.getId() == buttonProfilBagisOlustur.getId()){
            final BagisOlusturFragment bagisOlusturFragment = new BagisOlusturFragment();
            setFragment(bagisOlusturFragment);
        }else if(view.getId() == buttonProfilBagislarim.getId()){
            final BagislarimFragment bagislarimFragment = new BagislarimFragment();
            setFragment(bagislarimFragment);
        }else if(view.getId() == buttonProfilCikisYap.getId()){
            mAuth.signOut();
            Intent UygulamaGiris = new Intent(getContext(),UygulamaGiris.class);
            startActivity(UygulamaGiris);
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
