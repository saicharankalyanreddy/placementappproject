package com.example.ossd_project;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class Editprofile extends AppCompatActivity {


    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;

    EditText email,un,cg,sem;

    FloatingActionButton save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);



        auth= FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();

        email=findViewById(R.id.eprofileemail);

        un=findViewById(R.id.eprofileusername);

        cg=findViewById(R.id.eprofilecgpa);

        sem=findViewById(R.id.eprofilesem);

        save = findViewById(R.id.save);







        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog p = new ProgressDialog(Editprofile.this);
                p.setTitle("Profile update");
                p.setMessage("Please wait");
                p.show();


                auth.getCurrentUser().updateEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Map<String,Object> mm = new HashMap<>();
                            mm.put("Email",email.getText().toString());
                            mm.put("FullName",un.getText().toString());
                            mm.put("Sem",sem.getText().toString());
                            mm.put("CGPA",cg.getText().toString());




                            DocumentReference d = firebaseFirestore.collection("students").document(auth.getCurrentUser().getUid());
                            d.update(mm).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        Toast.makeText(Editprofile.this,"Data updated",Toast.LENGTH_SHORT).show();
                                        p.dismiss();
                                        finish();

                                    }
                                    else {
                                        Toast.makeText(Editprofile.this,"Try again later",Toast.LENGTH_SHORT).show();
                                        p.dismiss();

                                    }
                                }

                            });
                        }

                    }
                });
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
