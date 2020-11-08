package com.example.ossd_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class addcompany extends AppCompatActivity {

    ImageView deadlinedate,deadlinetime;

    RadioGroup iorf;

    EditText dtt,ddt;

    EditText companyname;
    EditText companyctc;
    EditText role;
    EditText CGPA;
    EditText aboutcompany;
    EditText additionalinfo;
    String internorFTE;

    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcompany);


        ddt=findViewById(R.id.textdate);
        dtt=findViewById(R.id.texttime);

        deadlinedate=findViewById(R.id.buttondate);

        deadlinetime=findViewById(R.id.buttontime);

        aboutcompany=findViewById(R.id.aboutc);
        additionalinfo=findViewById(R.id.additionalc);
        companyname=findViewById(R.id.cname);
        role=findViewById(R.id.crole);
        companyctc=findViewById(R.id.cctc);
        CGPA=findViewById(R.id.mincg);

        iorf=findViewById(R.id.intenorfte);


        iorf.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.intern){
                    internorFTE="intern";
                }
                if(checkedId==R.id.fte){
                    internorFTE="fte";
                }
                else{
                    internorFTE="intern and fte";
                }
            }
        });

        ////timepicker

        deadlinetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(addcompany.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                dtt.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });


////datepicker
        deadlinedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(addcompany.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                ddt.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });









    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addc,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.done){
            adddata();
        }
        return super.onOptionsItemSelected(item);
    }



    void adddata(){

        if(companyname.getText().toString().isEmpty()){
            companyname.setError("Required!");
        }
        else if(companyctc.getText().toString().isEmpty()){
            companyctc.setError("required!");
        }
        else if(role.getText().toString().isEmpty()){
            role.setError("required!");
        }
        else if(CGPA.getText().toString().isEmpty()){
            CGPA.setError("required!");
        }
        else if(ddt.getText().toString().isEmpty()){
            ddt.setError("required!");
        }
        else if(dtt.getText().toString().isEmpty()){
            dtt.setError("required!");
        }
        else if(internorFTE.isEmpty()){
            internorFTE="intern and fte";
            Toast.makeText(this,"Did not select any drive so taking it as intern and fte",Toast.LENGTH_LONG).show();
        }
        else{

            Map<String,Object> cdetails=new HashMap<>();
            cdetails.put("name",companyname.getText().toString());
            cdetails.put("ctc",companyctc.getText().toString());
            cdetails.put("role",role.getText().toString());
            cdetails.put("cgpa",CGPA.getText().toString());
            cdetails.put("deadlinedate",ddt.getText().toString());
            cdetails.put("deadlinetime",dtt.getText().toString());
            cdetails.put("internorfte",internorFTE);
            if(!aboutcompany.getText().toString().isEmpty()){
                cdetails.put("about",aboutcompany.getText().toString());
            }
            if(!additionalinfo.getText().toString().isEmpty()){
                cdetails.put("additional",additionalinfo.getText().toString());
            }

            FirebaseFirestore.getInstance().collection("companies").document(companyname.getText().toString())
                    .set(cdetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(addcompany.this,"Company successfully added",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(addcompany.this,admin_companieslist.class));
                    }
                    else {
                        Toast.makeText(addcompany.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });






        }



    }




}