package com.example.clientapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clientapp.Classes.Client;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText email;
    EditText pass;
    EditText confi;
    EditText fullname;
    EditText address;
    EditText phone,cin;

    Button send;
    TextView gotologin;
    FirebaseAuth fAuth;
    FirebaseDatabase firebaseDatabase;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        email = findViewById(R.id.email);
        phone= findViewById(R.id.phone);
        cin=findViewById(R.id.cin);

        pass= findViewById(R.id.pass);
        confi = findViewById(R.id.confi);
        send = findViewById(R.id.send);
        gotologin = findViewById(R.id.gotologin);
        fAuth = FirebaseAuth.getInstance();
        fullname= findViewById(R.id.fullname);
        address= findViewById(R.id.address);



        gotologin.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
                                     });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtemail = email.getText().toString();
                String txtpass= pass.getText().toString();
                String txtconfi= confi.getText().toString();
                String txtname = fullname.getText().toString();
                String txtadd= address.getText().toString();
                String txtphone= phone.getText().toString();
                String txtcin=cin.getText().toString();

                // validation des donnees


                if(txtemail.isEmpty()){
                    email.setError("Full name is required!");
                    return;
                }
                if(txtphone.isEmpty()){
                    phone.setError("Full name is required!");
                    return;
                }

                if(txtcin.isEmpty()){
                    cin.setError("Full name is required!");
                    return;
                }



                if(txtpass.isEmpty()){
                    pass.setError("Full name is required!");
                    return;
                }
                if(txtconfi.isEmpty()){
                    confi.setError("Full name is required!");
                    return;
                }

                if(!txtpass.equals(txtconfi)){
                    pass.setError("Password don't match");
                    return;
                }
                //validation  effectue

                Toast.makeText(RegisterActivity.this, "Data validated" ,Toast.LENGTH_SHORT ).show();


                fAuth.createUserWithEmailAndPassword(txtemail,txtpass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Client client = new Client(txtname,txtphone,txtemail,txtadd,txtcin);


                                    FirebaseDatabase.getInstance().getReference("customers")
                                            .child(FirebaseAuth.getInstance().getUid())
                                            .setValue(client).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(RegisterActivity.this,"User has been registred successfully",Toast.LENGTH_LONG).show();

                                            }else{
                                                Toast.makeText(RegisterActivity.this,"Failed To register ok Try Again! ", Toast.LENGTH_LONG).show();

                                            }
                                        }
                                    });



                                }else{

                                    Toast.makeText(RegisterActivity.this,"Failed To register Try Again! ", Toast.LENGTH_LONG).show();

                                }
                            }
                        });


            }
        });}



}