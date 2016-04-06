package com.azra.putujmozajedno;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NoviKorisnikActivity extends ActionBarActivity {

    JSONParser jsonParser = new JSONParser();
    EditText ime;
    EditText prezime;
    EditText email;
    EditText password;
    EditText telefon;
    EditText fb;


   // private static String url_kreiraj_korisnika = "http://10.0.2.2/putujmo_zajedno/kreiraj_korisnika.php";
    private static String url_kreiraj_korisnika = "http://192.168.5.1/putujmo_zajedno/kreiraj_korisnika.php";
    // JSON
    private static final String TAG_USPJEH = "uspjeh";
    private static final String TAG_PORUKA = "tekst";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kreiraj_korisnika);

        // Edit Text
        ime = (EditText) findViewById(R.id.imeKorisnika);
        prezime = (EditText) findViewById(R.id.prezimeKorisnika);
        email = (EditText) findViewById(R.id.emailKorisnika);
        password = (EditText) findViewById(R.id.passwordKorisnika);

        // Create button
        Button spasiKorisnika = (Button) findViewById(R.id.spasiDugme);

        // button click event
        spasiKorisnika.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(ime.getText().toString().trim().length() > 0 && prezime.getText().toString().trim().length() > 0
                        && email.getText().toString().trim().length() > 0 && password.getText().toString().trim().length() > 0){

                    new KreirajKorisnika().execute();
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Ispunite obavezna polja", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
    }

    class KreirajKorisnika extends AsyncTask<String, String, String> {

        String message;

        protected String doInBackground(String... args) {

            String imeK = ime.getText().toString();
            String prezimeK = prezime.getText().toString();
            String emailK = email.getText().toString();
            String passwordK = password.getText().toString();
            String telefonK;
            String fbK;
            if(telefon!=null) {
                telefonK = telefon.getText().toString();
            }
            else{
                telefonK = "";
            }
            if(fb!=null){
                fbK = fb.getText().toString();
            }
            else{
                fbK = "";
            }

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("ime", imeK));
            params.add(new BasicNameValuePair("prezime", prezimeK));
            params.add(new BasicNameValuePair("email", emailK));
            params.add(new BasicNameValuePair("password", passwordK));
            params.add(new BasicNameValuePair("telefon", telefonK));
            params.add(new BasicNameValuePair("fb", fbK));

            JSONObject json = jsonParser.makeHttpRequest(url_kreiraj_korisnika,
                    "POST", params);
            Log.d("Registracija: ", json.toString());

            try {
                int success = json.getInt(TAG_USPJEH);
                message = json.getString(TAG_PORUKA);

                if (success == 1) {

                    Sesija userSession = new Sesija(getApplicationContext());
                    userSession.login(json.getString("id"), emailK);

                    Intent intent = new Intent(getApplicationContext(),MainScreenActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            Toast toast = Toast.makeText(getApplicationContext(), message , Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}