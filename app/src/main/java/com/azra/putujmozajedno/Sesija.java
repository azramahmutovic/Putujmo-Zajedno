package com.azra.putujmozajedno;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by Azra on 24.08.2015..
 */
public class Sesija {

    SharedPreferences sp;

    //editor pomoću kojeg se pišu podaci u SharedPreferences
    SharedPreferences.Editor editor;

    //kontekst - odakle se poziva klasa
    Context context;

    int PRIVATE = 0;

    private static final String SP_IME = "KorisnikSesija";
    private static final String LOGGED_IN = "LoggedIn";
    private static final String ID = "korisnik_id";
    private static final String USER = "korisnik_email";

    public Sesija(Context context){
        this.context = context;
        sp = context.getSharedPreferences(SP_IME, PRIVATE);
        editor = sp.edit();
    }

    public void login(String id, String email) {

        editor.putBoolean(LOGGED_IN, true);
        editor.putString(ID, id);
        editor.putString(USER, email);
        editor.commit();
    }

    //vrati korisnikov ID
    public String dajUserID() {
        return sp.getString(ID, null);
    }

    //vrati korisnikov mail
    public String dajUserEmail() {
        return sp.getString(USER, null);
    }

    //odjava korisnika
    public void logout(){
        editor.clear();
        editor.commit();

        Intent intent = new Intent(context, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    //provjera da li je korisnik prijavljen
    public boolean isLoggedIn(){
        return sp.getBoolean(LOGGED_IN, false);
    }
}
