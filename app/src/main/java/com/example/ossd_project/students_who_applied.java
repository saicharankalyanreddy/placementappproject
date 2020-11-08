package com.example.ossd_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class students_who_applied extends AppCompatActivity {

    RecyclerView swa;


    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;
    swaadapter adapter;

    String cname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_who_applied);

        swa=findViewById(R.id.studentswhoapplied);


        auth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        cname =getIntent().getStringExtra("cn");

        Query q = firebaseFirestore.collection("companies").document(cname).collection("students_applied");

        FirestoreRecyclerOptions<Student> options = new FirestoreRecyclerOptions.Builder<Student>()
                .setQuery(q,Student.class).build();

        adapter = new swaadapter(options);


        swa.setAdapter(adapter);


        swa.setHasFixedSize(true);
        swa.setLayoutManager(new LinearLayoutManager(this));





    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    class swaadapter extends FirestoreRecyclerAdapter<Student,swavh>{

        /**
         * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
         * FirestoreRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public swaadapter(@NonNull FirestoreRecyclerOptions<Student> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull final swavh holder, int position, @NonNull final Student model) {


            holder.cname.setText(model.getFullName());
            holder.ddate.setText(model.getEmail());

            holder.dtime.setVisibility(View.GONE);








        }

        @NonNull
        @Override
        public swavh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.companyview,parent,false);
            return new swavh(v);

        }
    }
    class swavh extends RecyclerView.ViewHolder{

        TextView cname,ddate,dtime;

        public swavh(@NonNull View itemView) {
            super(itemView);

            cname=itemView.findViewById(R.id.companyname);
            ddate=itemView.findViewById(R.id.deadlinedate);
            dtime=itemView.findViewById(R.id.deadlinetime);

        }
    }
}