package com.example.ossd_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class Each_Company_Details extends AppCompatActivity {
TextView company_name,company_description,company_ctc;
TextInputLayout job_offer,job_role,other_details;
Button apply;

    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each__company__details);


        auth= FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();

        company_name = findViewById(R.id.name_company);
        company_description = findViewById(R.id.company_description);
        company_ctc = findViewById(R.id.companyctc);
        job_offer = findViewById(R.id.job_offer);
        job_role = findViewById(R.id.job_role);
        other_details = findViewById(R.id.other_details);
        apply = findViewById(R.id.apply);



        final String cname = getIntent().getStringExtra("cname");

        DocumentReference df = firebaseFirestore.collection("companies").document(cname);

        df.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                company_name.setText(value.getString("name"));
                company_description.setText(value.getString("about"));
                company_ctc.setText(value.getString("ctc"));
                job_role.getEditText().setText(value.getString("role"));
                job_offer.getEditText().setText(value.getString("internorfte"));
                other_details.getEditText().setText(value.getString("cgpa") + " CGPA min");


            }
        });
apply.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(Each_Company_Details.this, "Applied for Company", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(Each_Company_Details.this,applyforc.class);
        i.putExtra("cname",cname);
        startActivity(i);

    }
});


    }
}