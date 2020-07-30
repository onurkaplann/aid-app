package com.example.mobileapp;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Dimension;
import android.view.Display;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class ObjeLayout extends LinearLayout {
    Bagis bagis;

    public ObjeLayout(Context context, Bagis gelenBagis) {
        super(context);
        this.bagis=gelenBagis;
        String baslik =bagis.getBaslik() ;
        String ozet = bagis.getOzet();
        this.setOrientation(VERTICAL);
        this.addView(new ObjeGorsel(getContext(),bagis.getResimKey()));
        this.addView(new ObjeBilgi(getContext(),baslik));
        this.addView(new ObjeBilgi(getContext(),ozet,1));
    }
    public ObjeLayout(Context context, Kurum gelenKurum){
        super(context);
        this.setOrientation(VERTICAL);
        this.addView(new ObjeGorsel(getContext(), gelenKurum.resimKey,1));
        this.addView(new ObjeBilgi(getContext(),gelenKurum.kurumAdi));

    }


}
