package com.example.ossd_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Applicationview extends AppCompatActivity {

    TextView company_name,company_description,company_ctc;

    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;

    TextInputLayout afn,areg,aemail;

    String resumesrc;

    ImageView r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicationview);

        auth= FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();

        company_name = findViewById(R.id.name_company);
        company_description = findViewById(R.id.company_description);
        company_ctc = findViewById(R.id.companyctc);

        afn=findViewById(R.id.afn);
        areg=findViewById(R.id.areg);
        aemail=findViewById(R.id.aemail);
        r=findViewById(R.id.aresume);




        final String cname = getIntent().getStringExtra("cname");


        DocumentReference df = firebaseFirestore.collection("companies").document(cname);

        df.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                company_name.setText(value.getString("name"));
                company_description.setText(value.getString("about"));
                company_ctc.setText(value.getString("ctc"));

            }
        });


        df.collection("students_applied").document(auth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                afn.getEditText().setText(value.getString("FullName"));
                aemail.getEditText().setText(value.getString("Email"));
                areg.getEditText().setText(value.getString("RegNo"));

                resumesrc=value.getString("Resume");

            }
        });


        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Applicationview.this,resumesrc,Toast.LENGTH_LONG).show();

                Intent i = new Intent(Applicationview.this,pdfview.class);
                i.putExtra("pdflink",resumesrc);
                startActivity(i);

            }
        });




















    }
}