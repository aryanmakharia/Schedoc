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
import android.widget.Toast;

import com.example.drapp.DocAdapter;
import com.example.drapp.R;
import com.example.drapp.UserHelperClass;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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

import java.lang.reflect.Array;
import java.util.ArrayList;


public class PatDash extends AppCompatActivity {

    AlertDialog.Builder reset_alert;
    FirebaseUser user;
    FirebaseAuth Fauth;
    Button button7;

//    for recycler view
    RecyclerView recyclerView;
    DatabaseReference database;
    DocAdapter docAdapter;
    ArrayList<UserHelperClass> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pat_dash);
        reset_alert = new AlertDialog.Builder(this);
        button7 = findViewById(R.id.button7);

//        recyclerView
        recyclerView = findViewById(R.id.userList);
        database = FirebaseDatabase.getInstance().getReference("Doctors");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        docAdapter = new DocAdapter(this, list);

        recyclerView.setAdapter(docAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for ( DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UserHelperClass user = dataSnapshot.getValue(UserHelperClass.class);
                    list.add(user);
                }
                docAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PatDash.this,MyAppoints.class);
                startActivity(intent);
            }
        });

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater Inflater = getMenuInflater();
        Inflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
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
//                                    Toast.makeText(PatDash.this, ""+user.getEmail()+ user.getPhoneNumber(), Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(PatDash.this, "Account deleted", Toast.LENGTH_SHORT).show();
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                                    String email = user.getEmail();
                                    Query checkUser = reference.child("Patients").orderByChild("email").equalTo(email);
                                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                            for (DataSnapshot appleSnapshot: snapshot.getChildren()) {
                                                appleSnapshot.getRef().removeValue();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                            Toast.makeText(PatDash.this, "Error in account deletion", Toast.LENGTH_SHORT).show();
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
}