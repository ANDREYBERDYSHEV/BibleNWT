package com.andreyberdyshev.biblenwt;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDatabase extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "NWTDB";
    private static final int DATABASE_VERSION = 1;
    public MyDatabase(Context context) {
    	
    //	super(context, DATABASE_NAME, null, null, DATABASE_VERSION);
        super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath() + "/", null, DATABASE_VERSION);
    }
}
