package com.example.mobileapp;

public class Birey {
    String ad;
    String soyad;
    String adres;
    String telefon;
    String kullaniciAdi;
    String sosyalMedya;
    String email;
    String resimKey;
    String uid;
    public Birey(){

    }
    public Birey(String gelenAd, String gelenSoyad, String gelenAdres, String gelenTelefon, String gelenKullaniciAdi, String gelenSosyalMedya, String gelenEmail){
        this.ad = gelenAd;
        this.soyad = gelenSoyad;
        this.adres = gelenAdres;
        this.telefon = gelenTelefon;
        this.kullaniciAdi = gelenKullaniciAdi;
        this.sosyalMedya = gelenSosyalMedya;
        this.email = gelenEmail;
    }
    public Birey(String gelenAd, String gelenSoyad, String gelenAdres, String gelenTelefon, String gelenKullaniciAdi, String gelenSosyalMedya, String gelenEmail, String gelenResimKey, String gelenUid){
        this.ad = gelenAd;
        this.soyad = gelenSoyad;
        this.adres = gelenAdres;
        this.telefon = gelenTelefon;
        this.kullaniciAdi = gelenKullaniciAdi;
        this.sosyalMedya = gelenSosyalMedya;
        this.email = gelenEmail;
        this.resimKey = gelenResimKey;
        this.uid = gelenUid;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getSosyalMedya() {
        return sosyalMedya;
    }

    public void setSosyalMedya(String sosyalMedya) {
        this.sosyalMedya = sosyalMedya;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResimKey() {
        return resimKey;
    }

    public void setResimKey(String resimKey) {
        this.resimKey = resimKey;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
