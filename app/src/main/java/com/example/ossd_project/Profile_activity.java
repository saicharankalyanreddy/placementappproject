package com.example.ossd_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Profile_activity extends AppCompatActivity {

    FloatingActionButton editprofile;
    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;


    TextView email,un,cg,sem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile_activity);


        auth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        email=findViewById(R.id.profileemail);

        un=findViewById(R.id.profileusername);

        cg=findViewById(R.id.profilecgpa);

        sem=findViewById(R.id.profilesem);




        editprofile=findViewById(R.id.editprofile);
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile_activity.this,Editprofile.class));
            }
        });






    }


    @Override
    protected void onStart() {
        super.onStart();

        firebaseFirestore.collection("students").document(auth.getCurrentUser().getUid())
                .addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        email.setText(value.getString("Email"));
                        un.setText(value.getString("FullName"));
                        cg.setText(value.getString("CGPA"));
                        sem.setText(value.getString("Sem"));
                    }
                });
    }
}