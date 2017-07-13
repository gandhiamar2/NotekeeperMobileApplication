package com.example.gandh.inclass07;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gandh on 2/27/2017.
 */

public class List_adaptor extends ArrayAdapter<Note> {

    Context context;
    List<Note> notes;
    int resource;
    Note_Manager manager;

    Note_Dao dao;

    public List_adaptor(Context context, int resource, List<Note> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.notes= objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.note_item,parent,false);

        }

        TextView subject = (TextView) convertView.findViewById(R.id.tv1);
        TextView priority = (TextView) convertView.findViewById(R.id.tv2);
        TextView time = (TextView) convertView.findViewById(R.id.tv3);
       final CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox);
       final Note note = notes.get(position);
        subject.setText(note.subject);
        priority.setText(note.priority);
        manager = new Note_Manager(context);
        dao = manager.getNotedad();
        //time.setText(note.subject);
        if(note.status.equals("completed"))
        {
            cb.setChecked(true);
        }
        else
            cb.setChecked(false);

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (note.getStatus().equals("")||note.getStatus().equals("pending")) {
                    new AlertDialog.Builder(context)
                            .setMessage("Are you sure that you want to mark it as completed?")
                            .setNeutralButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    note.setStatus("completed");
                                   cb.setChecked(true);
                                    dao.update(note);

                                }
                            })
                            .show();
                } else {
                    new AlertDialog.Builder(context)
                            .setMessage("Are you sure that you want to mark it to  pending?")
                            .setNeutralButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    cb.setChecked(true);
                                }
                            })
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    note.setStatus("pending");
                                   cb.setChecked(false);
                                    dao.update(note);
                                }
                            })
                            .show();
                }
            }
        });
        return convertView;
    }

}
