package com.example.ossd_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class Registeractivity extends AppCompatActivity {
    Button loginpage;
    Spinner sem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeractivity);
        loginpage=findViewById(R.id.tologinpage);
        loginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Registeractivity.this,Loginactivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

            }
        });
        sem = findViewById(R.id.semester);

        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i=1;i<=8;i++){
            arrayList.add(i);
        }
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(Registeractivity.this,
                android.R.layout.simple_list_item_1,arrayList);
        sem.setAdapter(arrayAdapter);

    }
}