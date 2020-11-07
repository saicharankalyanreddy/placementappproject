package com.example.ossd_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class students_appliedfor extends AppCompatActivity {


    RecyclerView studentappliedfor;

    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;

    studentappliedforadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_appliedfor);


        studentappliedfor=findViewById(R.id.studentappliedfor);

        auth= FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();




        Query q = firebaseFirestore.collection("students").document(auth.getCurrentUser().getUid())
                .collection("companies_appliedfor");

        FirestoreRecyclerOptions<companylist> options = new FirestoreRecyclerOptions.Builder<companylist>()
                .setQuery(q,companylist.class).build();
        adapter=new studentappliedforadapter(options);



        studentappliedfor.setAdapter(adapter);



        studentappliedfor.setHasFixedSize(true);
        studentappliedfor.setLayoutManager(new LinearLayoutManager(this));




    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    class  studentappliedforadapter extends FirestoreRecyclerAdapter<companylist,studentappliedvh>{


        /**
         * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
         * FirestoreRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public studentappliedforadapter(@NonNull FirestoreRecyclerOptions<companylist> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull studentappliedvh holder, int position, @NonNull companylist model) {

            holder.cname.setText(model.getName());
            holder.dtime.setVisibility(View.GONE);
            holder.ddate.setVisibility(View.GONE);
            holder.hrs.setVisibility(View.GONE);
            holder.deadline.setVisibility(View.GONE);

        }

        @NonNull
        @Override
        public studentappliedvh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_item,parent,false);
            return new studentappliedvh(v);

        }
    }

    class studentappliedvh extends RecyclerView.ViewHolder{

        TextView cname,ddate,dtime,hrs,deadline;

        public studentappliedvh(@NonNull View itemView) {
            super(itemView);

            cname=itemView.findViewById(R.id.comp_name);
            hrs=itemView.findViewById(R.id.textView6);
            deadline=itemView.findViewById(R.id.textView3);
            ddate=itemView.findViewById(R.id.deadline_date);
            dtime=itemView.findViewById(R.id.deadline_time);


        }
    }



}