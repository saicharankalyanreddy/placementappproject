package com.example.ossd_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Registeractivity extends AppCompatActivity {
    Button loginpage,signup;
    Spinner sem;
    EditText email,username,cgpa,p,cp;
    FirebaseAuth auth;

    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeractivity);


        auth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();


        loginpage=findViewById(R.id.tologinpage);
        signup=findViewById(R.id.signup);
        email=findViewById(R.id.email);
        username=findViewById(R.id.Username);
        cgpa=findViewById(R.id.Cgpa);
        p=findViewById(R.id.password);
        cp=findViewById(R.id.confirmpass);

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


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().isEmpty()){
                    email.setError("Email can not be empty");
                    email.requestFocus();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                    email.setError("Enter valid Email");
                    email.requestFocus();
                }
                else if(username.getText().toString().isEmpty()){
                    username.setError("Enter Username ");
                    username.requestFocus();
                }
                else if(cgpa.getText().toString().isEmpty()){
                    cgpa.setError("CGPA can not be empty");
                    cgpa.requestFocus();
                }

                else if(p.getText().toString().isEmpty()){
                    p.setError("Password can not be empty");
                    p.requestFocus();

                }

                else if(!p.getText().toString().equals(cp.getText().toString())){
                    cp.setError("Password and confirm password do not match");
                    cp.requestFocus();
                }
                else {
                    final ProgressDialog ps = new ProgressDialog(Registeractivity.this);
                    ps.setTitle("Creating account");
                    ps.setMessage("Please wait");
                    ps.show();
                    auth.createUserWithEmailAndPassword(email.getText().toString(),p.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(Registeractivity.this,"Complted creating account",Toast.LENGTH_LONG).show();
                                Student student = new Student(username.getText().toString(),email.getText().toString(),sem.getSelectedItem().toString()
                                        ,cgpa.getText().toString(),p.getText().toString()
                                );
                                firebaseFirestore.collection("students").document(auth.getCurrentUser().getUid())
                                        .set(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            ps.dismiss();
                                            Intent i = new Intent(Registeractivity.this,MainActivity.class);
                                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(i);
                                            finish();
                                        }

                                    }
                                });
                            }


                            else {
                                ps.dismiss();
                                if(task.getException() instanceof FirebaseAuthUserCollisionException)
                                {
                                    Toast.makeText(Registeractivity.this,"Email already registered",Toast.LENGTH_SHORT).show();
                                }
                                else
                                    Toast.makeText(Registeractivity.this,"Network Error",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }


            }
        });

    }
}