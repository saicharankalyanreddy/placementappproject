package com.example.ossd_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.zip.Inflater;

public class admin_companieslist extends AppCompatActivity {

    FloatingActionButton addc;
    RecyclerView companies;

    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;
    admincompanylistadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_companieslist);

        addc=findViewById(R.id.addcompany);
        companies=findViewById(R.id.companieslist);
        auth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        Query q = firebaseFirestore.collection("companies");

        FirestoreRecyclerOptions<companylist> options = new FirestoreRecyclerOptions.Builder<companylist>()
                .setQuery(q,companylist.class).build();

        adapter = new admincompanylistadapter(options);


        companies.setAdapter(adapter);

        companies.setHasFixedSize(true);
        companies.setLayoutManager(new LinearLayoutManager(this));



        addc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_companieslist.this,addcompany.class));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {



        getMenuInflater().inflate(R.menu.admin_logout,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutadmin:
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(admin_companieslist.this,Loginactivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}