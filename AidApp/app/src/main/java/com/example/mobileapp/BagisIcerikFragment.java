package com.example.mobileapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BagisIcerikFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BagisIcerikFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BagisIcerikFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText editTextBagisIcerikKGoster;


    Bagis bagis;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private OnFragmentInteractionListener mListener;

    public BagisIcerikFragment() {
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
    public static BagisIcerikFragment newInstance(String param1, String param2) {
        BagisIcerikFragment fragment = new BagisIcerikFragment();
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
    TextView textViewBagisIcerikBaslik,textViewBagisIcerikBilgi,textViewBagisIcerikKurum;
    Button buttonBagislarIcerikBagisYap;
    ImageView imageViewBagisIcerikResim;

    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.fragment_bagis_icerik, container, false);
        textViewBagisIcerikBaslik=(TextView)RootView.findViewById(R.id.textViewBagisIcerikBaslik);
        textViewBagisIcerikBilgi=(TextView)RootView.findViewById(R.id.textViewBagisIcerikBilgi);
        textViewBagisIcerikKurum=(TextView)RootView.findViewById(R.id.textViewBagisIcerikKurum);
        textViewBagisIcerikBaslik.setText(bagis.baslik);
        textViewBagisIcerikBilgi.setText(bagis.bilgi);
        textViewBagisIcerikKurum.setText(bagis.kurum);
        buttonBagislarIcerikBagisYap = (Button) RootView.findViewById(R.id.buttonBagisIcerikBagisYap);
        buttonBagislarIcerikBagisYap.setOnClickListener(this);
        imageViewBagisIcerikResim = (ImageView) RootView.findViewById(R.id.imageViewBagisIcerikResim);

        editTextBagisIcerikKGoster = (EditText) RootView.findViewById(R.id.editTextBagisIcerikMiktar);

        fab = (FloatingActionButton) RootView.findViewById(R.id.floatingActionButtonSms);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sms();
            }
        });

        if(bagis.resimKey != null){
            Bitmap bmp2;
            StorageReference storageReferance  = FirebaseStorage.getInstance().getReference();
            final StorageReference islandRef = storageReferance.child("images").child(bagis.getResimKey());
            final long ONE_MEGABYTE = 1024 * 1024;
            islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    BagisIcerikFragment.this.imageViewBagisIcerikResim.setImageBitmap(bmp);
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

    public void sms(){
        if(bagis.getSmsAdres()!= null){
            Uri uri = Uri.parse("smsto:"+ bagis.getSmsAdres());
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra("sms_body", bagis.getSmsMetin());
            startActivity(intent);
        }
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
        if(v.getId() == buttonBagislarIcerikBagisYap.getId()){
            if(!isNetworkAvailable(getContext())) {
                Toast.makeText(getContext(),"Internet Baglantinizi Kontrol Edin",Toast.LENGTH_LONG).show();
            }else {
                kontrol();
            }

        }
    }

    public void kontrol(){
        final DatabaseReference bagisci =db.getReference().child("Bagislar").child(bagis.bagisid).child("bagiscilar").child(user.getUid());
        bagisci.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    if(dataSnapshot.getValue()!=null){
                        int onceki=Integer.parseInt(dataSnapshot.getValue().toString());
                        dbBagisEkle(onceki);
                    }else{
                        dbBagisEkle(0);
                    }
                    Toast.makeText(getContext(),"Bağışınız Alınmıştır, Teşekkür Ederiz.",Toast.LENGTH_SHORT).show();
                    BagisIcerikFragment.this.editTextBagisIcerikKGoster.setText("");

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void dbBagisEkle(int miktar){
        DatabaseReference yazBagislar =db.getReference().child("Bagislar").child(bagis.getBagisid());
        DatabaseReference yazBagislarim = db.getReference().child("Kullanicilar").child(Anasayfa.kullaniciTipi).child(user.getUid()).child("YaptigimBagislar");
        String uID = user.getUid();
        if(miktar==0){
            if(!editTextBagisIcerikKGoster.getText().toString().equals("") && !editTextBagisIcerikKGoster.getText().toString().equals("0"))
                yazBagislar.child("bagiscilar").child(uID).setValue(editTextBagisIcerikKGoster.getText().toString());
                yazBagislarim.child(bagis.getBagisid()).setValue(editTextBagisIcerikKGoster.getText().toString());
        }else{
            int i=Integer.parseInt(editTextBagisIcerikKGoster.getText().toString())+miktar;
            yazBagislar.child("bagiscilar").child(uID).setValue(i);
            System.out.println("userid::"+user.getUid());
            yazBagislarim.child(bagis.getBagisid()).setValue(i);
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
