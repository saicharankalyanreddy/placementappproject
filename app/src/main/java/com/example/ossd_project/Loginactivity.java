package com.example.ossd_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Loginactivity extends AppCompatActivity {

    CheckBox sp;
    TextInputEditText email;
    TextInputEditText password;
    Button signuppage;
    Button login;
    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;
    // to check git

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);


        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        signuppage=findViewById(R.id.signuppage);
        auth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        signuppage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ir = new Intent(Loginactivity.this,Registeractivity.class);
                ir.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ir);
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
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
                else if(password.getText().toString().isEmpty()){
                    password.setError("Password can not be empty");
                    password.requestFocus();
                }
                else {
                    auth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                checkuserAccessLevel(auth.getCurrentUser().getUid());
                            }
                            else {
                                Toast.makeText(Loginactivity.this,"Credentials Wrong",Toast.LENGTH_LONG).show();
                                email.requestFocus();
                            }
                        }
                    });

                }

            }
        });

    }


    private void checkuserAccessLevel(String uid) {
        DocumentReference df = FirebaseFirestore.getInstance().collection("students").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","OnSuccess: " + documentSnapshot.getData());
                //identify user access level
                if(documentSnapshot.getString("isStudent") != "1" ){

                    Intent i = new Intent(Loginactivity.this,MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(new Intent(i));
                    finish();
                }
                if(documentSnapshot.getString("isAdmin") != null) {
                    Intent i = new Intent(Loginactivity.this,AdminDashboard.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(new Intent(i));
                    finish();
                }

            }
        });
    }
}