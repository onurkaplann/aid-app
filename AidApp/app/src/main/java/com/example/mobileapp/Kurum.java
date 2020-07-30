package com.example.mobileapp;

public class Kurum {
    String kurumAdi;
    String adres;
    String kurumNo;
    String telefon;
    String sosyalMedya;
    String email;
    String resimKey;
    String uid;

    public Kurum(){

    }

    public Kurum (String kurumAdi, String adres, String kurumNo, String telefon, String sosyalMedya, String email, String uid){
        this.kurumAdi=kurumAdi;
        this.adres=adres;
        this.kurumNo=kurumNo;
        this.telefon=telefon;
        this.sosyalMedya=sosyalMedya;
        this.email = email;
        this.uid = uid;
    }

    public Kurum (String kurumAdi, String adres, String kurumNo, String telefon, String sosyalMedya, String email, String resimKey, String uid){
        this.kurumAdi=kurumAdi;
        this.adres=adres;
        this.kurumNo=kurumNo;
        this.telefon=telefon;
        this.sosyalMedya=sosyalMedya;
        this.email = email;
        this.resimKey = resimKey;
        this.uid = uid;
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

    public String getKurumAdi() {
        return kurumAdi;
    }

    public void setKurumAdi(String kurumAdi) {
        this.kurumAdi = kurumAdi;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getKurumNo() {
        return kurumNo;
    }

    public void setKurumNo(String kurumNo) {
        this.kurumNo = kurumNo;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getSosyalMedya() {
        return sosyalMedya;
    }

    public void setSosyalMedya(String sosyalMedya) {
        this.sosyalMedya = sosyalMedya;
    }


}
