package com.azra.putujmozajedno;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SveVoznjeActivity extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> oglasiLista;

    // url za sve voznje
    // private static String url_sve_voznje = "http://10.0.2.2/putujmo_zajedno/daj_voznje.php";
    private static String url_sve_voznje = "http://192.168.5.100/putujmo_zajedno/daj_voznje.php";

    //url za voznje po pretrazi
    //private static String url_trazi_voznje = "http://10.0.2.2/putujmo_zajedno/trazi_voznje.php";
    private static String url_trazi_voznje = "http://192.168.5.100/putujmo_zajedno/trazi_voznje.php";

    // JSON Node names
    private static final String TAG_USPJEH = "uspjeh";
    private static final String TAG_ID = "id";
    private static final String TAG_POLAZAK = "polazak";
    private static final String TAG_ODREDISTE = "odrediste";
    private static final String TAG_DATUM = "datum";
    private static final String TAG_TIP = "tip_oglasa";
    private static final String TAG_MJESTA = "br_mjesta";
    private static final String TAG_OGLASI = "oglasi";
    private static final String TAG_OGLAS_ID = "oglas_id";

    // products JSONArray
    JSONArray oglasi = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sve_voznje);

        // Hashmap for ListView
        oglasiLista = new ArrayList<HashMap<String, String>>();

        // Loading products in Background Thread
        new LoadAllProducts().execute();

        // Get listview
        ListView lv = getListView();


        // pritisnut red liste lv
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // id izabranog oglasa
                String oglas_id = ((TextView) view.findViewById(R.id.idItem)).getText()
                        .toString();
                // novi Intent kreiran
                Intent intent = new Intent(getApplicationContext(),
                        VoznjaInfoActivity.class);
                // u Intent dodan ID oglasa
                intent.putExtra(TAG_OGLAS_ID, oglas_id);
                startActivity(intent);
            }
        });

    }

    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadAllProducts extends AsyncTask<String, String, String> {

        int success;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SveVoznjeActivity.this);
            pDialog.setMessage("Učitavanje vožnji...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            JSONObject json;

            if(getIntent().getStringExtra("activity").equals("main")){
                json = jParser.makeHttpRequest(url_sve_voznje, "GET", params);
            }
            else if(getIntent().getStringExtra("activity").equals("pretraga")){

                params.add(new BasicNameValuePair("polazak", getIntent().getStringExtra("polazak")));
                params.add(new BasicNameValuePair("odrediste", getIntent().getStringExtra("odrediste")));
                json = jParser.makeHttpRequest(url_trazi_voznje, "GET", params);
            }
            else {
                json = new JSONObject();
            }
            // Check your log cat for JSON response
            Log.d("All Products: ", json.toString());

            try {

                // Checking for SUCCESS TAG
                success = json.getInt(TAG_USPJEH);

                if (success == 1) {
                    // uzimanje niza "oglasi" iz JSON odgovora
                    oglasi = json.getJSONArray(TAG_OGLASI);

                    // petlja kroz sve članove niza
                    for (int i = 0; i < oglasi.length(); i++) {
                        JSONObject c = oglasi.getJSONObject(i);

                        // spašavanje podataka o vožnji u stringove
                        String id = c.getString(TAG_ID);
                        String polazak = c.getString(TAG_POLAZAK);
                        String odrediste = c.getString(TAG_ODREDISTE);
                        String br_mjesta = c.getString(TAG_MJESTA);
                        String tip_oglasa = c.getString(TAG_TIP);
                            if(tip_oglasa.equals("ponuda"))
                                tip_oglasa = "Ponuda";
                            else if(tip_oglasa.equals("potraznja"))
                                tip_oglasa = "Potražnja";

                        String datum = c.getString(TAG_DATUM);

                        //mijenjanje formata datuma
                        DateFormat df1 = new SimpleDateFormat("yyyy-mm-dd");
                        Date date = df1.parse(datum);

                        DateFormat df2 = new SimpleDateFormat("dd/mm/yyyy");
                        datum = df2.format(date);

                        // kreiranje novog HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // dodavanje podataka o vožnji u paru ključ-vrijednost
                        map.put(TAG_ID, id);
                        map.put(TAG_POLAZAK, polazak);
                        map.put(TAG_ODREDISTE, odrediste);
                        map.put(TAG_DATUM, datum);
                        map.put(TAG_MJESTA, br_mjesta);
                        map.put(TAG_TIP, tip_oglasa);

                        // dodavanje HashList u listu oglasiLista
                        oglasiLista.add(map);


                    }
                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();

            } catch (ParseException e) {
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
                        ListAdapter adapter = new SimpleAdapter(
                                SveVoznjeActivity.this, oglasiLista,
                                R.layout.list_item, new String[] { TAG_ID, TAG_DATUM,
                                TAG_POLAZAK, TAG_ODREDISTE, TAG_TIP, TAG_MJESTA},
                                new int[] { R.id.idItem, R.id.trosakTxt, R.id.polazakItem, R.id.odredisteItem, R.id.tipItem, R.id.mjestaItem  });
                        setListAdapter(adapter);
                    }

                    else{
                        Toast.makeText(getApplicationContext(), "Trenutno nema vožnji" , Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainScreenActivity.class);
                        startActivity(intent);
                        finish();
                    }


                }
            });

        }

    }
}
