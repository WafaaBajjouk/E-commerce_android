package com.example.clientapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.content.Intent;
import android.view.MenuItem;
import androidx.annotation.NonNull;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientapp.Classes.Marchandise;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MarchAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private List<Marchandise> mUploads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomBar);
        bottomNavigationView.setSelectedItemId(R.id.home);


        mRecyclerView = findViewById(R.id.recycler1);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:

                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(),Account.class));
                        finish();
                        overridePendingTransition(0,0);
                        return;

                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(),Settings.class));
                        finish();
                        overridePendingTransition(0,0);
                        return;
                }
            }
        });




        mUploads = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("merchandises");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Marchandise upload = postSnapshot.getValue(Marchandise.class);
                    mUploads.add(upload);
                }

                mAdapter = new MarchAdapter(MainActivity.this, mUploads);
                mRecyclerView.setAdapter(mAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }
}
