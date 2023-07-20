package com.example.cryptography;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class History1 extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    NoteAdapter1 noteAdapter1;
    RecyclerView mrecyclerview1;

    StaggeredGridLayoutManager staggeredGridLayoutManager;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history1);

        mrecyclerview1 = findViewById(R.id.recyclerview1);
        setupRecyclerView1();
    }

    private void setupRecyclerView1() {

        Query query = Utility1.getCollectionReferenceForNotes().orderBy("me1",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Note1> options = new FirestoreRecyclerOptions.Builder<Note1>().setQuery(query,Note1.class).build();
        mrecyclerview1.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter1 = new NoteAdapter1(options,this);
        mrecyclerview1.setAdapter(noteAdapter1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        noteAdapter1.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        noteAdapter1.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        noteAdapter1.notifyDataSetChanged();
    }
}