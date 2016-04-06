package com.azra.putujmozajedno;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.internal.zzhl.runOnUiThread;

/**
 * Created by Azra on 26.08.2015..
 */
public class Korisnik implements Serializable{

    private String id;
    private String ime;
    private String prezime;
    private String email;
    private String telefon;
    private String fb;

   Korisnik(String id, String ime, String prezime, String email, String telefon, String fb){
       this.id = id;
       this.ime = ime;
       this.prezime = prezime;
       this.email = email;
       this.telefon = telefon;
       this.fb = fb;
   }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getFb() {
        return fb;
    }

    public void setFb(String fb) {
        this.fb = fb;
    }
}
