package com.example.cryptography;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NoteAdapter1 extends FirestoreRecyclerAdapter<Note1, NoteAdapter1.NoteViewHolder1> {

    Context context;
    public NoteAdapter1(@NonNull FirestoreRecyclerOptions<Note1> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder1 holder, int position, @NonNull Note1 note) {
        holder.p1.setText(note.ct1);
        holder.meth1.setText(note.me1);
        holder.c1.setText(note.pt1);
    }

    @NonNull
    @Override
    public NoteViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history1_layout,parent,false);
        return  new NoteViewHolder1(view);
    }

    class NoteViewHolder1 extends RecyclerView.ViewHolder{

        TextView p1,c1,meth1;

        public NoteViewHolder1(@NonNull View itemView) {
            super(itemView);
            p1 = itemView.findViewById(R.id.e_p_text1);
            meth1 = itemView.findViewById(R.id.e_method1);
            c1 = itemView.findViewById(R.id.e_c_text1);
        }
    }
}
