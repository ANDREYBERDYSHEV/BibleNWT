package com.andreyberdyshev.biblenwt;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class StartActivity extends Activity implements View.OnClickListener {

    SharedPreferences sPref;
    final String SAVED_LANG = "saved_lang";
    final String SAVED_TEXT_SIZE = "saved_text_size";


    Button btnLANGukr;
    Button btnLANGrus;
    String SavedLang;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);


        btnLANGrus  = (Button) findViewById(R.id.btnStartRus);
        btnLANGukr  = (Button) findViewById(R.id.btnStartUKR);
        btnLANGrus.setOnClickListener(this);
        btnLANGukr.setOnClickListener(this);

        sPref = getPreferences(MODE_PRIVATE);

        SavedLang = sPref.getString(SAVED_LANG, " ");

        if (SavedLang.equals("UKR"))
        {
            Intent intent2 = new Intent(getApplicationContext(), UKR.class);
            startActivity(intent2);
        } else
        if (SavedLang.equals("RUS"))
        {
            Intent intent3 = new Intent(getApplicationContext(), NwtmainActivity.class);
            startActivity(intent3);

        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_ukrbible_start) {
            sPref = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString(SAVED_LANG, "UKR");
            ed.commit();

           // ValueLangBible.setLangBible("UKR");
            Intent intent2 = new Intent(getApplicationContext(), UKR.class);
            startActivity(intent2);

            return true;
        }
        if (id == R.id.action_rusbible_start) {
            MyApp ValueLangBible=((MyApp)getApplicationContext());
            ValueLangBible.setLangBible("RUS");
            sPref = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString(SAVED_LANG, "RUS");
            ed.commit();

            Intent intent2 = new Intent(getApplicationContext(), NwtmainActivity.class);
            startActivity(intent2);


            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartRus:
                // TODO Call second activity
                sPref = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString(SAVED_LANG, "RUS");
                ed.commit();
                Intent intent = new Intent(this, NwtmainActivity.class);
                startActivity(intent);
                break;
            case R.id.btnStartUKR:
                // TODO Call second activity
                sPref = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor ed1 = sPref.edit();
                ed1.putString(SAVED_LANG, "UKR");
                ed1.commit();
                Intent intent1 = new Intent(this, UKR.class);
                startActivity(intent1);
                break;

            default:
                break;
        }
    }




}

