package com.example.cryptography;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    EditText uname,email,phn,pswrd,cpswrd;
   // private ProgressBar progressBar;
    private static final String TAG = "Register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        uname = findViewById(R.id.uname);
        email = findViewById(R.id.email);
        phn = findViewById(R.id.phn);
        pswrd = findViewById(R.id.pswrd);
        cpswrd = findViewById(R.id.c_pswrd);
       // ProgressBar progressBar = findViewById(R.id.progressBar);
        Button register_b = (Button) findViewById(R.id.register_button);
        register_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = uname.getText().toString();
                String email_id = email.getText().toString();
                String mobile_no = phn.getText().toString();
                String password = pswrd.getText().toString();
                String c_password = cpswrd.getText().toString();

                String mobileRegex = "[6-9][0-9]{9}";
                Matcher mobileMatcher;
                Pattern mobilePattern = Pattern.compile(mobileRegex);
                mobileMatcher = mobilePattern.matcher(mobile_no);

                
                if(TextUtils.isEmpty(username)){
                    Toast.makeText(Register.this, "Enter Username", Toast.LENGTH_SHORT).show();
                    uname.setError("Username is required");
                    uname.requestFocus();
                } else if (TextUtils.isEmpty(email_id)) {
                    Toast.makeText(Register.this, "Enter E-mail address", Toast.LENGTH_SHORT).show();
                    email.setError("E-mail is required");
                    email.requestFocus();
                }
                else if (TextUtils.isEmpty(mobile_no)) {
                    Toast.makeText(Register.this, "Enter E-mail Mobile Number", Toast.LENGTH_SHORT).show();
                    phn.setError("Mobile number is required");
                    phn.requestFocus();
                }
                else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Register.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    pswrd.setError("Enter password");
                    pswrd.requestFocus();
                }
                else if (TextUtils.isEmpty(c_password)) {
                    Toast.makeText(Register.this, "Confirm the password", Toast.LENGTH_SHORT).show();
                    cpswrd.setError("Confirm your password");
                    cpswrd.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email_id).matches()) {
                    Toast.makeText(Register.this, "Please re-enter your e-mail address", Toast.LENGTH_SHORT).show();
                    email.setError("E-mail is invalid");
                    email.requestFocus();
                } else if (mobile_no.length() != 10) {
                    Toast.makeText(Register.this, "Please re-enter your mobile number", Toast.LENGTH_SHORT).show();
                    phn.setError("Invalid mobile number");
                    phn.requestFocus();
                } else if (!mobileMatcher.find()) {
                    Toast.makeText(Register.this, "Please re-enter your mobile number", Toast.LENGTH_SHORT).show();
                    phn.setError("Invalid mobile number");
                    phn.requestFocus();
                } else if (password.length() < 6) {
                    Toast.makeText(Register.this, "Password should contain atleast 6 characters", Toast.LENGTH_SHORT).show();
                    pswrd.setError("Password is weak");
                    pswrd.requestFocus();
                } else if (!password.equals(c_password)) {
                    Toast.makeText(Register.this, "Confirmation password should be same as password", Toast.LENGTH_SHORT).show();
                    cpswrd.setError("Confirmation password is not matching with the original password");
                    cpswrd.requestFocus();
                    pswrd.clearComposingText();
                    cpswrd.clearComposingText();
                }else{
                 //   progressBar.setVisibility(View.VISIBLE);
                    registerUser(username,email_id,mobile_no,password);
                }
            }

            private void registerUser(String username, String email_id, String mobile_no, String password) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.createUserWithEmailAndPassword(email_id,password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            FirebaseUser firebaseUser = auth.getCurrentUser();

                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
                            firebaseUser.updateProfile(profileChangeRequest);

                            //Enter user data into the firebase realtime db
                            ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(username,email_id,mobile_no);

                            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
                            referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @SuppressLint("NotificationPermission")
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        firebaseUser.sendEmailVerification();
                                        Toast.makeText(Register.this, "User registered successfully. Please verify your email", Toast.LENGTH_SHORT).show();
                                        String message = "Your registration is done.";

                                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
                                        {
                                            NotificationChannel chnnelID=new NotificationChannel("Notification","My notification", NotificationManager.IMPORTANCE_DEFAULT);
                                            NotificationManager manager=getSystemService(NotificationManager.class);
                                            manager.createNotificationChannel(chnnelID);
                                        }
                                        NotificationCompat.Builder builder=new NotificationCompat.Builder(Register.this,"Notification")
                                                .setSmallIcon(R.mipmap.ic_launcher_round)
                                                .setContentTitle("Registration")
                                                .setContentText("Your registration is completed.")
                                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                                .setAutoCancel(true);
                                        Intent i=new Intent(Register.this,MainActivity2.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        i.putExtra("Message",message);
                                        PendingIntent p=PendingIntent.getActivity(Register.this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
                                        builder.setContentIntent(p);
                                        NotificationManager manager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                        manager.notify(1, builder.build());
                                        /*
                                        Intent intent = new Intent(Register.this,UserProfileActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();

                                         */

                                    }
                                    else{
                                        Toast.makeText(Register.this, "User registeration failed.", Toast.LENGTH_SHORT).show();

                                    }
                                   // progressBar.setVisibility(View.GONE);
                                }
                            });

                        }
                        else{
                            try{
                                throw task.getException();
                            }catch (FirebaseAuthWeakPasswordException e){
                                pswrd.setError("Your password is too weak. Kindly use a mix of alphabets, numbers and special characters.");
                                pswrd.requestFocus();
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                email.setError("Your email is invalid or already in use. Kindly re-enter.");
                                email.requestFocus();
                            }catch (FirebaseAuthUserCollisionException e){
                                pswrd.setError("User is alreaddy registered with this email. Use another email.");;
                                pswrd.requestFocus();
                            }catch (Exception e){
                                Log.e(TAG,e.getMessage());
                                Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                         //   progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }
}