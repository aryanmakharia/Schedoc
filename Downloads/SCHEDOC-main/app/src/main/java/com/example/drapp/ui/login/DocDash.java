package com.example.drapp.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drapp.ApptAdapter;
import com.example.drapp.DocHelperClasss;
import com.example.drapp.R;
import com.example.drapp.UserHelperClass;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

public class DocDash extends AppCompatActivity {
    AlertDialog.Builder reset_alert;
    FirebaseUser user;
    FirebaseAuth Fauth;

    TextView textView15;

    // Rview
    RecyclerView recyclerView;
    DatabaseReference database;
    ApptAdapter apptAdapter;
    ArrayList<DocHelperClasss> list;

    Button availBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_dash);
        reset_alert = new AlertDialog.Builder(this);
        textView15 = findViewById(R.id.textView15);

        availBtn = findViewById(R.id.button9);

        textView15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView = findViewById(R.id.apptlist);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(DocDash.this));
                list = new ArrayList<>();

                apptAdapter = new ApptAdapter(DocDash.this, list);
                recyclerView.setAdapter(apptAdapter);

                user = FirebaseAuth.getInstance().getCurrentUser();
//                if(user!=null){
//                    Toast.makeText(DocDash.this, "not null", Toast.LENGTH_SHORT).show();
//                } else Toast.makeText(DocDash.this, "null", Toast.LENGTH_SHORT).show();
//        Intent intent = getIntent();
//        String email = intent.getStringExtra("email");
                String email = user.getEmail().toString();
//                Toast.makeText(DocDash.this, ""+user.getEmail(), Toast.LENGTH_SHORT).show();
//
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                Query checkUser = reference.child("Doctors").orderByChild("email").equalTo(user.getEmail());
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for (DataSnapshot appleSnapshot: snapshot.getChildren()) {
                            UserHelperClass tmp = appleSnapshot.getValue(UserHelperClass.class);
                            String mobile = tmp.getPhno();
//                            Toast.makeText(DocDash.this, ""+mobile, Toast.LENGTH_SHORT).show();
                            database = FirebaseDatabase.getInstance().getReference().child("Dashboard").child(mobile);

                            database.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snap) {
                                    for(DataSnapshot dataSnapshot: snap.getChildren()){

                                        DocHelperClasss user1 = dataSnapshot.getValue(DocHelperClasss.class);
                                        list.add(user1);
//                                        Toast.makeText(DocDash.this, ""+dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();

                                    }
                                    apptAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                }
                            });


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    }
                });
            }
        });

        availBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                String email = user.getEmail().toString();
//                Toast.makeText(DocDash.this, ""+user.getEmail(), Toast.LENGTH_SHORT).show();
//
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                Query checkUser = reference.child("Doctors").orderByChild("email").equalTo(user.getEmail());
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for (DataSnapshot appleSnapshot: snapshot.getChildren()) {
                            UserHelperClass tmp = appleSnapshot.getValue(UserHelperClass.class);
                            String mobile = tmp.getPhno();
                            String drname = tmp.getName();
                            Intent intent = new Intent(DocDash.this,DocHoliday.class);
                            intent.putExtra("phno", mobile);
                            intent.putExtra("name", drname);
                            startActivity(intent);
//                            Toast.makeText(DocDash.this, ""+mobile, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    }
                });
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater Inflater = getMenuInflater();
        Inflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void signnOut() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.resetUserPassword){
            startActivity(new Intent(getApplicationContext(), ResetPass.class));
        }
        if(item.getItemId() == R.id.deleteAcc){
            reset_alert.setTitle("Delete account?")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            Toast.makeText(PatDash.this, "GG", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = Fauth.getInstance().getCurrentUser();
                            user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(DocDash.this, "Account deleted", Toast.LENGTH_SHORT).show();
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                                    String email = user.getEmail();
                                    Query checkUser = reference.child("Doctors").orderByChild("email").equalTo(email);
                                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                            for (DataSnapshot appleSnapshot: snapshot.getChildren()) {
                                                appleSnapshot.getRef().removeValue();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                            Toast.makeText(DocDash.this, "Error in account deletion", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    Fauth.getInstance().signOut();
                                    signnOut();
                                    startActivity(new Intent(getApplicationContext(), PatLogin.class));
                                    finish();
                                }
                            });
                        }
                    }).setNegativeButton("Cancel",null)
                    .create().show();
        }
        if(item.getItemId() == R.id.logoutBtn){
            Fauth.getInstance().signOut();
            signnOut();
            startActivity(new Intent(getApplicationContext(), PatLogin.class));
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }
}