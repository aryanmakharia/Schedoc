package com.example.drapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drapp.ui.login.DocDash;
import com.example.drapp.ui.login.GInfo;
import com.example.drapp.ui.login.Scheduler;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.ArrayList;
import java.util.Locale;

import static androidx.core.content.ContextCompat.startActivity;

public class DocAdapter extends RecyclerView.Adapter<DocAdapter.DocViewHolder> {

    Context context;
    ArrayList<UserHelperClass> list;

    public DocAdapter(Context context, ArrayList<UserHelperClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public DocViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new DocViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DocViewHolder holder, int position) {

        UserHelperClass user = list.get(position);
        holder.name.setText(user.getName());
        holder.email.setText(user.getEmail());
        holder.phno.setText(user.getPhno());
        holder.spec.setText(user.getSpec());
        holder.getLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String geoUri = "http://maps.google.com/maps?q=loc:" + user.getLat() + "," + user.getLon() + " (" + "mTitle" + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(geoUri));
                Intent chooser = Intent.createChooser(intent, "Launch maps");
                v.getContext().startActivity(chooser);

            }
        });

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on clicking card of doctors
//                Toast.makeText(context, "Booked with!"+user.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), Scheduler.class);
                intent.putExtra("email", user.getEmail());
                intent.putExtra("phno", user.getPhno());
                intent.putExtra("name", user.getName());

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class DocViewHolder extends RecyclerView.ViewHolder {

        TextView name, email, phno, spec;
        Button getLoc;

        LinearLayout linearLayout;


        public DocViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textView5);
            email = itemView.findViewById(R.id.textView8);
            phno = itemView.findViewById(R.id.textView10);
            spec = itemView.findViewById(R.id.textView12);
            getLoc = itemView.findViewById(R.id.getLoc);

            linearLayout = itemView.findViewById(R.id.userList);
        }
    }
}
