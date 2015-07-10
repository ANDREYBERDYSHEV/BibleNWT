package com.andreyberdyshev.biblenwt;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;
//import android.content.ContentValues;

public class StixActivity extends Activity {

    final int MENU_FIND_C=1;

	GridView GridStix;
//    TextView StixText;
    MyDatabase dbHelper1;
    ArrayAdapter<String> adapter;
    String bookname;
    String numchapter;
    String CurrentStix;
    int CountRec;
    String LB;
    //String sqlQuery;
    MyApp ValueLangBible;
    int numColWithStix;
    Cursor c;
    String sqlQuery = "select *  from maindetail  where  book='" + bookname + "' and numchapter=" + numchapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stix);
		Intent intent = getIntent();
	//	StixText = (TextView) findViewById(R.id.ChapterText2);
		GridStix = (GridView) findViewById(R.id.gridViewStix);
		//int idnumber=intent.getIntExtra("idnumber", 1);
		bookname = intent.getStringExtra("book");
		numchapter = intent.getStringExtra("chapter");

        ValueLangBible=((MyApp)getApplicationContext());
        LB = ValueLangBible.getLangBible();
		 dbHelper1 = new MyDatabase(this);
		 
	    SQLiteDatabase db = dbHelper1.getWritableDatabase();

        numchapter=numchapter.trim();

        //     c = db.query("main1", null, "booku=" + "'" + bookname + "'", null, null, null, null);
//String   sqlQuery = "select a._id as id, a.book as book, a.chapter as chapter, b.numstix as numstix, b.stix as stix from main1 as a, maindetail as b where b.linktoid=id and book='" + bookname + "' and chapter=" + numchapter;
        //            String sqlQuery1 = "select a._id as id, a.booku as book, a.chapter as chapter, b.numstix as numstix, b.stixu as stix from main1 as a, maindetail as b where b.linktoid=id and booku='" + bookname + "' and chapter=" + numchapter;
        if (LB=="RUS")
            sqlQuery = "select *  from maindetail  where  book='" + bookname + "' and numchapter=" + numchapter;
        else
            sqlQuery = String.format("select *  from maindetail  where  booku='%s' and numchapter=%s", bookname, numchapter);

        c = db.rawQuery(sqlQuery,null);

				 
	//	Cursor c=	db.query("maindetail", null, "_id="+idnumber, null, null, null, null);
	//	c.moveToFirst();
		CountRec = c.getCount();
		//Toast.makeText(getApplicationContext(), "������� ������: "+CountRec, Toast.LENGTH_LONG).show();
		this.setTitle(bookname+" "+numchapter+":1-"+CountRec);
        this.setTitleColor(Color.BLACK);
		//StixText.setText(bookname+" "+numchapter+":1-"+CountRec);
		final ArrayList<String>Stix = new ArrayList<String>();
		try {
            for (int i = 1; i < CountRec + 1; ++i) {
                Stix.add(" " + i + " ");
            }
        }
finally {
		c.close();
		db.close();}
		 adapter=new ArrayAdapter<String>(this,
				R.layout.item,R.id.tvText,Stix);
				//����������� ������� ������
				GridStix.setAdapter(adapter);
				GridStix.setNumColumns(GridView.AUTO_FIT);
				GridStix.setColumnWidth(80);
				GridStix.setVerticalSpacing(5);
				GridStix.setHorizontalSpacing(5);
				GridStix.setStretchMode(GridView.STRETCH_SPACING_UNIFORM);
				
				
				
// select a._id, a.book, a.chapter, b.numstix, b.stix from main1 a, maindetail b where b.linktoid=a._id and a.book="�����" and a.chapter=5 and b.numstix=1
	
				//GridStix.setOnItemLongClickListener(new OnItemLongClickListener() {
				GridStix.setOnItemClickListener(new OnItemClickListener() {
			    	 public void onItemClick(AdapterView<?> parent, View view,
			                    int position, long id)   {
			    		 
			    		 //Toast.makeText(getApplicationContext(), (String) parent.getAdapter().getItem(position), Toast.LENGTH_LONG).show();
			    	
			    		 SQLiteDatabase db = dbHelper1.getWritableDatabase();
			    	  	 String NumStix = (String) parent.getAdapter().getItem(position);
                         if (LB=="RUS") {


                             sqlQuery = "select *  from maindetail  where  book='" + bookname + "' and numchapter=" + numchapter + " and numstix>=" + NumStix;


                         } else
                         {
                             sqlQuery = "select *  from maindetail  where  booku='" + bookname + "' and numchapter=" + numchapter + " and numstix>=" + NumStix;

                         }
			    		 //String sqlQuery="select stix from maindetail where stix like '130.'";
                         c = db.rawQuery(sqlQuery,null);

                         final ArrayList<String>ListStix = new ArrayList<String>();

			    		 CurrentStix="";
			    		 if (c.moveToFirst()){
                             if (LB=="RUS") {

                                 numColWithStix = c.getColumnIndex("stix");
                             } else
                             {
                                 numColWithStix = c.getColumnIndex("stixu");
                             }
			    		 do
			    		 {
                             if (LB=="RUS") {

                                 CurrentStix = CurrentStix + " \r\n\r\n" + c.getString(numColWithStix);
                             } else
	// **************** ADD TO LIST
                             {
                                 ListStix.add(c.getString(numColWithStix));
                             }
			    		 } while (c.moveToNext());
			    		 }

                    Intent intent;
                         if (LB=="RUS") {

                             intent = new Intent(parent.getContext(), ShowStixActivity.class);
                             intent.putExtra("textofstix", CurrentStix);

                         } else {
                              intent = new Intent(parent.getContext(), ShowStixInList.class);
                              intent.putExtra("textofstix", ListStix);

                         }

                            intent.putExtra("book", bookname);
			    		    intent.putExtra("chapter", numchapter);
			    		    intent.putExtra("LastStix", CountRec);
			                intent.putExtra("stix", (String) parent.getAdapter().getItem(position));
			    		    startActivity(intent) ;
							
			                
			            }
						          } );


        registerForContextMenu(GridStix);
	
	}


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.gridViewStix:
                menu.add(0, MENU_FIND_C, 0, getResources().getString(R.string.menu_find));

                break;
        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case MENU_FIND_C:
                Toast.makeText(getApplicationContext(), "Find", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), FindActivity.class) ;
                startActivity(intent) ;
                break;
        }
        return super.onContextItemSelected(item);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stix, menu);
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




