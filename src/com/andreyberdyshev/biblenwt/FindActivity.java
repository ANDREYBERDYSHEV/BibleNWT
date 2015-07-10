package com.andreyberdyshev.biblenwt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.util.Log;


public class FindActivity extends Activity {

    final int MENU_TOOLS_SMS=1;
    final int MENU_TOOLS_SMS_TRANS=2;
    //final String LOG_TAG = "myLogs";

    MyDatabase dbHelper;
	ListView FindList;
    EditText myFindText;
    Button FindButton;
    MyApp aps;
    ArrayList<Map<String, String>> data;
    Handler h;



    int DataCount;
    ProgressBar pb;
    String fText;
    Transliterator Tr;
    String sqlQuery;
    MyApp ValueLangBible;
    String LB;
    Cursor c;


    @SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find);

        ValueLangBible=((MyApp)getApplicationContext());
        LB = ValueLangBible.getLangBible();

		
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		pb.setVisibility(View.INVISIBLE);
		dbHelper = new MyDatabase(this);
//		Toast.makeText(getApplicationContext(), "������ ������!", Toast.LENGTH_LONG).show();
//		if (aps==null) {aps = new MyApp();}
		aps = (MyApp)getApplication();
	    String LLT = aps.getLastTitle();
		this.setTitle(LLT);
        //this.setTitleColor(Color.BLACK);
		myFindText = (EditText) findViewById(R.id.editFindText);
		String FT = aps.getLastFindext();
		myFindText.setText(FT);
		
		FindList =  (ListView) findViewById (R.id.listFind) ; 
  		 
		if (!aps.getMapData().isEmpty()) {
					
			String[] from ={"title","stix"};
	   		 int[] to={R.id.TitleField,R.id.textStixField};
	   		 
	   		 
	   		 SimpleAdapter sAdapter = new SimpleAdapter(getApplicationContext(), aps.getMapData(), R.layout.show_find_result,
	   		        from, to);
		   		FindList.setAdapter(sAdapter);
			
		}
		
		FindButton = (Button) findViewById(R.id.buttonGoFind);
		// ������� ����������� ����� ���������� ������ � ������, ����������� �� ������� ������

            h = new Handler() {
                public void handleMessage(android.os.Message msg) {

                    if (LB == "RUS") {

                        FindActivity.this.setTitle(getResources().getString(R.string.title_reslut_find_window) + "_" + DataCount);
                    } else
                    {
                        FindActivity.this.setTitle(getResources().getString(R.string.title_reslut_find_windowukr) + "_" + DataCount);

                    }
                    String[] from = {"title", "stix"};
                    int[] to = {R.id.TitleField, R.id.textStixField};

                    SimpleAdapter sAdapter = new SimpleAdapter(getApplicationContext(), aps.getMapData(), R.layout.show_find_result,
                            from, to);
                    FindList.setAdapter(sAdapter);
                    FindList.requestFocus();
                    pb.setVisibility(View.INVISIBLE);

                }

            };

        registerForContextMenu(FindList);
	}

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.listFind:
                if (LB=="RUS") {

                    menu.add(0, MENU_TOOLS_SMS, 0, getResources().getString(R.string.MenuToolsFind1));
                    menu.add(1, MENU_TOOLS_SMS_TRANS, 0, getResources().getString(R.string.MenuToolsFindTRANS));
                } else
                {
                    menu.add(0, MENU_TOOLS_SMS, 0, getResources().getString(R.string.MenuToolsFind1ukr));
                    menu.add(1, MENU_TOOLS_SMS_TRANS, 0, getResources().getString(R.string.MenuToolsFindTRANSukr));
                }
                break;
        }
    }




    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
      /* int iii= info.position; */

        TextView Stix= (TextView) info.targetView.findViewById(R.id.textStixField);
        TextView TitleStix= (TextView) info.targetView.findViewById(R.id.TitleField);
        String sss = Stix.getText()+" - "+TitleStix.getText();


        switch (item.getItemId()) {
            case MENU_TOOLS_SMS:
                Uri uri = Uri.parse("smsto:");
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", sss);
                startActivity(it);
                break;
            case MENU_TOOLS_SMS_TRANS:
                Tr = new Transliterator();
                Uri uri1 = Uri.parse("smsto:");
                Intent it1 = new Intent(Intent.ACTION_SENDTO, uri1);
                sss = Tr.transliterate(sss);
                it1.putExtra("sms_body", sss);
                startActivity(it1);
                break;

        }
        return super.onContextItemSelected(item);
    }




    public void onclick(View v)
			   {
		         
		//		FindButton.setEnabled(false);   

		    	   switch (v.getId()) {
		    	case R.id.buttonGoFind:
		    		myFindText = (EditText) findViewById(R.id.editFindText);
		    		pb.setVisibility(View.VISIBLE);
		    		fText=myFindText.getText().toString();
		    	Thread t = new Thread(new Runnable()
		    	{
	        	
		    	public void run()
		    	{
				
				
		   		SQLiteDatabase db = dbHelper.getWritableDatabase();

                /*
		   		String sqlQuery="select a._id as id, a.book as book, " +
		   				"a.chapter as chapter, b.numstix as numstix, " +
		   				"b.stix as stix from main1 as a, maindetail as b where b.linktoid=id";
                */


                    sqlQuery = "select * from maindetail";
                //    else
                //    sqlQuery = "select booku as book, numchapter, numstix, stixu as stix from maindetail";


                    c = db.rawQuery(sqlQuery,null);


                    c.moveToFirst();

                    int ColStix;
                    int ColBook;
//LB="UKR";
                if (LB=="RUS") {

                    ColStix = c.getColumnIndex("stix");

                    ColBook = c.getColumnIndex("book");
                } else {
                    ColStix = c.getColumnIndex("stixu");
                    ColBook = c.getColumnIndex("booku");
                }

                    int ColChapter=c.getColumnIndex("numchapter");

                    int ColNumStix=c.getColumnIndex("numstix");
		   		
		   		int CountRec = c.getCount();
		   			
		   		final ArrayList<String>Chapters = new ArrayList<String>();
		   		final ArrayList<String>Titles = new ArrayList<String>();

                    String StrFind;
                    String Titles1;
//                    Toast.makeText(getApplicationContext(), "Begin for (int i=1; i < CountRec+1; ++i)", Toast.LENGTH_SHORT).show();
		   		for (int i=1; i < CountRec+1; ++i)
                //    for (int i=1; i<19000; ++i)
		   		{
		   			
		   		if (i==CountRec) break;

                    StrFind = c.getString(ColStix);

      //              Log.d(LOG_TAG, "***"+StrFind+"***"+c.getString(ColBook)+" "+c.getString(ColChapter)+":"+c.getString(ColNumStix));
                    if (StrFind.indexOf(fText)>0)
		   			{
		   				

                        Titles1 = c.getString(ColBook)+
                                " "+c.getString(ColChapter)+":"+c.getString(ColNumStix);

                        Titles.add(Titles1);

		   			Chapters.add(StrFind);
		   		
		   			//	Toast.makeText(getApplicationContext(), "�������: "+Titles1, Toast.LENGTH_SHORT).show();
		   			
		   			}
		   			c.moveToNext();
		   			
		   		}
		   		c.close();
		   		db.close();
		//ArrayList<Map<String, String>> data;
		   		data = new ArrayList<Map<String, String>>(Titles.size());
	   	
		   		Map<String, String> m;
	   		    
	   		 for (int i=0; i < Titles.size(); ++i)
		   		{
	   			  m = new HashMap<String, String>();
		   		  m.put("title", Titles.get(i).toString());
		   		  m.put("stix", Chapters.get(i).toString());
		   		  data.add(m);

		   		  
		   		}
	   		 DataCount=Titles.size();
	   		 Titles.clear();
	   		 Chapters.clear();
	    	   MyApp aps = (MyApp)getApplication();
	    		 aps.setLastTitle("Найдено: "+DataCount);
	    		 aps.setMapData(data);
	    		 aps.setFindText(fText);
		   		 data.clear();
		   		 h.sendEmptyMessage(0);
	   		 	}; 
		    	});
		    	t.start();
		   	break;
		   		default:
		   			break;
		    	} //	if (v.getId()=R.id.buttonGoFind)	
		       } //public void onClick(View v)
		     
		
			
	
		
			

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.find, menu);
		return true;
	}
	

}


