package com.andreyberdyshev.biblenwt;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.text.method.ScrollingMovementMethod;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;



public class ShowStixActivity extends Activity implements View.OnClickListener {

    final int MENU_SIZE_22 = 1;
    final int MENU_SIZE_26 = 2;
    final int MENU_SIZE_30 = 3;
    final int MENU_SIZE_32 = 4;
    final int MENU_SIZE_34 = 5;
    final int MENU_FIND = 6;

    SharedPreferences sPref;
    final String SAVED_TEXTSIZE = "saved_textsize";
    float NewTextSize;

    String TextofCurrStix;
    //TextView TextHeader;
    TextView TextCurrentStix;
    Button btnGoMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_stix);
        // Show the Up button in the action bar.
        //setupActionBar();
        btnGoMain = (Button) findViewById(R.id.btnGoMain);
        Intent intent = getIntent();
        TextofCurrStix = intent.getStringExtra("textofstix");
        String CurrChapter = intent.getStringExtra("chapter");
        String CurrBook = intent.getStringExtra("book");
        String CurrStix = intent.getStringExtra("stix");
        int LastStix = intent.getIntExtra("LastStix", 1);
        this.setTitle(CurrBook + " " + CurrChapter + ":" + CurrStix + "-" + LastStix);
        //this.setTitleColor(Color.BLACK);
        //	TextHeader = (TextView) findViewById(R.id.textViewStixHeader);
        //	TextHeader.setText(CurrBook+" "+CurrChapter+":"+CurrStix+"-"+LastStix);

        TextCurrentStix = (TextView) findViewById(R.id.textViewShowCurrentStix);
        TextCurrentStix.setText(TextofCurrStix);
        sPref = getPreferences(MODE_PRIVATE);
        float NewTextSize = sPref.getFloat(SAVED_TEXTSIZE, 18);
        TextCurrentStix.setTextSize(NewTextSize);
        TextCurrentStix.setMovementMethod(new ScrollingMovementMethod());
        registerForContextMenu(TextCurrentStix);

        btnGoMain.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGoMain:
                // TODO Call second activity
                Intent intent = new Intent(this, NwtmainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    //@TargetApi(Build.VERSION_CODES.HONEYCOMB)
    //private void setupActionBar() {
    //	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
    //		getActionBar().setDisplayHomeAsUpEnabled(true);
    //	}
    //}


	 @Override
	  public void onCreateContextMenu(ContextMenu menu, View v,
	      ContextMenuInfo menuInfo) {
	    // TODO Auto-generated method stub
	    switch (v.getId()) {
	    case R.id.textViewShowCurrentStix:
	      menu.add(0, MENU_SIZE_22, 0, getResources().getString(R.string.font_size)+" 16");
	      menu.add(0, MENU_SIZE_26, 0, getResources().getString(R.string.font_size)+" 18");
	      menu.add(0, MENU_SIZE_30, 0, getResources().getString(R.string.font_size)+" 20");
	      menu.add(0, MENU_SIZE_32, 0, getResources().getString(R.string.font_size)+" 22");
	      menu.add(0, MENU_SIZE_34, 0, getResources().getString(R.string.font_size)+" 30");
	      menu.add(0, MENU_FIND, 0, getResources().getString(R.string.menu_find));
	      
	      break;
	    }
	  }
	 @Override
	  public boolean onContextItemSelected(MenuItem item) {
	    // TODO Auto-generated method stub
	    switch (item.getItemId()) {
	    // ������ ���� ��� tvColor
	   case MENU_SIZE_22:
		  TextCurrentStix.setTextSize(16);
           NewTextSize=16;
	
	      break;
	    case MENU_SIZE_26:
	     TextCurrentStix.setTextSize(18);
           NewTextSize=18;
	      break;
	    case MENU_SIZE_30:
	    	TextCurrentStix.setTextSize(20);
            NewTextSize=20;
	        break;
	    case MENU_SIZE_32:
	    	TextCurrentStix.setTextSize(22);
            NewTextSize=22;
	        break;
	    case MENU_SIZE_34:
	    	TextCurrentStix.setTextSize(30);
            NewTextSize=30;
	        break;
	    case MENU_FIND:
	    	Toast.makeText(getApplicationContext(), "Find", Toast.LENGTH_SHORT).show();
	    	 Intent intent = new Intent(getApplicationContext(), FindActivity.class) ;
	         startActivity(intent) ;	 
	    	break;
	    }
         sPref = getPreferences(MODE_PRIVATE);
         Editor ed = sPref.edit();
         ed.putFloat(SAVED_TEXTSIZE, NewTextSize);
         ed.commit();

	    return super.onContextItemSelected(item);
	  } 
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_stix, menu);
		return true;
	}


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    
     String GT = ""+item.getTitle();
     if (GT.matches(getResources().getString(R.string.title_activity_find))) {

    	 Toast.makeText(getApplicationContext(), GT, Toast.LENGTH_SHORT).show();
    	 Intent intent = new Intent(getApplicationContext(), FindActivity.class) ;
         startActivity(intent) ;	 
    	 
     } // if (GT.matches("����� �� ������"))
     
      return super.onOptionsItemSelected(item);
    }



}
