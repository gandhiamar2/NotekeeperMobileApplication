package com.example.gandh.inclass07;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gandh on 2/27/2017.
 */

public class Note_Dao {

    private SQLiteDatabase db;

    public Note_Dao (SQLiteDatabase db) {
        this.db = db;
    }
    ArrayList<Note> notes11 = new ArrayList<Note>();
    long save(Note notes)
    {
        ContentValues cv = new ContentValues();
        cv.put(Note_table.column_subject,notes.subject);
        cv.put(Note_table.coulmn_priority,notes.priority);
        cv.put(Note_table.column_status,notes.status);
        return db.insert(Note_table.tablename,null,cv);
    }

    boolean update(Note notes)
    {
        ContentValues cv = new ContentValues();
        cv.put(Note_table.column_subject,notes.subject);
        cv.put(Note_table.coulmn_priority,notes.priority);
        cv.put(Note_table.column_status,notes.status);

        return db.update(Note_table.tablename,cv,Note_table.coulmn_id+"=?",new String[]{notes.id+""})>0;
    }

    boolean delete(Note notes)
    {
        return  db.delete(Note_table.tablename,Note_table.coulmn_id+"=?",new String[]{notes.id+""})>0;
    }

    Note get(Long id)
    {
        Note note = null;
        Cursor c=   db.query(true,Note_table.tablename,new String[]{Note_table.coulmn_id,Note_table.column_subject,
                Note_table.coulmn_priority,Note_table.column_status}
                ,Note_table.coulmn_id+"=?",new String[]{id+""},null,null,null,null);

        if(c!=null && c.moveToFirst())
        {
            note = notefromcurso(c);
            if(!c.isClosed())
                c.close();
        }

        return note;
    }

    Note notefromcurso(Cursor c)
    {
        Log.d("test","entered c");
        Note note = null;
        if(c!=null)
        {
            note = new Note();
            note.setId(c.getLong(0));

            note.setSubject(c.getString(1));

            note.setPriority(c.getString(2));

            note.setStatus(c.getString(3));

            return note;
        }

        return  null;


    }


    List<Note> getall()
    {

        Cursor c=   db.query(true,Note_table.tablename,new String[]{Note_table.coulmn_id,Note_table.column_subject,
                Note_table.coulmn_priority,Note_table.column_status},null,null,null,null,null,null);

        if(c!=null && c.moveToFirst())
        {
            do {
                Note note12 = notefromcurso(c);
                if(note12!=null) {
                    Log.d("testobj", note12.toString());
                    notes11.add(note12);
                    Log.d("testlist", notes11.toString());
                }

            }while (c.moveToNext());
            if(!c.isClosed())
            c.close();
        }
        Log.d("test", notes11.toString());
        return notes11;
    }
}
