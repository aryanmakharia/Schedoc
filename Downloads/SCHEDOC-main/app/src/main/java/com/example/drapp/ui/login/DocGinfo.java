package com.example.drapp.ui.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.drapp.R;
import com.example.drapp.UserHelperClass;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DocGinfo extends AppCompatActivity {

    EditText Gname;
    EditText Gnum;
    Button button;
    EditText speciality;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    Button locBtn;
    int PLACE_PICKER_REQUEST =1;
    String lon,lat;
    EditText addr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_ginfo);
        Gname = findViewById(R.id.gname);
        Gnum = findViewById(R.id.gnum);
        button = findViewById(R.id.button2);
        speciality = findViewById(R.id.editTextTextPersonName2);
        locBtn = findViewById(R.id.locBtn);
        addr = findViewById(R.id.editTextTextPersonName3);

        locBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(DocGinfo.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Doctors");
                String name = Gname.getText().toString();
                if(name.matches("")){
                    Gname.setError("Please enter your name");
                    return;
                }
                String spec= speciality.getText().toString();
                if(name.matches("")){
                    speciality.setError("Please enter your speciality");
                    return;
                }


                Intent intent = getIntent();
                String email = intent.getStringExtra("email");
                String phno = Gnum.getText().toString();
                if(phno.matches("")){
                    Gnum.setError("Please enter your phone no.");
                    return;
                }

                String address = addr.getText().toString();

                UserHelperClass helperClass = new UserHelperClass(name, email, phno, spec, lon, lat, address);
                reference.child(phno).setValue(helperClass);
                startActivity(new Intent(getApplicationContext(), DocDash.class));
            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST){
            if(resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(data, this);
                StringBuilder stringBuilder = new StringBuilder();
                lat = String.valueOf(place.getLatLng().latitude);
                lon = String.valueOf(place.getLatLng().longitude);
//                Toast.makeText(this, ""+lat+" "+lon, Toast.LENGTH_SHORT).show();

            }
        }

    }
}