package com.andreyberdyshev.biblenwt;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.content.ClipboardManager;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ShowStixInList extends Activity {

    ListView ListStixNew;

    SharedPreferences sPref;
    final String SAVED_TEXT_SIZE = "saved_text_size";

    final int MENU_GET_REFERENCES =1;
    final int MENU_COPY_TO_CLIPBOARD=2;
    final int MENU_FONT_UP=3;
    final int MENU_FONT_DOWN=4;

    final int DIALOG = 1;
    String CurrChapter;
    String CurrBook;
    String CurrStix;
    MyDatabase dbHelper1;
    String AS;
    DialogFragment dlg2;
    MyApp COMMENT;
    String SSS1;
    int CurrentTextSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_stix_in_list);
        ListStixNew =  (ListView) findViewById (R.id.listViewNewStix) ;
        Intent intent = getIntent();
        CurrChapter = intent.getStringExtra("chapter");
        CurrBook = intent.getStringExtra("book");
        CurrStix = intent.getStringExtra("stix");
        int LastStix = intent.getIntExtra("LastStix", 1);
        this.setTitle(CurrBook + " " + CurrChapter + ":" + CurrStix + "-" + LastStix);
        //this.setTitleColor(Color.BLACK);

        //    ListStix = new ArrayList<~>();
        //ArrayList ListStix;// = new ArrayList<String>();

        sPref = getPreferences(MODE_PRIVATE);

        CurrentTextSize = sPref.getInt(SAVED_TEXT_SIZE, 18);

        ArrayList   ListStix = intent.getStringArrayListExtra("textofstix");

         ArrayAdapter<String> adapter = new MySimpleAdapter(this,
                R.layout.show_stix_in_list_adapter, R.id.textViewForList,ListStix);
/*
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.show_stix_in_list_adapter, R.id.textViewForList,ListStix);
*/
        ListStixNew.setAdapter(adapter);
//        ListStixNew.requestFocus();
        registerForContextMenu(ListStixNew);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.listViewNewStix:
                    menu.add(0, MENU_GET_REFERENCES, 0, getResources().getString(R.string.Get_references));
                    menu.add(1, MENU_COPY_TO_CLIPBOARD, 0, getResources().getString(R.string.Copy_to_clipboard));
                menu.add(2, MENU_FONT_UP, 0, getString(R.string.font_up));
                menu.add(3, MENU_FONT_DOWN, 0, getString(R.string.font_down));

                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //int iii= info.position;
        TextView Stix= (TextView) info.targetView.findViewById(R.id.textViewForList);
        String SSS = (String) Stix.getText();
        int k = SSS.indexOf("*");
        AS="";

        if (k!=-1)
        {
            int i = SSS.indexOf(" ");
            SSS1 = SSS.substring(0, i);
            SSS1 = SSS1.trim();

            dbHelper1 = new MyDatabase(this);
            SQLiteDatabase db = dbHelper1.getWritableDatabase();
            String Query = "select * from refukr where booku='" + CurrBook + "' and numchapter=" + CurrChapter + " and numstix=" + SSS1 + ";";
            Cursor c = db.rawQuery(Query, null);
            int c_count=c.getCount();
            int comment_index=c.getColumnIndex("comment");
            c.moveToFirst();
            int kk=0;

            do {
                kk=kk+1;
                String sK= String.valueOf(kk);
                sK.trim();
                AS =  AS + sK + "* - "+c.getString(comment_index)+" \r\n";
            } while (c.moveToNext());

            c.close();
            db.close();
            dbHelper1.close();

            }

        switch (item.getItemId()) {
            case MENU_GET_REFERENCES:
                if (k==1){
                    Toast.makeText(getApplicationContext(), getString(R.string.nemae_primitok), Toast.LENGTH_SHORT).show();
                 break;}
                AlertDialog.Builder adb = new AlertDialog.Builder(this);
                adb.setTitle(CurrBook+" "+CurrChapter+":"+SSS1);
//                adb.setPositiveButton("OK", (android.content.DialogInterface.OnClickListener) this);
                adb.setMessage(AS);
                adb.create();
                adb.show();
              //  Toast.makeText(getApplicationContext(), AS, Toast.LENGTH_SHORT).show();
                break;
            case MENU_COPY_TO_CLIPBOARD:
               ClipboardManager clpb =  (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                clpb.setPrimaryClip(ClipData.newPlainText("1",SSS));
                Toast.makeText(getApplicationContext(), getString(R.string.Virsh_ckopiyovano), Toast.LENGTH_SHORT).show();

                break;
            case MENU_FONT_UP:
                CurrentTextSize=CurrentTextSize+5;
                sPref = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putInt(SAVED_TEXT_SIZE, CurrentTextSize);
                ed.commit();
                ListStixNew.invalidateViews();
                break;

            case MENU_FONT_DOWN:
                if (CurrentTextSize>10) {
                    CurrentTextSize = CurrentTextSize - 5;
                    sPref = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor ed1 = sPref.edit();
                    ed1.putInt(SAVED_TEXT_SIZE, CurrentTextSize);
                    ed1.commit();
                    ListStixNew.invalidateViews();
                }


                break;

        }
        return super.onContextItemSelected(item);
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_stix_in_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
//}

public class MySimpleAdapter extends ArrayAdapter<String>{

    public MySimpleAdapter(Context context, int resource, int textViewResourceId, ArrayList value){

        super(context, resource, textViewResourceId, value);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View returnView = super.getView(position, convertView, parent);
        TextView shopName = (TextView)returnView.findViewById(R.id.textViewForList);
        shopName.setTextSize(CurrentTextSize);
        return returnView;
    }

}
}