package com.example.drapp.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drapp.ApptAdapter;
import com.example.drapp.DocHelperClasss;
import com.example.drapp.PapptAdapter;
import com.example.drapp.R;
import com.example.drapp.UserHelperClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyAppoints extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    PapptAdapter apptAdapter;
    ArrayList<DocHelperClasss> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appoints);

        TextView textView17 = findViewById(R.id.textView17);


        textView17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView = findViewById(R.id.userList);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(MyAppoints.this));
                list = new ArrayList<>();

                apptAdapter = new PapptAdapter(MyAppoints.this, list);
                recyclerView.setAdapter(apptAdapter);

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                if(user!=null){
//                    Toast.makeText(MyAppoints.this, "not null", Toast.LENGTH_SHORT).show();
//                } else Toast.makeText(MyAppoints.this, "null", Toast.LENGTH_SHORT).show();
//
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                Query checkUser = reference.child("Patients").orderByChild("email").equalTo(user.getEmail());
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for (DataSnapshot appleSnapshot: snapshot.getChildren()) {
                            UserHelperClass tmp = appleSnapshot.getValue(UserHelperClass.class);
                            String mobile = tmp.getPhno();
//                            Toast.makeText(MyAppoints.this, ""+mobile, Toast.LENGTH_SHORT).show();

                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                            ref.child("Dashboard")
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot st : dataSnapshot.getChildren()) {
                                                Query checkUser = ref.child("Dashboard").child(st.getKey()).orderByChild("phno").equalTo(mobile);
                                                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull @NotNull DataSnapshot snap) {
                                                        for (DataSnapshot appleSnapshot: snap.getChildren()) {
                                                            DocHelperClasss user1 = appleSnapshot.getValue(DocHelperClasss.class);
                                                            list.add(user1);
//                                                            Toast.makeText(MyAppoints.this, ""+appleSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                                                        }
                                                        apptAdapter.notifyDataSetChanged();
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
//                                                        Toast.makeText(MyAppoints.this, "Error", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                            }
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });


//                            -----------------------------
//                            database = FirebaseDatabase.getInstance().getReference().child("Dashboard").child(mobile);
//
//                            database.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull @NotNull DataSnapshot snap) {
//                                    for(DataSnapshot dataSnapshot: snap.getChildren()){
//
//                                        DocHelperClasss user1 = dataSnapshot.getValue(DocHelperClasss.class);
//                                        list.add(user1);
//                                        Toast.makeText(MyAppoints.this, ""+dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
//
//                                    }
//                                    apptAdapter.notifyDataSetChanged();
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//                                }
//                            });


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    }
                });
            }
        });

    }
}