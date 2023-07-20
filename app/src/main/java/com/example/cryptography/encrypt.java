package com.example.cryptography;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import java.util.Random;

public class encrypt extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    List<String> b;
    FirebaseAuth authProfile;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    private final String CHANNEL_ID = "personal_notifications";
    private final int NOTIFICATION_ID = 001;
    int count = 1;
    String key = "";
    Random random = new Random();
    Spinner spinner;
    String ciphertext;
    String s;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);
        Button b1,b2,b3,b4;
        TextView t1;
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

                String method = s;
                String msg=e1.getText().toString();
                if(method != "Select a method"){
                    if(method == "Caesar Cipher"){
                        int key=3;
                        count = new Random().nextInt(5 - 1) + 1;
                        if(count==1){
                            key = 3;
                        }
                        else if (count ==2) {
                            key = 4;
                        }
                        else if(count == 3){
                            key = 5;
                        }
                        else if(count == 4){
                            key = 6;
                        }
                        else if(count == 5){
                            key = 7;
                        }

                        String ALPHABET ="abcdefghijklmnopqrstuvwxyz";
                        msg = msg.toLowerCase();

                        String encryptStr = "";

                        for (int i = 0; i < msg.length(); i++)
                        {
                            int encryptPos;
                            char encryptChar;
                            String Char = ""+msg.charAt(i);
                            if(ALPHABET.contains(Char) ){
                                int pos = ALPHABET.indexOf(msg.charAt(i));

                                encryptPos = (key  + pos) % 26;
                                encryptChar = ALPHABET.charAt(encryptPos);
                                encryptStr += encryptChar;
                            }
                            else{
                                encryptStr+=msg.charAt(i);
                            }

                        }
                        encryptStr=encryptStr+count;
                        ProgressDialog pd;
                        pd = new ProgressDialog(encrypt.this);
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
                        t1.setText(encryptStr);

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
                        
                         String s = msg;
                         String encryptedString = "";
                        for (int i = 0; i < s.length(); i++) {
                            for (int j = 0; j < 26; j++) {


                                if (s.charAt(i) == normalChar[j])
                                {
                                    encryptedString += codedChar[j];
                                    break;
                                }

                                if (s.charAt(i) < 'a' || s.charAt(i) > 'z')
                                {
                                    encryptedString += s.charAt(i);
                                    break;
                                }
                            }
                        }


                        ProgressDialog pd;
                        pd = new ProgressDialog(encrypt.this);
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

                        t1.setText(encryptedString);
                    } else if (method == "Playfair Cipher") {

                        String keyword = "monarchy";
                        count = new Random().nextInt(5 - 1) + 1;
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
                        String key_input = msg;
                        if(key_input.length()%2!=0){
                            key_input=key_input+"x";
                        }
                        String e = x.encryptMessage(key_input);


                        ProgressDialog pd;
                        pd = new ProgressDialog(encrypt.this);
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

                        e=e+count;

                        t1.setText(e);

                    }

                    else if (method == "Hill Cipher") {

                        String message = msg;
                        String e = encrypt(message);


                        ProgressDialog pd;
                        pd = new ProgressDialog(encrypt.this);
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

                        t1.setText(e);

                    }

                    else if (method == "One Time Padding") {
                        String text = msg.toUpperCase();
                        key = RandomAlpha(text.length());
                        String enc = OTPEncryption(text,key);

                        ProgressDialog pd;
                        pd = new ProgressDialog(encrypt.this);
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

                        t1.setText(enc);

                    }
                    if(count < 5){
                        count++;
                    }
                    else{
                        count=1;
                    }
                    
                    ciphertext = t1.getText().toString();
                    Note note = new Note();
                    note.setPt(msg);
                    note.setMe(s);
                    note.setCt(ciphertext);

                    saveNoteToFirebase(note);

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
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(encrypt.this,CHANNEL_ID);
                    builder.setSmallIcon(R.mipmap.ic_launcher_round);
                    builder.setContentTitle("Encryption");
                    builder.setContentText("Your encryption is done.");
                    builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    builder.setStyle(new NotificationCompat.BigTextStyle().bigText("Plain Text : "+msg+"\n"+"Method : "+s+"\n"+"Cipher Text : "+ciphertext));

                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(encrypt.this);
                   // notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
                    NotificationManager manager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.notify(1, builder.build());


                    

                }
                else{
                    Toast.makeText(encrypt.this, "Select a method", Toast.LENGTH_SHORT).show();
                }
                //t1.setText(msg);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(encrypt.this);
                builder.setTitle("Reset")
                        .setMessage("Are you sure???")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                e1.getText().clear();
                                t1.setText("This is encrypted message");
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

        Button intent = (Button) findViewById(R.id.intent);
        intent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText num = (EditText) findViewById(R.id.num);
                String number = num.getText().toString();
                Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("sms:"+number));
                i.putExtra("sms_body",ciphertext);
//                Intent i = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms",number,null));
//                i.putExtra("body","hi"+ciphertext);
                startActivity(i);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Text",t1.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(encrypt.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    static String encrypt(String message) {
        message = message.toUpperCase();
        // Get key matrix
        int matrixSize = 2;
        int[][] keyMatrix = {{1,2},{1,2}};
        int count = new Random().nextInt(5 - 1) + 1;
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
        validateDeterminant(keyMatrix, matrixSize);

        int[][] messageVector = new int[matrixSize][1];
        String CipherText = "";
        int[][] cipherMatrix = new int[matrixSize][1];
        int j = 0;
        while (j < message.length()) {
            for (int i = 0; i < matrixSize; i++) {
                if (j >= message.length()) {
                    messageVector[i][0] = 23;
                } else {
                    messageVector[i][0] = (message.charAt(j)) % 65;
                }
                System.out.println(messageVector[i][0]);
                j++;
            }
            int x, i;
            for (i = 0; i < matrixSize; i++) {
                cipherMatrix[i][0] = 0;

                for (x = 0; x < matrixSize; x++) {
                    cipherMatrix[i][0] += keyMatrix[i][x] * messageVector[x][0];
                }
                System.out.println(cipherMatrix[i][0]);
                cipherMatrix[i][0] = cipherMatrix[i][0] % 26;
            }
            for (i = 0; i < matrixSize; i++) {
                CipherText += (char) (cipherMatrix[i][0] + 65);
            }
        }
        return CipherText+count;
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
    static void validateDeterminant(int[][] keyMatrix, int n) {
        if (determinant(keyMatrix, n) % 26 == 0) {
            System.out.println(
                    "Invalid key, as determinant = 0. Program Terminated"
            );
        }
    }
    private void saveNoteToFirebase(Note note) {

        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForNotes().document();
        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(encrypt.this, "History saved", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(encrypt.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showPopup(View v){
        PopupMenu popup = new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_options);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        ProgressDialog pd = new ProgressDialog(encrypt.this);

        Intent t1 = new Intent(encrypt.this,Techniques.class);
        switch (item.getItemId()){
            case R.id.item2:
                Intent i1 = new Intent(encrypt.this,MainActivity.class);
                startActivity(i1);
                return true;
            case R.id.item3:
                Intent i2 = new Intent(encrypt.this,decrypt.class);
                startActivity(i2);
                return true;
            case R.id.tech1 :
                t1.putExtra("tech_name",item.getTitle().toString());
                startActivity(t1);
                return true;
            case R.id.tech2:
                Intent t2 = new Intent(encrypt.this,Monoaplhabetic.class);
                t2.putExtra("tech_name",item.getTitle().toString());
                startActivity(t2);
                return true;
            case R.id.tech3:
                Intent i = new Intent(encrypt.this,Playfair.class);
                i.putExtra("tech_name",item.getTitle().toString());
                startActivity(i);
                return true;
            case R.id.tech4:
                Intent intent2 = new Intent(encrypt.this,Hill.class);
                intent2.putExtra("tech_name",item.getTitle().toString());
                startActivity(intent2);
                return true;
            case R.id.tech5:
                Intent intent3 = new Intent(encrypt.this,OneTimePadding.class);
                intent3.putExtra("tech_name",item.getTitle().toString());
                startActivity(intent3);
                return true;
            case R.id.item4:
                Intent i3 = new Intent(encrypt.this,History.class);
                startActivity(i3);

                return true;
            case R.id.item5:
                Intent i4 = new Intent(encrypt.this,About.class);
                startActivity(i4);
                return true;
            case R.id.item6:
                Intent i5 = new Intent(encrypt.this, ViewProfile.class);
                startActivity(i5);
                return  true;
            case R.id.item7:
                authProfile.getInstance().signOut();
                Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(encrypt.this,MainActivity.class);

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

    public static String OTPEncryption(String text,String key){
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

                int total = (index + keydex) % 26;

                sb = sb+ alphaU.charAt(total);
            }
            else if(Character.isLowerCase(get)){
                int index = alphaL.indexOf(get);
                int keydex = alphaU.indexOf(Character.toLowerCase(keyget));

                int total = (index + keydex) % 26;

                sb = sb+ alphaL.charAt(total);
            }
            else{
                sb = sb + get;
            }
        }

        return sb;
    }
}