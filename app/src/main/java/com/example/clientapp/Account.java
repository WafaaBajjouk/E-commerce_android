package com.example.clientapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clientapp.Classes.Client;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Account extends AppCompatActivity {

    private Button logout;
    private ImageView imageView;

    private DatabaseReference reference;
    private FirebaseUser user;

    private TextView verify;
    private Button verifyemail;
    private FirebaseAuth auth;

    private String userid;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomBar);
        bottomNavigationView.setSelectedItemId(R.id.account);

        imageView=findViewById(R.id.imageView);

        logout = findViewById(R.id.logout);


        auth = FirebaseAuth.getInstance();


        verifyemail = findViewById(R.id.verifyemail);
        verify = findViewById(R.id.verify);





        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                finish();
            }
        });



//
//
//
        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("customers");
        userid= user.getUid();

        TextView fullnameTextView=findViewById(R.id.nameV);
        TextView emailTextView=findViewById(R.id.emailV);
        TextView addressTextView=findViewById(R.id.addV);
        TextView phoneTextView=findViewById(R.id.phoneV);
        TextView cinTextView=findViewById(R.id.cinV);

        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Client userprofile = snapshot.getValue(Client.class);

                if(userprofile != null ){
                    String fullname= userprofile.name;
                    String email=userprofile.email;
                    String address=userprofile.addresse;
                    String phone=userprofile.telephone;
                    String cin=userprofile.cin;

                    fullnameTextView.setText(fullname);
                    emailTextView.setText(email);
                    addressTextView.setText(address);
                    cinTextView.setText(cin);
                    phoneTextView.setText(phone);




                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Account.this, "Something wrong happend !" , Toast.LENGTH_LONG).show();

            }
        });
//
//

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return;

                    case R.id.account:
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(),Settings.class));
                        finish();
                        overridePendingTransition(0,0);
                        return;
                }
            }
        });


        // email verification


        auth = FirebaseAuth.getInstance();


        verifyemail = findViewById(R.id.verifyemail);
        verify = findViewById(R.id.verify);


        if (!auth.getCurrentUser().isEmailVerified()) {
            verify.setVisibility(View.VISIBLE);
            verifyemail.setVisibility(View.VISIBLE);
        }


        verifyemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send the email

                auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Account.this, "verification Email Sent !", Toast.LENGTH_SHORT).show();
                        verify.setVisibility(View.GONE);
                        verifyemail.setVisibility(View.GONE);
                    }
                });
            }
        });






    }
}
