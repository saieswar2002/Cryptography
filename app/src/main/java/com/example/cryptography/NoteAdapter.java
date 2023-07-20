package com.example.cryptography;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note,NoteAdapter.NoteViewHolder> {

    Context context;
    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options, Context context) {
        super(options);
        this.context = context;
    }
    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull Note note) {
        holder.p.setText(note.pt);
        holder.meth.setText(note.me);
        holder.c.setText(note.ct);
    }
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_layout,parent,false);
        return  new NoteViewHolder(view);
    }
    class NoteViewHolder extends RecyclerView.ViewHolder{
         TextView p,c,meth;
         public NoteViewHolder(@NonNull View itemView) {
             super(itemView);
             p = itemView.findViewById(R.id.e_p_text);
             meth = itemView.findViewById(R.id.e_method);
             c = itemView.findViewById(R.id.e_c_text);
         }
     }

}
