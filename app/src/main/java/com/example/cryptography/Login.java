package com.example.cryptography;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText eLoginEmail,eLoginPwd;
    TextView forgot_pwd;
    private FirebaseAuth authProfile;
    private static  final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        eLoginEmail = findViewById(R.id.l_email);
        eLoginPwd = findViewById(R.id.l_pswrd);
        forgot_pwd = findViewById(R.id.f_pwd);

        forgot_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this, "You can reset your password now!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this,ForgotPasswordActivity.class));
            }
        });

        ImageView imageViewShowHidePwd = findViewById(R.id.img);
        imageViewShowHidePwd.setImageResource(R.drawable.baseline_visibility_off_24);
        imageViewShowHidePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eLoginPwd.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    eLoginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageViewShowHidePwd.setImageResource(R.drawable.baseline_visibility_off_24);
                }
                else{
                    eLoginPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowHidePwd.setImageResource(R.drawable.baseline_visibility_24);
                }
            }
        });


        authProfile = FirebaseAuth.getInstance();

        Button bLogin = findViewById(R.id.login_button);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEmail = eLoginEmail.getText().toString();
                String textPwd = eLoginPwd.getText().toString();

                if(TextUtils.isEmpty(textEmail)){
                    Toast.makeText(Login.this, "please enter your email", Toast.LENGTH_SHORT).show();
                    eLoginEmail.setError("Email is required");
                    eLoginEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(Login.this, "please re-enter your email", Toast.LENGTH_SHORT).show();
                    eLoginEmail.setError("Valid e-mail is required");
                    eLoginEmail.requestFocus();
                } else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(Login.this, "please enter your password", Toast.LENGTH_SHORT).show();
                    eLoginPwd.setError("Password is required");
                    eLoginPwd.requestFocus();
                }else {
                    loginuser(textEmail,textPwd);
                }
            }
        });


        TextView reg = (TextView) findViewById(R.id.reg);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i1 = new Intent(Login.this,Register.class);
                startActivity(i1);
            }
        });
    }

    private void loginuser(String email, String pwd) {
        authProfile.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(Login.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = authProfile.getCurrentUser();

                    if(firebaseUser.isEmailVerified()){
                        Toast.makeText(Login.this, "You are logged in now", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(Login.this,MainActivity.class));
                        finish();
                    }
                    else{
                        firebaseUser.sendEmailVerification();
                        authProfile.signOut();
                        showAlertDialog();
                    }



                }else {
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        eLoginEmail.setError("User doesn't exist. Please register.");
                        eLoginEmail.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        eLoginEmail.setError("Invalid credentials. Kindly, check and re-enter");
                        eLoginEmail.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setTitle("Email Not Verified");
        builder.setMessage("Please verify your email now. You can not login without email verification");

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

    @Override
    protected void onStart(){
        super.onStart();
        if(authProfile.getCurrentUser() != null){
            Toast.makeText(this, "Already Logged In!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Login.this,ViewProfile.class));
            finish();
        }
        else{
            Toast.makeText(this, "You can login now!", Toast.LENGTH_SHORT).show();
        }
    }

}