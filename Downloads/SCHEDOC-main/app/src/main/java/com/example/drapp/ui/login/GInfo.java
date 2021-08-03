package com.example.drapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.drapp.R;
import com.example.drapp.UserHelperClass;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GInfo extends AppCompatActivity {

    EditText Gname;
    EditText Gnum;
    Button button;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ginfo);
        Gname = findViewById(R.id.Gname);
        Gnum = findViewById(R.id.Gnum);
        button = findViewById(R.id.button);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Patients");
                String name = Gname.getText().toString();
                if(name.matches("")){
                    Gname.setError("Please enter your name");
                    return;
                }

                Intent intent = getIntent();
                String email = intent.getStringExtra("email");
                String phno = Gnum.getText().toString();
                if(phno.matches("")){
                    Gnum.setError("Please enter your phone no.");
                    return;
                }

                UserHelperClass helperClass = new UserHelperClass(name, email, phno);
                reference.child(phno).setValue(helperClass);
                startActivity(new Intent(getApplicationContext(), PatDash.class));
            }
        });
    }
}