package com.andreyberdyshev.biblenwt;

import java.util.ArrayList;
import java.util.Map;

import android.app.Application;

public class MyApp extends Application {
	
	private ArrayList<Map<String, String>> privatedata = new ArrayList<Map<String, String>>(0);
	private String LastTitle = "";
    private String FindText = "";
	
    private String LangBible="";
    private String MESS="";
    private String TITLE="";

    public String getMESS () {return MESS;}
    public void setMESS (String MESSMESS) {this.MESS=MESSMESS;}

    public String getTITLE () {return TITLE;}
    public void setTITLE (String TITLETITLE) {this.TITLE=TITLETITLE;}

    public String getLangBible () {return LangBible;}

    public void setLangBible (String LB) {this.LangBible=LB;}

    public String getLastFindext (){
    	return FindText;
    }
    
    public void setFindText(String FT){
    	this.FindText = FT;
    }
    
	public ArrayList<Map<String, String>> getMapData() {return privatedata;}
	
	public String getLastTitle() {return LastTitle;}
	
	public void setLastTitle(String LT){
		this.LastTitle = LT;
	}
	
	public void setMapData(ArrayList<Map<String, String>> mydata) {
		this.privatedata.clear();
		this.privatedata.addAll(mydata);
		
	}
	
}
