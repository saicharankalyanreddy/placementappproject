package com.example.ossd_project;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class admincompanylistadapter extends FirestoreRecyclerAdapter<companylist,companyviewholder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public admincompanylistadapter(@NonNull FirestoreRecyclerOptions<companylist> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final companyviewholder holder, int position, @NonNull final companylist model) {
        holder.cname.setText(model.getName());
        holder.dtime.setText(model.getDeadlinetime());
        holder.ddate.setText(model.getDeadlinedate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(holder.itemView.getContext(),students_who_applied.class);
                i.putExtra("cn",model.getName());
                holder.itemView.getContext().startActivity(i);

            }
        });

    }

    @NonNull
    @Override
    public companyviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.companyview,parent,false);
        return new companyviewholder(v);
    }
}

class companyviewholder extends RecyclerView.ViewHolder{

    TextView cname,ddate,dtime;

    public companyviewholder(@NonNull View itemView) {
        super(itemView);

        cname=itemView.findViewById(R.id.companyname);
        ddate=itemView.findViewById(R.id.deadlinedate);
        dtime=itemView.findViewById(R.id.deadlinetime);

    }
}
