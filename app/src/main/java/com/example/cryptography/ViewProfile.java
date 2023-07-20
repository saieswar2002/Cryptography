package com.example.cryptography;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewProfile extends AppCompatActivity {
    private TextView tuid,tuname,temail,tphn;
    private String user_name,mail,mobile,user_id;
    private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        tuid = findViewById(R.id.v_uid);
        tuname = findViewById(R.id.v_uname);
        temail = findViewById(R.id.v_email);
        tphn = findViewById(R.id.v_phn);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        if(firebaseUser == null){
            Toast.makeText(this, "Something went wrong! Users data is not available at the moment.", Toast.LENGTH_SHORT).show();
        }
        else{

            checkifEmailVerified(firebaseUser);
            showUserProfile(firebaseUser);
        }
    }

    private void checkifEmailVerified(FirebaseUser firebaseUser) {
        if(!firebaseUser.isEmailVerified()){
            showAlertDialogue();
        }

    }

    private void showAlertDialogue() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ViewProfile.this);
        builder.setTitle("Email Not Verified");
        builder.setMessage("Please verify your email now. You can not login without email verification next time.");

        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();

        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if(readUserDetails != null){
                    user_name = firebaseUser.getDisplayName();
                    mail = firebaseUser.getEmail();
                    mobile = readUserDetails.mobile;
                    user_id = firebaseUser.getUid();

                    tuid.setText(user_id);
                    tuname.setText(user_name);
                    temail.setText(mail);
                    tphn.setText(mobile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewProfile.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}