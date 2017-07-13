package com.example.gandh.inclass07;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by gandh on 2/27/2017.
 */

public class Note_table {

    static String tablename= "notes";
    static String coulmn_id = "_id";
    static   String column_subject = "subject";
    static   String coulmn_priority = "priority";
    static   String column_status = "status";


    static  void oncreate(SQLiteDatabase db)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " +tablename+"(");
        sb.append(coulmn_id+" integer primary key autoincrement,");
        sb.append(column_subject +" text not null ,");
        sb.append(coulmn_priority +" text not null, ");
        sb.append(column_status +" text not null );");
        try {
            db.execSQL(sb.toString());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    static void onupdate(SQLiteDatabase db, int old, int neneww)
    {

        db.execSQL("DROP TABLE if EXISTS "+tablename);
        Note_table.oncreate(db);

    }

}