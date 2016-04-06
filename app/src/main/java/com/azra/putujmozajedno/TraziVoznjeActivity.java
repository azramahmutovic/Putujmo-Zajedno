package com.azra.putujmozajedno;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Azra on 23.08.2015..
 */
public class TraziVoznjeActivity extends Activity {

    EditText traziMjestoTxt;
    EditText traziOdredisteTxt;
    Button traziBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trazi_voznje);

        traziMjestoTxt = (EditText) findViewById(R.id.traziMjestoTxt);
        traziOdredisteTxt = (EditText) findViewById(R.id.traziOdredisteTxt);
        traziBtn = (Button) findViewById(R.id.traziBtn);

        traziBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SveVoznjeActivity.class);
                intent.putExtra("activity", "pretraga");
                intent.putExtra("polazak", traziMjestoTxt.getText().toString());
                intent.putExtra("odrediste", traziOdredisteTxt.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }
}
