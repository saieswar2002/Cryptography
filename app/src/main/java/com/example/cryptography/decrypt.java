package com.example.cryptography;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Random;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;

public class decrypt extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    List<String> b;
    private FirebaseAuth authProfile;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    private final String CHANNEL_ID = "personal_notifications";
    private final int NOTIFICATION_ID = 001;
    String plaintext;
    int count = 1;
    Spinner spinner;
    TextView t1;
    String s;
    ArrayAdapter adapter;
    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt);
        Button b1,b2,b3;

        EditText e1;
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        e1 = (EditText) findViewById(R.id.e1);
        t1 = (TextView) findViewById(R.id.t2);
        spinner = findViewById(R.id.spinner);
        b = Arrays.asList("Select a method","Caesar Cipher","Monoalphabetic Cipher","Playfair Cipher","Hill Cipher","One Time Padding");
        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(this,R.layout.spinner_background,b){
            @Override
            public boolean isEnabled(int position){
                return position != 0;
            }
        };
        mArrayAdapter.setDropDownViewResource(R.layout.spinner_background);
        spinner.setAdapter(mArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s = b.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = rand.nextInt(3);
                String msg = e1.getText().toString();
                String method = s;
                if(method != "Select a method"){
                    if(method == "Caesar Cipher"){
                        int key=3;

                        String ALPHABET ="abcdefghijklmnopqrstuvwxyz";
                        msg = msg.toLowerCase();
                        char k1 = msg.charAt(msg.length()-1);
                        int k = k1-'0';
                        if(k==1){
                            key = 3;
                        } else if (k == 2) {
                            key = 4;
                        } else if (k == 3) {
                            key = 5;
                        } else if (k == 4) {
                            key = 6;
                        } else if (k == 5) {
                            key = 7;
                        }

                        msg = msg.substring(0,msg.length()-1);
                        String decryptStr = "";

                        for (int i = 0; i < msg.length(); i++)
                        {
                            int decryptPos;
                            char decryptChar;
                            String Char = ""+msg.charAt(i);
                            if(ALPHABET.contains(Char) ){
                                int pos = ALPHABET.indexOf(msg.charAt(i));

                                decryptPos = (pos - key) % 26;
                                decryptChar = ALPHABET.charAt(decryptPos);
                                decryptStr += decryptChar;
                            }
                            else{
                                decryptStr+=msg.charAt(i);
                            }

                        }

                        ProgressDialog pd;
                        pd = new ProgressDialog(decrypt.this);
                        pd.setMessage("Encrypting...");
                        pd.setTitle("ProgressDialog");
                        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        pd.show();
                        pd.setCancelable(false);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(3000);
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                                pd.dismiss();
                            }
                        }).start();
                        t1.setText(decryptStr);

                    }
                    else if (method == "Monoalphabetic Cipher") {
                         char normalChar[]
                                = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                                'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                                's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

                         char codedChar[]
                                = { 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O',
                                'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K',
                                'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M' };
                        String s = msg.toUpperCase();
                        String decryptedString="";
                        for (int i = 0; i < s.length(); i++)
                        {
                            for (int j = 0; j < 26; j++) {


                                if (s.charAt(i) == codedChar[j])
                                {
                                    decryptedString += normalChar[j];
                                    break;
                                }


                                if (s.charAt(i) < 'A' || s.charAt(i) > 'Z')
                                {
                                    decryptedString += s.charAt(i);
                                    break;
                                }
                            }
                        }

                        ProgressDialog pd;
                        pd = new ProgressDialog(decrypt.this);
                        pd.setMessage("Encrypting...");
                        pd.setTitle("Wait few seconds");
                        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        pd.show();
                        pd.setCancelable(false);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(3000);
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                                pd.dismiss();
                            }
                        }).start();
                        t1.setText(decryptedString);
                    }

                    else if (method == "Playfair Cipher") {

                        String keyword = "monarchy";
                        char k1 = msg.charAt(msg.length()-1);
                        int count = k1-'0';

                        if(count==1){
                            keyword = "monarchy";
                        }
                        else if (count ==2) {
                            keyword = "vignan";
                        }
                        else if(count == 3){
                            keyword = "university";
                        }
                        else if(count == 4){
                            keyword = "eswar";
                        }
                        else if(count == 5){
                            keyword = "mobile";
                        }
                        PlayfairCipherDecryption x = new PlayfairCipherDecryption();
                        x.setKey(keyword);
                        x.KeyGen();
                        msg = msg.substring(0,msg.length()-1);
                        String key_input = msg;
                        if(key_input.length()%2!=0){
                            key_input=key_input+"x";
                        }
                        String d = x.decryptMessage(key_input);
                        int n = d.length();
                        String d1="";
                        for(int i=0;i<n;i++){
                            if(d.charAt(i)!='x'){
                                d1+=d.charAt(i);
                            }
                        }


                        ProgressDialog pd;
                        pd = new ProgressDialog(decrypt.this);
                        pd.setMessage("Encrypting...");
                        pd.setTitle("Wait few seconds");
                        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        pd.show();
                        pd.setCancelable(false);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(3000);
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                                pd.dismiss();
                            }
                        }).start();
                        t1.setText(d1);
                    }

                    else if (method == "Hill Cipher") {

                        String message = msg;
                        String d = decrypt(message);

                        ProgressDialog pd;
                        pd = new ProgressDialog(decrypt.this);
                        pd.setMessage("Encrypting...");
                        pd.setTitle("Wait few seconds");
                        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        pd.show();
                        pd.setCancelable(false);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(3000);
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                                pd.dismiss();
                            }
                        }).start();
                        t1.setText(d);

                    }


                    else if (method == "One Time Padding") {
                        String enc = msg;
                        String key = RandomAlpha(enc.length());
                        String dec = OTPDecryption(enc,key);



                        ProgressDialog pd;
                        pd = new ProgressDialog(decrypt.this);
                        pd.setMessage("Encrypting...");
                        pd.setTitle("Wait few seconds3");
                        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        pd.show();
                        pd.setCancelable(false);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(3000);
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                                pd.dismiss();
                            }
                        }).start();
                        t1.setText(dec);
                    }

                    plaintext = t1.getText().toString();
                    Note1 note1 = new Note1();
                    note1.setPt1(msg);
                    note1.setMe1(s);
                    note1.setCt1(plaintext);

                    saveNoteToFirebase1(note1);

                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
                    {
                        CharSequence name = "My Notification";
                        String description = "My notification description";
                        //importance of your notification
                        int importance = NotificationManager.IMPORTANCE_DEFAULT;
                        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                        notificationChannel.setDescription(description);
                        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                        notificationManager.createNotificationChannel(notificationChannel);
                    }
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(decrypt.this,CHANNEL_ID);
                    builder.setSmallIcon(R.mipmap.ic_launcher_round);
                    builder.setContentTitle("Decryption");
                    builder.setContentText("Your decryption is done.");
                    builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    builder.setStyle(new NotificationCompat.BigTextStyle().bigText("Cipher Text : "+msg+"\n"+"Method : "+s+"\n"+"Plain Text : "+plaintext));

                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(decrypt.this);
                    // notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
                    NotificationManager manager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.notify(1, builder.build());


                }

                else{
                    Toast.makeText(decrypt.this, "Select a method", Toast.LENGTH_SHORT).show();
                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(decrypt.this);
                builder.setTitle("Reset")
                        .setMessage("Are you sure???")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                e1.getText().clear();
                                t1.setText("This is decrypted message");
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(decrypt.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    static String decrypt(String message) {
        message = message.toUpperCase();
        char k1 = message.charAt(message.length()-1);
        int count = k1-'0';
        message = message.substring(0,message.length()-1);
        // Get key matrix
        int n = 2;
        int[][] keyMatrix = {{1,2},{3,4}};
        if(count==1){
            keyMatrix[0][0]=1 ;
            keyMatrix[0][1]= 2;
            keyMatrix[1][0]= 3;
            keyMatrix[1][1]= 4;
        }
        else if (count ==2) {
            keyMatrix[0][0]=5 ;
            keyMatrix[0][1]= 6;
            keyMatrix[1][0]= 7;
            keyMatrix[1][1]= 8;
        }
        else if(count == 3){
            keyMatrix[0][0]= 5;
            keyMatrix[0][1]=5 ;
            keyMatrix[1][0]=6 ;
            keyMatrix[1][1]= 7;
        }
        else if(count == 4){
            keyMatrix[0][0]=11 ;
            keyMatrix[0][1]= 12;
            keyMatrix[1][0]= 12;
            keyMatrix[1][1]=14 ;
        }
        else if(count == 5){
            keyMatrix[0][0]=12 ;
            keyMatrix[0][1]= 9;
            keyMatrix[1][0]= 1;
            keyMatrix[1][1]= 6;
        }

        //check if det = 0
        validateDeterminant(keyMatrix, n);

        //solving for the required plaintext message
        int[][] messageVector = new int[n][1];
        String PlainText = "";
        int[][] plainMatrix = new int[n][1];
        int j = 0;
        while (j < message.length()) {
            for (int i = 0; i < n; i++) {
                if (j >= message.length()) {
                    messageVector[i][0] = 23;
                } else {
                    messageVector[i][0] = (message.charAt(j)) % 65;
                }
                System.out.println(messageVector[i][0]);
                j++;
            }
            int x, i;
            for (i = 0; i < n; i++) {
                plainMatrix[i][0] = 0;

                for (x = 0; x < n; x++) {
                    plainMatrix[i][0] += keyMatrix[i][x] * messageVector[x][0];
                }

                plainMatrix[i][0] = plainMatrix[i][0] % 26;
            }
            for (i = 0; i < n; i++) {
                PlainText += (char) (plainMatrix[i][0] + 65);
            }
        }
        return PlainText;

    }


    static void validateDeterminant(int[][] keyMatrix, int n) {
        if (determinant(keyMatrix, n) % 26 == 0) {
            System.out.println(
                    "Invalid key, as determinant = 0. Program Terminated"
            );
        }
    }

    public static int determinant(int[][] a, int n) {
        int det = 0, sign = 1, p = 0, q = 0;

        if (n == 1) {
            det = a[0][0];
        } else {
            int[][] b = new int[n - 1][n - 1];
            for (int x = 0; x < n; x++) {
                p = 0;
                q = 0;
                for (int i = 1; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (j != x) {
                            b[p][q++] = a[i][j];
                            if (q % (n - 1) == 0) {
                                p++;
                                q = 0;
                            }
                        }
                    }
                }
                det = det + a[0][x] * determinant(b, n - 1) * sign;
                sign = -sign;
            }
        }
        return det;
    }

    private void saveNoteToFirebase1(Note1 note1) {

        DocumentReference documentReference1;
        documentReference1 = Utility1.getCollectionReferenceForNotes().document();
        documentReference1.set(note1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(decrypt.this, "History saved", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(decrypt.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showPopup(View v){
        PopupMenu popup = new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_options2);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        ProgressDialog pd = new ProgressDialog(decrypt.this);

        Intent t1 = new Intent(decrypt.this,Techniques.class);
        switch (item.getItemId()) {
            case R.id.item2:
                Intent i1 = new Intent(decrypt.this, MainActivity.class);
                startActivity(i1);
                return true;
            case R.id.item3:
                Intent i2 = new Intent(decrypt.this, encrypt.class);
                startActivity(i2);
                return true;
            case R.id.tech1:
                t1.putExtra("tech_name", item.getTitle().toString());
                startActivity(t1);
                return true;
            case R.id.tech2:
                Intent t2 = new Intent(decrypt.this, Monoaplhabetic.class);
                t2.putExtra("tech_name", item.getTitle().toString());
                startActivity(t2);
                return true;
            case R.id.tech3:
                Intent i = new Intent(decrypt.this,Playfair.class);
                i.putExtra("tech_name",item.getTitle().toString());
                startActivity(i);
                return true;
            case R.id.tech4:
                Intent intent2 = new Intent(decrypt.this,Hill.class);
                intent2.putExtra("tech_name",item.getTitle().toString());
                startActivity(intent2);
                return true;
            case R.id.tech5:
                Intent intent3  = new Intent(decrypt.this,OneTimePadding.class);
                intent3.putExtra("tech_name",item.getTitle().toString());
                startActivity(intent3);
                return true;
            case R.id.item4:
                Intent i3 = new Intent(decrypt.this, History1.class);
                startActivity(i3);

                return true;
            case R.id.item5:
                Intent i4 = new Intent(decrypt.this, About.class);
                startActivity(i4);
                return true;

            case R.id.item6:
                Intent i5 = new Intent(decrypt.this, ViewProfile.class);
                startActivity(i5);
                return true;
            case R.id.item7:
                authProfile.getInstance().signOut();
                Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(decrypt.this,MainActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            default:
                return false;
        }
    }


    public static String RandomAlpha(int len){
        Random r = new Random();
        String key = "";
        for(int x=0;x<len;x++)
            key = key + (char) (r.nextInt(26) + 'A');
        return key;
    }


    public static String OTPDecryption(String text,String key){
        String alphaU = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String alphaL = "abcdefghijklmnopqrstuvwxyz";

        int len = text.length();

        String sb = "";
        for(int x=0;x<len;x++){
            char get = text.charAt(x);
            char keyget = key.charAt(x);
            if(Character.isUpperCase(get)){
                int index = alphaU.indexOf(get);
                int keydex = alphaU.indexOf(Character.toUpperCase(keyget));

                int total = (index - keydex) % 26;
                total = (total<0)? total + 26 : total;

                sb = sb+ alphaU.charAt(total);
            }
            else if(Character.isLowerCase(get)){
                int index = alphaL.indexOf(get);
                int keydex = alphaU.indexOf(Character.toLowerCase(keyget));

                int total = (index - keydex) % 26;
                total = (total<0)? total + 26 : total;

                sb = sb+ alphaL.charAt(total);
            }
            else{
                sb = sb + get;
            }
        }

        return sb;
    }
}