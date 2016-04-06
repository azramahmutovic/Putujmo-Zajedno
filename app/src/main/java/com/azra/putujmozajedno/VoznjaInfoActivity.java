package com.azra.putujmozajedno;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class VoznjaInfoActivity extends Activity {

    private ProgressDialog pDialog;

    JSONParser jParser = new JSONParser();

    //private static String url_voznja_id = "http://10.0.2.2/putujmo_zajedno/daj_voznju_id.php";
    private static String url_voznja_id = "http://192.168.5.100/putujmo_zajedno/daj_voznju_id.php";
    //private static String url_korisnik_id = "http://10.0.2.2/putujmo_zajedno/daj_korisnika_id.php";
    private static String url_korisnik_id = "http://192.168.5.100/putujmo_zajedno/daj_korisnika_id.php";

    // JSON Node names
    private static final String TAG_USPJEH = "uspjeh";
    private static final String TAG_ID = "id";
    private static final String TAG_POLAZAK = "polazak";
    private static final String TAG_ODREDISTE = "odrediste";
    private static final String TAG_DATUM = "datum";
    private static final String TAG_VRIJEME = "vrijeme";
    private static final String TAG_DANA = "flex_dana";
    private static final String TAG_TROSAK = "trosak";
    private static final String TAG_INFO = "info";
    private static final String TAG_CIJENA = "cijena";
    private static final String TAG_TIP = "tip_oglasa";
    private static final String TAG_MJESTA = "br_mjesta";
    private static final String TAG_OGLASI = "oglasi";
    private static final String TAG_OGLAS_ID = "oglas_id";
    private static final String TAG_KORISNIK = "korisnik_id";

   //JSONArray
    JSONArray oglasi = null;
    JSONArray korisnici = null;

    TextView flexDana;
    TextView mjesto;
    TextView trosak;
    LinearLayout korisnikRed;
    Oglas oglas;
    Korisnik korisnik;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voznja);


        flexDana = (TextView) findViewById(R.id.flexDanaInfo);
        mjesto = (TextView) findViewById(R.id.mjestoInfo);
        trosak = (TextView) findViewById(R.id.trosakInfo);
        korisnikRed = (LinearLayout) findViewById(R.id.korisnikInfo);

        // Loading products in Background Thread
        new DajVoznju().execute();

        korisnikRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KorisnikInfoActivity.class);
                //proslijedi korisnika
                intent.putExtra("korisnik", korisnik);
                startActivity(intent);
            }
        });
    }

    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class DajVoznju extends AsyncTask<String, String, String> {

        int success;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(VoznjaInfoActivity.this);
            pDialog.setMessage("Učitavanje detalja vožnje...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            String oglas_id = getIntent().getStringExtra(TAG_OGLAS_ID);
            params.add(new BasicNameValuePair(TAG_ID, oglas_id));

            // uzimanje JSON-a iz URL-a

            JSONObject json = jParser.makeHttpRequest(url_voznja_id, "POST", params);

            // Check your log cat for JSON response
            Log.d("Voznja: ", json.toString());

            try {

                // Checking for SUCCESS TAG
                success = json.getInt(TAG_USPJEH);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    oglasi = json.getJSONArray(TAG_OGLASI);

                    JSONObject o = oglasi.getJSONObject(0);

                        oglas = new Oglas();
                        oglas.setId(o.getString(TAG_ID));
                        oglas.setPolazak(o.getString(TAG_POLAZAK));
                        oglas.setOdrediste(o.getString(TAG_ODREDISTE));
                        oglas.setBrMjesta(o.getString(TAG_MJESTA));
                        oglas.setDatum(o.getString(TAG_DATUM));
                        oglas.setVrijeme(o.getString(TAG_VRIJEME));
                        oglas.setFlexDana(o.getString(TAG_DANA));
                        oglas.setInfo(o.getString(TAG_INFO));
                        oglas.setTrosak(o.getString(TAG_TROSAK));
                        oglas.setCijena(o.getString(TAG_CIJENA));
                        oglas.setKorisnikId(o.getString(TAG_KORISNIK));

                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();

            }

            //JSON za korisnika koji je objavio oglas
            List<NameValuePair> params2 = new ArrayList<NameValuePair>();
            params2.add(new BasicNameValuePair("id",oglas.getKorisnikId()));

            json = jParser.makeHttpRequest(url_korisnik_id, "POST", params2);

            Log.d("Korisnik: ", json.toString());

            try {

                int success = json.getInt("uspjeh");

                if (success == 1) {
                    // korisnik pronadjen

                    //spašavanje podataka o korisniku iz JSON-a
                    korisnici = json.getJSONArray("korisnici");
                    JSONObject k = korisnici.getJSONObject(0);

                    korisnik = new Korisnik(k.getString(TAG_ID), k.getString("ime"), k.getString("prezime"),
                            k.getString("email"), k.getString("telefon"), k.getString("fb"));


                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();

            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {

                    if(success == 1){
                        //ispis detalja na ekran
                        ((TextView) findViewById(R.id.polazakInfo)).setText(oglas.getPolazak());
                        ((TextView) findViewById(R.id.odredisteInfo)).setText(oglas.getOdrediste());
                        ((TextView) findViewById(R.id.vrijemeInfo)).setText(oglas.getDatum() + "  " + oglas.getVrijeme());

                        //
                        if(oglas.getFlexDana().equals(""))
                            flexDana.setVisibility(View.GONE);
                        else
                            flexDana.setText("Polazak fleksibilan za "+ oglas.getFlexDana());

                        //br mjesta
                        if(Integer.parseInt(oglas.getBrMjesta()) % 10 == 1)
                            mjesto.setText(oglas.getBrMjesta() + " mjesto");
                        else
                            mjesto.setText(oglas.getBrMjesta() + " mjesta");

                        //trosak
                        if(oglas.getTrosak().equals(""))
                            trosak.setVisibility(View.GONE);
                        else if(oglas.getTrosak().equals("dogovor"))
                            trosak.setText("Trošak po dogovoru");
                        else
                            trosak.setText(oglas.getCijena() + " KM po putniku");

                        //dodatne informacija
                        if(oglas.getInfo().equals("") || oglas.getInfo().equals("null"))
                            ((TextView) findViewById(R.id.dodatnoInfo)).setVisibility(View.GONE);
                        else
                            ((TextView) findViewById(R.id.dodatnoInfo)).setText(oglas.getInfo());

                        //ime i prezime korisnika
                        ((TextView) findViewById(R.id.korisnikIme)).setText(korisnik.getIme() + " " + korisnik.getPrezime());

                    }

                    else{
                        Toast.makeText(getApplicationContext(), "Detalji vožnje trenutno nisu dostupni" , Toast.LENGTH_SHORT).show();
                        finish();
                    }


                }
            });

        }

    }
}
