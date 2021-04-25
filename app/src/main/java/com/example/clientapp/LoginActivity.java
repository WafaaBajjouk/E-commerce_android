package com.example.clientapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    TextView creatacc;
    TextView logemail;
    TextView logpass;
    Button logsend;
    TextView forgetpass;
    FirebaseAuth firebaseAuth;
    AlertDialog.Builder reset_alert;
    LayoutInflater inflater;
    private static final String TAG = "MainActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        creatacc = findViewById(R.id.createacc);
        forgetpass= findViewById(R.id.forgetpass);
        firebaseAuth= FirebaseAuth.getInstance();
         reset_alert = new AlertDialog.Builder(this);
         inflater = this.getLayoutInflater();







        creatacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });

        logemail= findViewById(R.id.logemail);
        logpass = findViewById(R.id.logpass);
        logsend = findViewById(R.id.logsend);

        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = inflater.inflate(R.layout.resetpop, null);

                reset_alert.setTitle("Reset Forget Password?")
                        .setMessage("Enter Your Email to get Password Reset link. ")
                        .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // validation  de email
                                EditText resetemail = view.findViewById(R.id.resetemail);
                                if(resetemail.getText().toString().isEmpty()){
                                    resetemail.setError("Required Field");
                                    return;

                                }
                                 firebaseAuth.sendPasswordResetEmail(resetemail.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                     @Override
                                     public void onSuccess(Void aVoid) {
                                        Toast.makeText(LoginActivity.this,"Reset Email Sent" , Toast.LENGTH_SHORT).show();



                                     }
                                 }).addOnFailureListener(new OnFailureListener() {
                                     @Override
                                     public void onFailure(@NonNull Exception e) {
                                         Toast.makeText(LoginActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();


                                     }
                                 });



                            }
                        }).setNegativeButton("Cancel", null)
                        .setView(view)
                        .create().show();
            }
        });


        logsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(logemail.getText().toString().isEmpty()){
                    logemail.setError("Email is missing");
                    return;
                }

                if(logpass.getText().toString().isEmpty()){
                    logpass.setError("Password is missing");
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(logemail.getText().toString().trim(),logpass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
//                        login is succeful
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                    finish();
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

    }

//    public void updateUI(FirebaseUser currentUser) {
//        Intent profileIntent = new Intent(getApplicationContext(), Account.class);
//        profileIntent.putExtra("email", currentUser.getEmail());
//        Log.v("DATA", currentUser.getUid());
//        startActivity(profileIntent);
//    }

}