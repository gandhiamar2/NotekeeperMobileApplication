package com.example.gandh.inclass07;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gandh on 2/27/2017.
 */

public class Note_Openhelper extends SQLiteOpenHelper{

    static String name = "notes.db";
    static  int version = 6;

    public Note_Openhelper(Context context)
    {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Note_table.oncreate(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Note_table.onupdate(db,oldVersion,newVersion);
    }
}
