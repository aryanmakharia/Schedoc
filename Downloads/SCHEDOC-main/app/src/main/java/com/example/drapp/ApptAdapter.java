package com.example.drapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drapp.ui.login.MyAppoints;
import com.example.drapp.ui.login.Scheduler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ApptAdapter extends RecyclerView.Adapter<ApptAdapter.ApptViewHolder> {

    Context context;

    ArrayList<DocHelperClasss> list;
    AlertDialog.Builder reset_alert;

    public ApptAdapter(Context context, ArrayList<DocHelperClasss> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public ApptViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.appt,parent,false);
        return new ApptViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ApptViewHolder holder, int position) {
        DocHelperClasss user = list.get(position);
        holder.name.setText(user.getName());
        holder.phno.setText(user.getPhno());
        holder.slot.setText(user.getSlot());
        holder.date.setText(user.getDate());
        holder.reason.setText(user.getReason());

//        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // on clicking card of doctors
//                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent(v.getContext(), Scheduler.class);
////                intent.putExtra("email", user.getEmail());
////                intent.putExtra("phno", user.getPhno());
////                intent.putExtra("name", user.getName());
//
////                v.getContext().startActivity(intent);
//            }
//        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_alert = new AlertDialog.Builder(context);
                // on clicking card of doctors
                reset_alert.setTitle("Delete Appointment?")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                                DatabaseReference reference = ref.child("Appoint").child(user.getDrphno());
                                String fdate = user.getDate().replaceAll("[^a-zA-Z0-9]", "");
                                String s = user.getSlot();
                                if(s.equals("4-4:30")) reference.child(fdate).child("s400").setValue(0);
                                if(s.equals("4:30-5")) reference.child(fdate).child("s430").setValue(0);
                                if(s.equals("5-5:30")) reference.child(fdate).child("s500").setValue(0);
                                if(s.equals("5:30-6")) reference.child(fdate).child("s530").setValue(0);
                                if(s.equals("6-6:30")) reference.child(fdate).child("s600").setValue(0);
                                if(s.equals("6:30-7")) reference.child(fdate).child("s630").setValue(0);
//
                                Query checkUser = ref.child("Dashboard").child(user.getDrphno()).orderByChild("phno").equalTo(user.getPhno());
                                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                        for (DataSnapshot appleSnapshot: snapshot.getChildren()) {
                                            appleSnapshot.getRef().removeValue();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
//                                        Toast.makeText(PatDash.this, "Error in account deletion", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                Intent intent = new Intent (v.getContext(), MyAppoints.class);
                                v.getContext().startActivity(intent);
                            }
                        }).setNegativeButton("Cancel",null)
                        .create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ApptViewHolder extends RecyclerView.ViewHolder{

        TextView name, phno, reason, slot, date;
        Button deleteBtn;

        LinearLayout linearLayout;

        public ApptViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textView5);
            phno = itemView.findViewById(R.id.textView8);
            slot = itemView.findViewById(R.id.textView10);
            date = itemView.findViewById(R.id.textView12);
            reason = itemView.findViewById(R.id.textView14);
            deleteBtn = itemView.findViewById(R.id.getLoc);

//            linearLayout = itemView.findViewById(R.id.apptlist);
        }
    }

}
