package com.example.gandh.inclass07;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar t;
    ListView lv;
    Spinner sp;
    EditText et;
    Button add;
    List_adaptor adaptor, adaptor1;
    Note_Manager manager;
    List<Note> notes, notes1;
    Note_Dao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t=(Toolbar)findViewById(R.id.tool_bar);
        add = (Button) findViewById(R.id.button);
        sp = (Spinner) findViewById(R.id.spinner);
        lv = (ListView) findViewById(R.id.lv1);
        et = (EditText) findViewById(R.id.editText);
        manager = new Note_Manager(this);
        setSupportActionBar(t);
        dao = manager.getNotedad();
        notes = dao.getall();
        adaptor = new List_adaptor(MainActivity.this,R.layout.note_item ,notes);
        adaptor.setNotifyOnChange(true);
        lv.setAdapter(adaptor);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
  Note note=new Note(et.getText().toString(),sp.getSelectedItem().toString(),"pending");
               dao.save(note);
                adaptor.add(note);
                Log.d("demooooo",notes.toString());
                et.setText("");
              // sp.setPromptId();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.all:
                Collections.sort(notes, new Comparator<Note>() {

                    @Override
                    public int compare(Note o1, Note o2) {
                        if(o1.getStatus().equals("completed"))
                            return -1;

                        else
                            return 1;

                    }
                });

                adaptor = new List_adaptor(MainActivity.this,R.layout.note_item ,notes);
                adaptor.setNotifyOnChange(true);
                lv.setAdapter(adaptor);

                break;
            case R.id.completed:
                notes1 = new ArrayList<>();
                for (Note n :
                        notes) {
                    if(n.getStatus().equals("completed"))
                    {
                        notes1.add(n);
                    }
                    else
                    {

                    }
                }


                adaptor = new List_adaptor(MainActivity.this,R.layout.note_item ,notes1);
                adaptor.setNotifyOnChange(true);
                lv.setAdapter(adaptor);

                break;
            case R.id.Sortbytime:
                Collections.sort(notes, new Comparator<Note>() {

                    @Override
                    public int compare(Note o1, Note o2) {
                        return 0;
                    }
                });



                break;

            case R.id.Sortbyprior:
                Collections.sort(notes, new Comparator<Note>() {

                    @Override
                    public int compare(Note o1, Note o2) {

                        if(o1.getPriority().equals("Low"))
                            return 2;

                        else if(o1.getPriority().equals("High")) {
                            return -1;
                        }
                       else if (o1.getPriority().equals("Medium")) {
                            return 1;
                        }
                           else
                            return 0;

                    }
                });

                adaptor = new List_adaptor(MainActivity.this,R.layout.note_item ,notes);
                adaptor.setNotifyOnChange(true);
                lv.setAdapter(adaptor);

                break;

            case R.id.pending:
                notes1 = new ArrayList<>();

                for (Note n :
                        notes) {
                    if(n.getStatus().equals("completed"))
                    {

                    }
                    else
                    {
                        notes1.add(n);
                    }
                }

                adaptor = new List_adaptor(MainActivity.this,R.layout.note_item ,notes1);
                adaptor.setNotifyOnChange(true);
                lv.setAdapter(adaptor);

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
