package com.azra.putujmozajedno;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Login extends Activity {

    JSONParser jsonParser = new JSONParser();
    private static String url_login_provjera = "http://10.0.2.2/putujmo_zajedno/login_provjera.php";
    //private static String url_login_provjera = "http://192.168.5.100/putujmo_zajedno/login_provjera.php";
    private static  String TAG_USPJEH = "uspjeh";
    private static String TAG_PORUKA = "tekst";

    private EditText email;
    private EditText password;
    private Button loginBtn;
    private TextView registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //provjera da li je korisnik logovan od ranije
        Sesija sesija = new Sesija(getApplicationContext());
        if(sesija.isLoggedIn()){
            Intent i = new Intent(getApplicationContext(), MainScreenActivity.class);
            startActivity(i);
            finish();
        }
        else{
            setContentView(R.layout.activity_login);

            email = (EditText) findViewById(R.id.email);
            password = (EditText) findViewById(R.id.password);
            loginBtn = (Button) findViewById(R.id.login);
            registerBtn = (TextView) findViewById(R.id.register);

            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new LoginCheck().execute();
                }
            });

            registerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),NoviKorisnikActivity.class);
                    startActivity(intent);
                }
            });
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class LoginCheck extends AsyncTask<String, String, String>{

        String message;

        @Override
        protected String doInBackground(String... args) {
            String emailString = email.getText().toString();
            String passwordString = password.getText().toString();

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", emailString));
            params.add(new BasicNameValuePair("password", passwordString));

            JSONObject json = jsonParser.makeHttpRequest(url_login_provjera,"POST",params);

            try {
                int success = json.getInt(TAG_USPJEH);
                message = json.getString(TAG_PORUKA);

                if (success == 1) {

                    Sesija userSession = new Sesija(getApplicationContext());
                    userSession.login(json.getString("id"), emailString);

                    Intent i = new Intent(getApplicationContext(), MainScreenActivity.class);
                    startActivity(i);
                    // closing this screen
                    finish();
                } else {

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast toast = Toast.makeText(getApplicationContext(), message , Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}

