package com.azra.putujmozajedno;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Azra on 20.08.2015..
 */
public class NoviOglas3Activity extends ActionBarActivity {

    Button postaviBtn;

    EditText brDana;
    EditText brMjesta;
    EditText infoTxt;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    JSONParser jsonParser = new JSONParser();

    //private static String url_dodaj_oglas = "http://10.0.2.2/putujmo_zajedno/dodaj_oglas.php";
    private static String url_dodaj_oglas = "http://192.168.5.100/putujmo_zajedno/dodaj_oglas.php";

    // JSON
    private static final String TAG_USPJEH = "uspjeh";
    private static final String TAG_PORUKA = "tekst";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.postavi_oglas);
        postaviBtn = (Button) findViewById(R.id.postaviBtn);

        brDana = (EditText) findViewById(R.id.brDanaTxt);
        brMjesta = (EditText) findViewById(R.id.brMjestaTxt);
        infoTxt = (EditText) findViewById(R.id.infoTxt);

        pref = getApplicationContext().getSharedPreferences("Oglas", 0); // 0 - for private mode
        editor = pref.edit();

        postaviBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(brMjesta.getText().toString().trim().length() > 0){
                    new PostaviOglas().execute();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Unesite broj mjesta", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    class PostaviOglas extends AsyncTask<String, String, String> {

        String message;

        @Override
        protected String doInBackground(String... args) {

            String tip_oglasa = pref.getString("tip_oglasa", "");
            String polazak = pref.getString("polazak", "");
            String odrediste = pref.getString("odrediste", "");
            String datum = pref.getString("datum", "");
            String vrijeme = pref.getString("vrijeme", "");
            String trosak = pref.getString("trosak", "");
            String cijena = pref.getString("cijena", "");
            String flex_dana = brDana.getText().toString();
            String br_mjesta = brMjesta.getText().toString();
            String info = infoTxt.getText().toString();

            Sesija sesija = new Sesija(getApplicationContext());
            String id = sesija.dajUserID();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("tip_oglasa", tip_oglasa));
            params.add(new BasicNameValuePair("polazak", polazak));
            params.add(new BasicNameValuePair("odrediste", odrediste));
            params.add(new BasicNameValuePair("datum", datum));
            params.add(new BasicNameValuePair("vrijeme", vrijeme));
            params.add(new BasicNameValuePair("flex_dana", flex_dana));
            params.add(new BasicNameValuePair("br_mjesta", br_mjesta));
            params.add(new BasicNameValuePair("info", info));
            params.add(new BasicNameValuePair("trosak", trosak));
            params.add(new BasicNameValuePair("cijena", cijena));
            params.add(new BasicNameValuePair("korisnik_id", id));

            JSONObject json = jsonParser.makeHttpRequest(url_dodaj_oglas,
                    "POST", params);
            Log.d("Oglas: ", json.toString());

            editor.clear();
            editor.commit();

            try {
                int success = json.getInt(TAG_USPJEH);
                message = json.getString(TAG_PORUKA);

                if (success == 1) {

                    Intent intent = new Intent(getApplicationContext(),MainScreenActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                } else {
                    finish();
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
