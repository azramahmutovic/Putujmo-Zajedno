package com.azra.putujmozajedno;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Azra on 20.08.2015..
 */
public class NoviOglasActivity extends Activity {

    Button ponudaBtn;
    Button potraznjaBtn;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tip_oglasa);

        pref = getApplicationContext().getSharedPreferences("Oglas", 0); // 0 - for private mode
        editor = pref.edit();

        ponudaBtn = (Button) findViewById(R.id.ponudaBtn);
        potraznjaBtn = (Button) findViewById(R.id.potraznjaBtn);

        ponudaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("tip_oglasa", "ponuda");
                editor.commit();
                Intent i = new Intent(getApplicationContext(), NoviOglas2Activity.class);
                startActivity(i);
            }
        });

        potraznjaBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                editor.putString("tip_oglasa","potraznja");
                editor.commit();
                Intent i = new Intent(getApplicationContext(), NoviOglas2Activity.class);
                startActivity(i);
            }
        });

    }
}
