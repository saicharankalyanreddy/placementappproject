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

    TextView password;

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

        password=findViewById(R.id.epassword);


        


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
                            mm.put("email",email.getText().toString());
                            mm.put("username",un.getText().toString());
                            mm.put("sem",sem.getText().toString());
                            mm.put("cgpa",cg.getText().toString());
                            mm.put("password",password.getText().toString());



                            DocumentReference d = firebaseFirestore.collection("students").document(auth.getCurrentUser().getUid());
                               d.update(mm).addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                       if(task.isSuccessful()){

                                           Toast.makeText(Editprofile.this,"Data updated",Toast.LENGTH_SHORT).show();
                                           p.dismiss();
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
                        email.setText(value.getString("email"));
                        un.setText(value.getString("username"));
                        cg.setText(value.getString("cgpa"));
                        sem.setText(value.getString("sem"));
                        password.setText(value.getString("password"));
                    }
                });



    }
}