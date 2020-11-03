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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* public class Registeractivity extends AppCompatActivity {
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
} */
public class Registeractivity extends AppCompatActivity {
    Button loginpage,signup;
    Spinner sem;
    EditText email,username,cgpa,p,cp,code;
    RadioButton isStudent,isCIR;
    TextView sem_text;
    int flag =0,flag1=0;

    FirebaseAuth fAuth;
    boolean valid = true;
    FirebaseFirestore fstore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeractivity);


        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();


        loginpage=findViewById(R.id.tologinpage); //btn
        signup=findViewById(R.id.signup); //btn
        email=findViewById(R.id.email);
        username=findViewById(R.id.Username);
        cgpa=findViewById(R.id.Cgpa);
        p=findViewById(R.id.password);
        cp=findViewById(R.id.confirmpass);
        code = findViewById(R.id.code);

         isStudent = (RadioButton) findViewById(R.id.isStudent);
         isCIR = (RadioButton) findViewById(R.id.isCIR);
      RadioGroup radiogrp = (RadioGroup) findViewById(R.id.rg);
        sem_text = findViewById(R.id.sem_text);
        sem = findViewById(R.id.semester);

        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i=1;i<=8;i++){
            arrayList.add(i);
        }
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(Registeractivity.this,
                android.R.layout.simple_list_item_1,arrayList);
        sem.setAdapter(arrayAdapter);

        radiogrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (isStudent.isChecked() == true) {
                    flag=1;
                   code.setVisibility(View.GONE);
                    cgpa.setVisibility(View.VISIBLE);
                    sem.setVisibility(View.VISIBLE);
                    sem_text.setVisibility(View.VISIBLE);
                }else{
                    flag=2;
                    code.setVisibility(View.VISIBLE);
                   cgpa.setVisibility(View.GONE);
                   sem.setVisibility(View.GONE);
                   sem_text.setVisibility(View.GONE);
                }

            }
        });

       signup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               checkField(email);
               checkField(username);

               if(flag == 1)
                   checkField(cgpa);
               if (flag==2) {
                   checkField(code);
              //     if(code.getText().toString() != "0000")
                   //    code.setError("Error");
                //  else
                       flag1=1;
               }
               checkField(p);
               checkField(cp);

               if(valid)
               {
                   fAuth.createUserWithEmailAndPassword(email.getText().toString(),p.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                       @Override
                       public void onSuccess(AuthResult authResult) {
                           FirebaseUser user = fAuth.getCurrentUser();
                           Toast.makeText(Registeractivity.this,"Account Created",Toast.LENGTH_LONG).show();
                           DocumentReference df1 = fstore.collection("students").document(user.getUid());
                           Map<String,Object> userInfo = new HashMap<>();
                           userInfo.put("FullName",username.getText().toString());
                           userInfo.put("Email",email.getText().toString());

                           //specify access level
                           if(flag==1){
                           userInfo.put("isStudent","1");
                           userInfo.put("CGPA",cgpa.getText().toString());
                           userInfo.put("Sem",sem.getSelectedItem().toString());
                           //DocumentReference df1 = fstore.collection("students").document(user.getUid());
                           df1.set(userInfo);
                           }
                           if(flag==2 && flag1==1) {
                               userInfo.put("isAdmin", "1");
                              // DocumentReference df2 = fstore.collection("CIR").document(user.getUid());
                               df1.set(userInfo);
                           }
                           startActivity(new Intent(getApplicationContext(),Loginactivity.class));
                           finish(); //so that the user does not go back to registration page once he is done
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(Registeractivity.this,"Failed to Create Account",Toast.LENGTH_LONG).show();
                       }
                   });
               }
           }
       });

        loginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(),Loginactivity.class));
            }
        });

    }

    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        }else {
            valid = true;
        }

        return valid;
    }
}