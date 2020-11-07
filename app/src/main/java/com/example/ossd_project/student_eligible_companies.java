package com.example.ossd_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class student_eligible_companies extends AppCompatActivity {

    RecyclerView studenteligiblec;

    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;

    studenteligblec_adapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_eligible_companies);
        studenteligiblec=findViewById(R.id.studenteligiblec);

        auth= FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();



        final String studentcg = getIntent().getStringExtra("cg");






        Query q = firebaseFirestore.collection("companies").whereLessThanOrEqualTo("cgpa",studentcg);

        FirestoreRecyclerOptions<companylist> options = new FirestoreRecyclerOptions.Builder<companylist>()
                .setQuery(q,companylist.class).build();
        adapter=new studenteligblec_adapter(options);
        studenteligiblec.setAdapter(adapter);



        studenteligiblec.setHasFixedSize(true);
        studenteligiblec.setLayoutManager(new LinearLayoutManager(this));


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
}


class studenteligblec_adapter extends FirestoreRecyclerAdapter<companylist,studenteligiblec_vh>{

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public studenteligblec_adapter(@NonNull FirestoreRecyclerOptions<companylist> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final studenteligiblec_vh holder, int position, @NonNull final companylist model) {

        holder.cname.setText(model.getName());
        holder.dtime.setText(model.getDeadlinetime());
        holder.ddate.setText(model.getDeadlinedate());



        FirebaseFirestore.getInstance().collection("students").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("companies_appliedfor").document(model.getName()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    holder.alreadyapplied.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(), model.getName(), Toast.LENGTH_SHORT).show();
                Intent in = new Intent(holder.itemView.getContext(),Each_Company_Details.class);
                in.putExtra("cname",model.getName());
                holder.itemView.getContext().startActivity(in);
            }
        });

    }



    @NonNull
    @Override
    public studenteligiblec_vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_item,parent,false);
        return new studenteligiblec_vh(v);
    }
}
class studenteligiblec_vh extends RecyclerView.ViewHolder{
    TextView cname,ddate,dtime;
    ImageButton alreadyapplied;

    public studenteligiblec_vh(@NonNull View itemView) {
        super(itemView);


        cname=itemView.findViewById(R.id.comp_name);
        alreadyapplied=itemView.findViewById(R.id.alreadyapplied);
        ddate=itemView.findViewById(R.id.deadline_date);
        dtime=itemView.findViewById(R.id.deadline_time);
    }





}