package com.example.gandh.inclass07;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by gandh on 2/27/2017.
 */

public class Note_Manager {

    Context context;
    private Note_Openhelper openHelper;
    private SQLiteDatabase db;
    static private Note_Dao notedad;

    public static Note_Dao getNotedad() {
        return notedad;
    }

    public Note_Manager(Context context) {
        this.context = context;
        openHelper = new Note_Openhelper(context);
        db = openHelper.getWritableDatabase();
      //  openHelper.onCreate(db);
        notedad = new Note_Dao(db);
    }

    void close(){
        if(db!=null)
        {
            db.close();
        }
    }
}
