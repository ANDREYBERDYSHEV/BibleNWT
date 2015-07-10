package com.andreyberdyshev.biblenwt;

import android.app.Activity;
//import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class UKR extends Activity {

    final int MENU_FIND_M = 1;


    String[] Evr1;
    String[] Grec1;

    ListView lvEvr1;
    ListView lvGrec1;
    MyDatabase dbHelper1;
    final int MENU_FIND = 1;
    final int MENU_ABOUT = 2;
    SharedPreferences sPref;
    final String SAVED_LANG = "saved_lang";


    @Override
    protected void onResume() {
        super.onResume();
        MyApp ValueLangBible=((MyApp)getApplicationContext());
        ValueLangBible.setLangBible("UKR");
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_LANG, "UKR");
        ed.commit();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ukr);
        this.setTitle("Переклад нового світу");
        this.setTitleColor(Color.BLACK);
        dbHelper1 = new MyDatabase(this);

        SQLiteDatabase db = dbHelper1.getWritableDatabase();
        db.close();
        dbHelper1.close();
        ListView lvEvr1 =   (ListView) findViewById (R.id.lvEvrukr) ;
        ListView lvGrec1 =   (ListView) findViewById (R.id.lvGrecukr) ;
        Evr1 = getResources().getStringArray(R.array.Evrukr);
        Grec1 = getResources().getStringArray(R.array.Grecukr);


        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,
                R.layout.my_lay_list, Evr1);
        //����������� ������� ������
        lvEvr1.setAdapter(adapter);


        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this,
                R.layout.my_lay_list,Grec1);
        //����������� ������� ������
        lvGrec1.setAdapter(adapter2);

        //   lvEvr1.setOnItemLongClickListener(new OnItemLongClickListener() {
        lvEvr1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //   	 public boolean onItemLongClick(AdapterView<?> parent, View view,
            //                   int position, long id)   {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)   {


                //	 Toast.makeText(getApplicationContext(), (String) parent.getAdapter().getItem(position), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(parent.getContext(), ChapterActivity.class) ;
                intent.putExtra("book", (String) parent.getAdapter().getItem(position));
                startActivity(intent) ;
//					return false;

            }
        } );
        lvGrec1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)   {

                // Toast.makeText(getApplicationContext(), (String) parent.getAdapter().getItem(position), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(parent.getContext(), ChapterActivity.class) ;
                intent.putExtra("book", (String) parent.getAdapter().getItem(position));
                startActivity(intent) ;


            }



        } );

        registerForContextMenu(lvGrec1);
        registerForContextMenu(lvEvr1);

        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_LANG, "UKR");
        ed.commit();


    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.lvEvrukr:
                menu.add(0, MENU_FIND_M, 0, getString(R.string.menu_poshuk_po_biblii));
                break;
            case R.id.lvGrecukr:
                menu.add(0, MENU_FIND_M, 0, getString(R.string.menu_poshuk_po_biblii));


                break;
        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            // ������ ���� ��� tvColor
            case MENU_FIND_M:

             //   Toast.makeText(getApplicationContext(), "Find", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), FindActivity.class) ;
                startActivity(intent) ;
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ukr, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int aaa = item.getItemId();
        //String GT = ""+item.getTitle();
        //if (GT.matches(getResources().getString(R.string.title_activity_find))) {
        if (aaa==R.id.action_findbible) {
            //    Toast.makeText(getApplicationContext(), GT, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), FindActivity.class);
            startActivity(intent);
        }
        if (aaa==R.id.action_about) {
            Intent intent1 = new Intent(getApplicationContext(), About.class) ;
            startActivity(intent1) ;
        }
        if (aaa==R.id.action_rusbible) {
            //    Toast.makeText(getApplicationContext(), GT, Toast.LENGTH_SHORT).show();
            MyApp ValueLangBible=((MyApp)getApplicationContext());
            ValueLangBible.setLangBible("RUS");
            sPref = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString(SAVED_LANG, "RUS");
            ed.commit();

            Intent intent2 = new Intent(getApplicationContext(), NwtmainActivity.class);
            startActivity(intent2);
        }

        // } // if (GT.matches("����� �� ������"))

        return super.onOptionsItemSelected(item);
    }
}


