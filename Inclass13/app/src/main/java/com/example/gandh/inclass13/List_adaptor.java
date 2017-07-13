package com.example.gandh.inclass13;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ocpsoft.pretty.time.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by gandh on 2/27/2017.
 */

public class List_adaptor extends RecyclerView.Adapter {

    Context context;
    List<Note> notes;
    int resource;
    onclickcart indf;
    PrettyTime pt;


    public List_adaptor(Context context, List<Note> objects, onclickcart indf) {

        this.context = context;
        this.indf = indf;
        this.notes = objects;
        Realm.init(context);
        pt = new PrettyTime();
    }

    interface onclickcart {
        void addtocart(Note note, int position);
    }

    class holder extends RecyclerView.ViewHolder {
        View v;
        TextView title;
        TextView priority;
        TextView time;
        CheckBox b;

        public holder(View v) {
            super(v);
            this.v = v;
            title = (TextView) v.findViewById(R.id.tv1);
            priority = (TextView) v.findViewById(R.id.tv2);
            time = (TextView) v.findViewById(R.id.tv3);
            b = (CheckBox) v.findViewById(R.id.checkBox);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.note_item, parent, false);
        holder h = new holder(v);
        return h;
    }


    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final holder h = (holder) holder;
        final Note note = notes.get(position);
        h.title.setText(notes.get(position).getSubject());
        h.priority.setText(notes.get(position).getPriority().equals("c")?"High":notes.get(position).getPriority().equals("b")?"Medium":"Low");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            h.time.setText(pt.format(sd.parse(notes.get(position).getTime())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final Realm realm = Realm.getDefaultInstance();
        if(note.getStatus().equals("completed"))
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    h.b.setChecked(true);
                }
            });
        h.v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                indf.addtocart(note,position);

                return false;
            }
        });
        h.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (note.getStatus().equals("") || note.getStatus().equals("pending")) {
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
                                    realm.executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
                                            note.setStatus("completed");

                                            h.b.setChecked(true);
                                        }
                                    });


                                }
                            })
                            .show();
                } else {
                    new AlertDialog.Builder(context)
                            .setMessage("Are you sure that you want to mark it to  pending?")
                            .setNeutralButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    h.b.setChecked(true);
                                }
                            })
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    realm.executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
                                            note.setStatus("pending");
                                            h.b.setChecked(false);
                                        }
                                    });

                                }
                            })
                            .show();
                }

            }
        });


    }
}


