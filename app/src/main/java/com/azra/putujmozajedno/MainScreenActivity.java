package com.azra.putujmozajedno;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainScreenActivity extends AppCompatActivity {

    Button btnSveVoznje;
    Button btnTraziVoznje;
    Button btnPostaviOglas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        // Buttons
        btnSveVoznje = (Button) findViewById(R.id.btnSveVoznje);
        btnTraziVoznje = (Button) findViewById(R.id.btnTraziVoznje);
        btnPostaviOglas = (Button) findViewById(R.id.btnPostaviOglas);

        // view products click event
        btnSveVoznje.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent intent = new Intent(getApplicationContext(), SveVoznjeActivity.class);
                intent.putExtra("activity", "main");
                startActivity(intent);

            }
        });

        btnTraziVoznje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TraziVoznjeActivity.class);
                startActivity(intent);
            }
        });

        // view products click event
        btnPostaviOglas.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching create new product activity
                Intent intent = new Intent(getApplicationContext(), NoviOglasActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.odjavaItemMenu : {
                Sesija sesija = new Sesija(getApplicationContext());
                sesija.logout();
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}