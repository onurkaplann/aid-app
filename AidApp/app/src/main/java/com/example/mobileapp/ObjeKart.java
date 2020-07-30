package com.example.mobileapp;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class ObjeKart extends android.support.v7.widget.CardView {
    Bagis bagis;
    Kurum kurum;
    public ObjeKart(@NonNull Context context,Bagis gelenBagis) {
        super(context);
        bagis=gelenBagis;
        this.setCardBackgroundColor(Color.parseColor("#ffffff"));
        this.setRadius((float)50F);
        this.addView(new ObjeLayout(getContext(), gelenBagis));


    }
    public ObjeKart(@NonNull Context context,Kurum gelenKurum) {
        super(context);
        kurum=gelenKurum;
        test();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((w-80)/2, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(20, 20, 20, 20);
        this.setLayoutParams(params);
        this.setCardBackgroundColor(Color.parseColor("#ffffff"));
        this.setRadius((float)50F);
        this.addView(new ObjeLayout(getContext(), gelenKurum));

    }

    int h,w;
    public void test(){
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        w = metrics.widthPixels;
        h = metrics.heightPixels;
    }
}
