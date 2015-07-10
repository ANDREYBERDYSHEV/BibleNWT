package com.andreyberdyshev.biblenwt;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class NwtmainActivity extends Activity {

    final int MENU_FIND_M = 1;


    String[] Evr1;
    String[] Grec1;
	
	ListView lvEvr1;
	ListView lvGrec1;
	MyDatabase dbHelper1;
	final int MENU_FIND = 1;
    final int MENU_ABOUT = 2;
    MyApp ValueLangBible;
    SharedPreferences sPref;
    final String SAVED_LANG = "saved_lang";

    @Override
    protected void onResume() {
        super.onResume();
        MyApp ValueLangBible=((MyApp)getApplicationContext());
        ValueLangBible.setLangBible("RUS");

    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nwtmain);
        this.setTitle(R.string.main_title);
        this.setTitleColor(Color.BLACK);


        ValueLangBible=((MyApp)getApplicationContext());
        ValueLangBible.setLangBible("RUS");
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_LANG, "RUS");
        ed.commit();

        dbHelper1 = new MyDatabase(this);
        SQLiteDatabase db = dbHelper1.getWritableDatabase();
		 db.close();
		 dbHelper1.close();
		ListView lvEvr1 =   (ListView) findViewById (R.id.lvEvr) ; 
		ListView lvGrec1 =   (ListView) findViewById (R.id.lvGrec) ; 
		Evr1 = getResources().getStringArray(R.array.Evr);
		Grec1 = getResources().getStringArray(R.array.Grec);
				
		
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,
				R.layout.my_lay_list, Evr1);
				//����������� ������� ������
				lvEvr1.setAdapter(adapter);
				
		
		ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this,
				R.layout.my_lay_list,Grec1);
						//����������� ������� ������
						lvGrec1.setAdapter(adapter2);
	
	 //   lvEvr1.setOnItemLongClickListener(new OnItemLongClickListener() {
						lvEvr1.setOnItemClickListener(new OnItemClickListener() {
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
	    lvGrec1.setOnItemClickListener(new OnItemClickListener() {
	    	
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

        String SavedLang = sPref.getString(SAVED_LANG, "RUS");
        if (SavedLang=="UKR")
        {
            ValueLangBible.setLangBible("UKR");
            Intent intent2 = new Intent(getApplicationContext(), UKR.class);
            startActivity(intent2);

        }


    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.lvEvr:
                menu.add(0, MENU_FIND_M, 0, getResources().getString(R.string.menu_find));
                break;
            case R.id.lvGrec:
                menu.add(0, MENU_FIND_M, 0, getResources().getString(R.string.menu_find));


                break;
        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            // ������ ���� ��� tvColor
            case MENU_FIND_M:

               // Toast.makeText(getApplicationContext(), "Find", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), FindActivity.class) ;
                startActivity(intent) ;
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nwtmain, menu);
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
        if (aaa==R.id.action_ukrbible) {
            //    Toast.makeText(getApplicationContext(), GT, Toast.LENGTH_SHORT).show();
            sPref = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString(SAVED_LANG, "UKR");
            ed.commit();

            ValueLangBible.setLangBible("UKR");
            Intent intent2 = new Intent(getApplicationContext(), UKR.class);
            startActivity(intent2);
        }

    // } // if (GT.matches("����� �� ������"))
     
      return super.onOptionsItemSelected(item);
    }
}




