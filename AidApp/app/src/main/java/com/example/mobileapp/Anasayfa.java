package com.example.mobileapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class Anasayfa extends AppCompatActivity
        implements AnasayfaFragment.OnFragmentInteractionListener,
        ProfilFragment.OnFragmentInteractionListener,BagislarFragment.OnFragmentInteractionListener,
        IstatistikFragment.OnFragmentInteractionListener,AyarlarFragment.OnFragmentInteractionListener,
        ProfiliDuzenleFragment.OnFragmentInteractionListener,HatirlaticiFragment.OnFragmentInteractionListener,
        DestekOlFragment.OnFragmentInteractionListener,BildirimlerFragment.OnFragmentInteractionListener,HakkindaFragment.OnFragmentInteractionListener,
        KurumlarFragment.OnFragmentInteractionListener,KurumDetayFragment.OnFragmentInteractionListener,BagisOlusturFragment.OnFragmentInteractionListener,
        BagislarimFragment.OnFragmentInteractionListener,BagisIcerikFragment.OnFragmentInteractionListener, BagisimIcerikFragment.OnFragmentInteractionListener,
        BagiscilariGosterFragment.OnFragmentInteractionListener,KurumBagislarFragment.OnFragmentInteractionListener,ProfiliDuzenleKurumsalFragment.OnFragmentInteractionListener,
        BagisGuncelleFragment.OnFragmentInteractionListener

{

    private TextView mTextMessage;
    public static Birey currentBirey = new Birey();
    public static Kurum currentKurum = new Kurum();
    public static String kullaniciTipi;

    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);
        mTextMessage = (TextView) findViewById(R.id.message);

        final AnasayfaFragment anasayfaFragment=new AnasayfaFragment();
        final BagislarFragment bagislarFragment=new BagislarFragment();
        final ProfilFragment profilFragment=new ProfilFragment();
        final KurumlarFragment kurumlarFragment=new KurumlarFragment();
        setFragment(anasayfaFragment);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                if(id==R.id.navigation_anasayfa){
                    setFragment(anasayfaFragment);
                    return true;
                }else if(id==R.id.navigation_bagislar){
                    setFragment(bagislarFragment);
                    return true;
                }else if(id==R.id.navigation_kurumlar){
                    setFragment(kurumlarFragment);
                    return true;
                }else if(id==R.id.navigation_profil){
                    setFragment(profilFragment);
                    return true;
                }
                return false;
            }
        });

    }
    @Override
    public void onBackPressed() {
        if(navigation.getSelectedItemId()==R.id.navigation_anasayfa){
            minimizeApp();
        }else{
            String strFragment=getSupportFragmentManager().getFragments().toString();
            String currentFragment;
            currentFragment=strFragment.substring(1,strFragment.indexOf('{'));
            if(currentFragment.equals("BagislarFragment") || currentFragment.equals("KurumlarFragment") || currentFragment.equals("ProfilFragment")){
                final AnasayfaFragment anasayfaFragment=new AnasayfaFragment();
                setFragment(anasayfaFragment);
                navigation.setSelectedItemId(R.id.navigation_anasayfa);
            }else
                super.onBackPressed();
        }
    }
    public void minimizeApp() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }



    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
