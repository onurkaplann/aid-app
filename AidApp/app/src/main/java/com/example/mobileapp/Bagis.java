package com.example.mobileapp;

public class Bagis {
    String baslik;
    String bilgi;
    String ozet;
    String kurum;
    String kullaniciKey;
    String resimKey;
    String bagisid;
    String smsAdres;
    String smsMetin;

    public Bagis(String baslik, String bilgi, String ozet, String kurum, String kullaniciKey, String resimKey, String bagisid, String smsAdres, String smsMetin) {
        this.baslik = baslik;
        this.bilgi = bilgi;
        this.ozet = ozet;
        this.kurum = kurum;
        this.kullaniciKey = kullaniciKey;
        this.resimKey = resimKey;
        this.bagisid = bagisid;
        this.smsAdres = smsAdres;
        this.smsMetin = smsMetin;
    }
    public Bagis(String baslik, String bilgi, String ozet, String kurum, String resimKey, String bagisid, String smsAdres, String smsMetin) {
        this.baslik = baslik;
        this.bilgi = bilgi;
        this.ozet = ozet;
        this.kurum = kurum;
        this.resimKey = resimKey;
        this.bagisid = bagisid;
        this.smsAdres = smsAdres;
        this.smsMetin = smsMetin;
    }


    public String getKurum() {
        return kurum;
    }

    public void setKurum(String kurum) {
        this.kurum = kurum;
    }

    public String getSmsAdres() {
        return smsAdres;
    }

    public void setSmsAdres(String smsAdres) {
        this.smsAdres = smsAdres;
    }

    public String getSmsMetin() {
        return smsMetin;
    }

    public void setSmsMetin(String smsMetin) {
        this.smsMetin = smsMetin;
    }

    public String getBagisid() {
        return bagisid;
    }

    public void setBagisid(String bagisid) {
        this.bagisid = bagisid;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getBilgi() {
        return bilgi;
    }

    public void setBilgi(String bilgi) {
        this.bilgi = bilgi;
    }

    public String getOzet() {
        return ozet;
    }

    public void setOzet(String ozet) {
        this.ozet = ozet;
    }
    public String getKullaniciKey() {
        return kullaniciKey;
    }

    public void setKullaniciKey(String kullaniciKey) {
        this.kullaniciKey = kullaniciKey;
    }

    public String getResimKey() {
        return resimKey;
    }

    public void setResimKey(String resimKey) {
        this.resimKey = resimKey;
    }
}
