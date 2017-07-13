package com.example.gandh.inclass13;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.UUID;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;


public class MainActivity extends AppCompatActivity implements List_adaptor.onclickcart {

    Toolbar t;
    ListView lv;
    Spinner sp;
    EditText et;
    Button add;
    SimpleDateFormat dt;
    RecyclerView rv;
    ArrayList<Note> notesList;
    List_adaptor adaptor;
    int size=0;
     Realm realm;
    RealmResults<Note> result, temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        setSupportActionBar(toolbar);
        notesList=new ArrayList<>();
        t=(Toolbar)findViewById(R.id.tool_bar);
        add = (Button) findViewById(R.id.button);
        sp = (Spinner) findViewById(R.id.spinner);
        rv = (RecyclerView) findViewById(R.id.r1);
        et = (EditText) findViewById(R.id.editText);
        LinearLayoutManager grid = new LinearLayoutManager(this);
        rv.setLayoutManager(grid);
        Realm.init(this);
         realm = Realm.getDefaultInstance();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //final Note note=new Note(et.getText().toString(),sp.getSelectedItem().toString(),"pending");
                final Note note  = new Note();
               // writeNewPost(note);
                note.setKey(UUID.randomUUID().toString());
                note.setSubject(et.getText().toString());
                note.setPriority(sp.getSelectedItem().toString().equals("High")?"c":sp.getSelectedItem().toString().equals("Medium")?"b":"a");
                note.setStatus("pending");
                note.setTime(dt.format(new Date()));
                Log.d("demo",dt.format(new Date()));
                et.setText("");
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealmOrUpdate(note);

                    }
                });

            }
        });
        result  = realm.where(Note.class).findAll();
        adaptor = new List_adaptor(MainActivity.this,result, MainActivity.this);
        rv.setAdapter(adaptor);
        result.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<Note>>() {
            @Override
            public void onChange(RealmResults<Note> collection, OrderedCollectionChangeSet changeSet) {
                adaptor = new List_adaptor(MainActivity.this,result, MainActivity.this);
                rv.setAdapter(adaptor);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
                        {
                            case R.id.all:

                              temp = result.sort("status");

                                adaptor = new List_adaptor(MainActivity.this,temp, MainActivity.this);
                                rv.setAdapter(adaptor);

                                break;

                            case R.id.completed:
                                 temp = result.where().equalTo("status", "completed").findAll();


                                adaptor = new List_adaptor(MainActivity.this,temp, MainActivity.this);
                                rv.setAdapter(adaptor);

                                break;
                            case R.id.Sortbytime:
                               temp = result.sort("time");
                                adaptor = new List_adaptor(MainActivity.this,temp, MainActivity.this);
                                rv.setAdapter(adaptor);

                                break;

                            case R.id.Sortbyprior:
                              temp =  result.sort("priority", Sort.DESCENDING);
                                adaptor = new List_adaptor(MainActivity.this,temp, MainActivity.this);
                                rv.setAdapter(adaptor);

                                break;

                            case R.id.pending:
                               temp = result.where().equalTo("status", "pending").findAll();
                                adaptor = new List_adaptor(MainActivity.this,temp, MainActivity.this);
                                rv.setAdapter(adaptor);

                                break;
                        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void addtocart(final Note note, int position) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Note some = note;
                some.deleteFromRealm();
            }
        });
    }
}
