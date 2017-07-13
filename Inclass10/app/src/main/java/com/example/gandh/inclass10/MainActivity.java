package com.example.gandh.inclass10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements List_adaptor.onclickcart {
    DatabaseReference mDatabase,son;
    GoogleApiClient client;
    Toolbar t;
    ListView lv;
    Spinner sp;
    EditText et;
    Button add;
    RecyclerView rv;
    ArrayList<Note> notesList;
    List_adaptor adaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notesList=new ArrayList<>();
        t=(Toolbar)findViewById(R.id.tool_bar);
        add = (Button) findViewById(R.id.button);
        sp = (Spinner) findViewById(R.id.spinner);
        rv = (RecyclerView) findViewById(R.id.r1);
        et = (EditText) findViewById(R.id.editText);
        LinearLayoutManager grid = new LinearLayoutManager(this);
        rv.setLayoutManager(grid);

        mDatabase = FirebaseDatabase.getInstance().getReference();
         son = mDatabase.child("notes");
       String s=  mDatabase.child("posts").push().getKey();


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note=new Note(et.getText().toString(),sp.getSelectedItem().toString(),"pending");

                writeNewPost(note);
                et.setText("");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void writeNewPost(Note note) {

        String key = son.child("id").push().getKey();
        Map<String, Object> postValues = note.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/notes/" + key, postValues);
        note.setKey(key);
        notesList.add(note);

        mDatabase.updateChildren(childUpdates);
    }



    @Override
    protected void onStart() {
        super.onStart();

        son.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {


                Note note1;
                note1=postSnapshot.getValue(Note.class);
                    Log.d("demo",note1.toString());
                    notesList.add(note1);
//                    public boolean onOptionsItemSelected(MenuItem item) {
//                        switch (item.getItemId())
//                        {
//                            case R.id.all:
//                                Collections.sort(notes, new Comparator<Note>() {
//
//                                    @Override
//                                    public int compare(Note o1, Note o2) {
//                                        if(o1.getStatus().equals("completed"))
//                                            return -1;
//
//                                        else
//                                            return 1;
//
//                                    }
//                                });
//
//                                adaptor = new List_adaptor(MainActivity.this,R.layout.note_item ,notes);
//                                adaptor.setNotifyOnChange(true);
//                                lv.setAdapter(adaptor);
//
//                                break;
//                            case R.id.completed:
//                                notes1 = new ArrayList<>();
//                                for (Note n :
//                                        notes) {
//                                    if(n.getStatus().equals("completed"))
//                                    {
//                                        notes1.add(n);
//                                    }
//                                    else
//                                    {
//
//                                    }
//                                }
//
//
//                                adaptor = new List_adaptor(MainActivity.this,R.layout.note_item ,notes1);
//                                adaptor.setNotifyOnChange(true);
//                                lv.setAdapter(adaptor);
//
//                                break;
//                            case R.id.Sortbytime:
//                                Collections.sort(notes, new Comparator<Note>() {
//
//                                    @Override
//                                    public int compare(Note o1, Note o2) {
//                                        return 0;
//                                    }
//                                });
//
//
//
//                                break;
//
//                            case R.id.Sortbyprior:
//                                Collections.sort(notes, new Comparator<Note>() {
//
//                                    @Override
//                                    public int compare(Note o1, Note o2) {
//
//                                        if(o1.getPriority().equals("Low"))
//                                            return 2;
//
//                                        else if(o1.getPriority().equals("High")) {
//                                            return -1;
//                                        }
//                                        else if (o1.getPriority().equals("Medium")) {
//                                            return 1;
//                                        }
//                                        else
//                                            return 0;
//
//                                    }
//                                });
//
//                                adaptor = new List_adaptor(MainActivity.this,R.layout.note_item ,notes);
//                                adaptor.setNotifyOnChange(true);
//                                lv.setAdapter(adaptor);
//
//                                break;
//
//                            case R.id.pending:
//                                notes1 = new ArrayList<>();
//
//                                for (Note n :
//                                        notes) {
//                                    if(n.getStatus().equals("completed"))
//                                    {
//
//                                    }
//                                    else
//                                    {
//                                        notes1.add(n);
//                                    }
//                                }
//
//                                adaptor = new List_adaptor(MainActivity.this,R.layout.note_item ,notes1);
//                                adaptor.setNotifyOnChange(true);
//                                lv.setAdapter(adaptor);
//
//                                break;
//                        }
//                        return super.onOptionsItemSelected(item);
//                    }
                adaptor = new List_adaptor(MainActivity.this,notesList, MainActivity.this);
                rv.setAdapter(adaptor);
            }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void addtocart(Note note, int position) {
        notesList.set(position, note);
//        adaptor = new List_adaptor(MainActivity.this,notesList, MainActivity.this);
//        rv.setAdapter(adaptor);
        son.child(note.getKey()).updateChildren(note.toMap());
    }
}
