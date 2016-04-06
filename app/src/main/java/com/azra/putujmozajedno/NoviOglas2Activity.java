package com.azra.putujmozajedno;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Azra on 20.08.2015..
 */
public class NoviOglas2Activity extends ActionBarActivity {

    EditText datumTxt;
    EditText vrijemeTxt;
    EditText polazakTxt;
    EditText odredisteTxt;
    RadioGroup rg;
    RadioButton dogovor;
    RadioButton cijena;
    EditText cijenaTxt;
    Button daljeBtn;
    TextView trosakTxt;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oglas_mjesto);

        pref = getApplicationContext().getSharedPreferences("Oglas", 0); // 0 - for private mode
        editor = pref.edit();

        polazakTxt = (EditText) findViewById(R.id.polazakTxt);
        odredisteTxt = (EditText) findViewById(R.id.odredisteTxt);
        dogovor = (RadioButton) findViewById(R.id.dogovorBtn);
        cijena = (RadioButton) findViewById(R.id.cijenaBtn);

        datumTxt = (EditText) findViewById(R.id.datumTxt);
        datumTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DialogFragment newFragment = new DatePickerFragment();
                    newFragment.show(getFragmentManager(), "DatePicker");
                }
            }
        });


        vrijemeTxt = (EditText) findViewById(R.id.vrijemeTxt);

        vrijemeTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    DialogFragment newFragment = new TimePickerFragment();
                    newFragment.show(getFragmentManager(), "TimePicker");
                }
            }
        });

        rg = (RadioGroup) findViewById(R.id.radioGroup);
        cijenaTxt = (EditText) findViewById(R.id.cijenaTxt);
        trosakTxt = (TextView) findViewById(R.id.trosakTxt);

        if(pref.getString("tip_oglasa", null).equals("potraznja")){
            //sakrij RadioGroup
            rg.setVisibility(View.GONE);
            //sakrij unos cijene
            trosakTxt.setVisibility(View.GONE);
        }
        else{
            dogovor.setChecked(true);
        }

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.dogovorBtn:
                        cijenaTxt.setVisibility(View.GONE);
                        break;
                    case R.id.cijenaBtn:
                        cijenaTxt.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        daljeBtn = (Button) findViewById(R.id.daljeBtn);
        daljeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(polazakTxt.getText().toString().trim().length() > 0 && odredisteTxt.getText().toString().trim().length() > 0
                        && datumTxt.getText().toString().trim().length() > 0){

                    editor.putString("polazak", polazakTxt.getText().toString());
                    editor.putString("odrediste", odredisteTxt.getText().toString());
                    editor.putString("datum", datumTxt.getText().toString());
                    editor.putString("vrijeme", vrijemeTxt.getText().toString());
                    if(cijena.isChecked()){
                        editor.putString("trosak","cijena");
                        editor.putString("cijena", cijenaTxt.getText().toString());
                    }
                    else if(dogovor.isChecked()){
                        editor.putString("trosak", "dogovor");
                    }
                    else{
                        editor.putString("trosak", "");
                    }

                    editor.commit();
                    Intent i = new Intent(getApplicationContext(), NoviOglas3Activity.class);
                    startActivity(i);
                }
                else if(polazakTxt.getText().toString().trim().length() == 0){
                    Toast.makeText(getApplicationContext(), "Unesite mjesto polaska", Toast.LENGTH_SHORT).show();
                }
                else if(odredisteTxt.getText().toString().trim().length() == 0){
                    Toast.makeText(getApplicationContext(), "Unesite odredište", Toast.LENGTH_SHORT).show();
                }
                else if(datumTxt.getText().toString().trim().length() == 0){
                    Toast.makeText(getApplicationContext(), "Unesite datum polaska", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}


