package com.example.drapp.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.drapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class ResetPass extends AppCompatActivity {

    EditText Pass1, Pass2;
    Button RBtn;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        Pass1 = findViewById(R.id.Rpass1);
        Pass2 = findViewById(R.id.Rpass2);
        RBtn = findViewById(R.id.RBtn);
        user = FirebaseAuth.getInstance().getCurrentUser();

        RBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Pass1.getText().toString().isEmpty()){
                    Pass1.setError("Required field");
                    return;
                }
                if(Pass2.getText().toString().isEmpty()){
                    Pass2.setError("Required field");
                    return;
                }
                if(!Pass1.getText().toString().equals(Pass2.getText().toString())){
                    Pass2.setError("Passwords do not match");
                    return;
                }

                user.updatePassword(Pass1.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ResetPass.this, "Password updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), PatLogin.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(ResetPass.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
