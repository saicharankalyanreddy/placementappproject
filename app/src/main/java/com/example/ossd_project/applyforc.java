package com.example.ossd_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class applyforc extends AppCompatActivity {

    TextInputLayout fullname,regnumber,email;
    ImageButton resume;
    Button apply;
    Uri fileuri;
    String cname;

    String fileurl="";
    ProgressDialog loading;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applyforc);



        fullname=findViewById(R.id.sfullname);
        regnumber=findViewById(R.id.regno);
        email=findViewById(R.id.semail);
        resume=findViewById(R.id.resume);
        apply=findViewById(R.id.sapply);

        loading = new ProgressDialog(applyforc.this);


        cname=getIntent().getStringExtra("cname");


        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.setType("application/pdf");
                startActivityForResult(Intent.createChooser(i,"Select file"),43);

            }
        });


        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fullname.getEditText().getText().toString().isEmpty()){
                    fullname.setError("Can not be empty");
                }
                else if(regnumber.getEditText().getText().toString().isEmpty()){
                    regnumber.setError("Can not be empty");
                }
                else if(email.getEditText().getText().toString().isEmpty()){
                    email.setError("Can not be empty");
                }

                else if(fileurl.isEmpty()){
                    resume.requestFocus();

                }
                else {



                    Map<String,Object> m = new HashMap<>();
                    m.put("FullName",fullname.getEditText().getText().toString());
                    m.put("RegNo",regnumber.getEditText().getText().toString());
                    m.put("Email",email.getEditText().getText().toString());
                    m.put("Resume",fileurl);
                    FirebaseFirestore.getInstance().collection("companies")
                            .document(cname).collection("students_applied")
                            .document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(m).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Map<String,Object> ma = new HashMap<>();
                            ma.put("name",cname);
                            FirebaseFirestore.getInstance().collection("students").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .collection("companies_appliedfor").document(cname).set(ma).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(applyforc.this,"Appllied successfully",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(applyforc.this,MainActivity.class));
                                }
                            });


                        }
                    });

                }

            }
        });





    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==43 && resultCode==RESULT_OK && data!=null && data.getData()!=null){

            loading.setTitle("file uploading");
            loading.show();

            fileuri=data.getData();
            String userid=FirebaseAuth.getInstance().getCurrentUser().getUid();

            final StorageReference storageReference = FirebaseStorage.getInstance().getReference().
                    child(cname).child("Resumefiles").child(userid+".pdf");


            storageReference.putFile(fileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            fileurl=uri.toString();
                            resume.setImageResource(R.drawable.ic_baseline_done_24);
                            loading.dismiss();

                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double p = (100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    int d = (int) p;
                    loading.setMessage(d+"%"+"  uploading...");
                }
            });





        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseFirestore.getInstance().collection("students").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        email.getEditText().setText(value.getString("Email"));
                        fullname.getEditText().setText(value.getString("FullName"));

                    }
                });
    }
}