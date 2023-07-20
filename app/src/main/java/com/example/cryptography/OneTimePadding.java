package com.example.cryptography;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

public class OneTimePadding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent t2 = getIntent();
        String tech_name = t2.getStringExtra("tech_name");
        //setContentView(R.layout.activity_techniques);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        WindowManager w = getWindowManager();
        Display d = w.getDefaultDisplay();

        if(d.getWidth() < d.getHeight())
        {
            Fragment f1 = new OneTimePadding_F1();
            Bundle data = new Bundle();
            data.putString("tech_name",tech_name);
            f1.setArguments(data);
            ft.replace(android.R.id.content,f1);
        }
        else
        {
            Fragment f2 = new OneTimePadding_F2();
            Bundle data = new Bundle();
            data.putString("tech_name",tech_name);
            f2.setArguments(data);
            ft.replace(android.R.id.content,f2);
        }
        ft.commit();
    }
}