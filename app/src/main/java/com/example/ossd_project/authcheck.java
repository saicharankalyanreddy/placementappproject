package com.example.ossd_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class authcheck extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authcheck);

        auth=FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                if(auth.getCurrentUser()==null){

                    Intent i = new Intent(authcheck.this, Loginactivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }
                    else {
                        checkuserAccessLevel(auth.getCurrentUser().getUid());
                }
            }
        }, 2000);

    }


    private void checkuserAccessLevel(String uid) {
        DocumentReference df = FirebaseFirestore.getInstance().collection("students").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","OnSuccess: " + documentSnapshot.getData());
                //identify user access level
                if(documentSnapshot.getString("isStudent") != "1" ){

                    Intent i = new Intent(authcheck.this,MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(new Intent(i));
                    finish();
                }
                if(documentSnapshot.getString("isAdmin") != null) {
                    Intent i = new Intent(authcheck.this,admin_companieslist.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(new Intent(i));
                    finish();
                }

            }
        });
    }



}