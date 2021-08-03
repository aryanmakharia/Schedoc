package com.example.drapp.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drapp.ApptHelperClass;
import com.example.drapp.DatePickerFragment;
import com.example.drapp.DocHelperClasss;
import com.example.drapp.R;
import com.example.drapp.UserHelperClass;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.Reference;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class Scheduler extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView date;
//    TextView editTextTextPersonName5;
    TextInputLayout til;
    AutoCompleteTextView autoCompleteTextView;

    ArrayList<String> slots;
    ArrayAdapter<String> aas;

    Button button4;

    FirebaseDatabase rootNode;
    DatabaseReference reference,ref;

    String fdate;
    String dat;

    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);
        date = findViewById(R.id.textView13);
        button4 = findViewById(R.id.button4);

        til = (TextInputLayout) findViewById(R.id.til);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();

                datePicker.show(getSupportFragmentManager(), "Date Picker");
//                slotsCall();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = autoCompleteTextView.getText().toString();
//                Toast.makeText(Scheduler.this, ""+s, Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                String phno = intent.getStringExtra("phno");
                String drname = intent.getStringExtra("name");
                ref = rootNode.getReference("Appoint");
                reference = ref.child(phno);
                if(s.equals("4-4:30")) reference.child(fdate).child("s400").setValue(1);
                if(s.equals("4:30-5")) reference.child(fdate).child("s430").setValue(1);
                if(s.equals("5-5:30")) reference.child(fdate).child("s500").setValue(1);
                if(s.equals("5:30-6")) reference.child(fdate).child("s530").setValue(1);
                if(s.equals("6-6:30")) reference.child(fdate).child("s600").setValue(1);
                if(s.equals("6:30-7")) reference.child(fdate).child("s630").setValue(1);

//                 String[] mobile = new String[1];
//                 String[] patnam = new String[1];
                EditText reason = findViewById(R.id.Rtxt);
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                String email = user.getEmail();
                Query checkUser = reference.child("Patients").orderByChild("email").equalTo(email);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

//                        mobile = tmp.getPhno();
                        for (DataSnapshot appleSnapshot: snapshot.getChildren()) {
                            UserHelperClass tmp = appleSnapshot.getValue(UserHelperClass.class);

                             String mobile = tmp.getPhno();
                             String patnam = tmp.getName();
//                            Toast.makeText(Scheduler.this, ""+mobile, Toast.LENGTH_SHORT).show();
                            DatabaseReference r = reference.child("Dashboard");
                            DatabaseReference re = r.child(phno);
                            DocHelperClasss helperClass = new DocHelperClasss(patnam, mobile, reason.getText().toString(), s, dat, drname, phno);
//                            DocHelperClasss helperClass = new DocHelperClasss("ll", "546", "reason.getText().toString()", "s", "dat", "drname", "phno");
                            re.child(mobile).setValue(helperClass);
//                            Toast.makeText(Scheduler.this, "yay", Toast.LENGTH_SHORT).show();

                            // for notification
//                            String mssg = "Appointment booked with " + drname + "on " + "from " + s + ".";
//                            Toast.makeText(Scheduler.this, "+mssg", Toast.LENGTH_SHORT).show();
//                            NotificationCompat.Builder builder = new NotificationCompat.Builder(Scheduler.this)
//                                    .setContentTitle("New notification!").setContentText(mssg).setAutoCancel(true);
//                            Intent intnt = new Intent(Scheduler.this, PatDash.class);

//                            intnt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            intnt.putExtra("message", mssg);
//                            PendingIntent pendingIntent = PendingIntent.getActivity(Scheduler.this, 0,intnt,PendingIntent.FLAG_UPDATE_CURRENT);
//                            builder.setContentIntent(pendingIntent);
//                            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                            notificationManager.notify(0, builder.build());

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        Toast.makeText(Scheduler.this, "Error in account deletion", Toast.LENGTH_SHORT).show();
                    }
                });

                                startActivity(new Intent(getApplicationContext(), PatDash.class));
            }
        });

    }

    private void slotsCall() {

        Intent intent = getIntent();
        String phno = intent.getStringExtra("phno");

//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();


//
        rootNode = FirebaseDatabase.getInstance();
        ref = rootNode.getReference("Appoint");
        reference = ref.child(phno);
        //        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.hasChild(fdate.toString())){
//
//                    String q = snapshot.child(fdate.toString()).child("s400").getValue(String.class);

                    // add redmarking here
                } else {
                    Toast.makeText(Scheduler.this, "Does not exist", Toast.LENGTH_SHORT).show();
                    ApptHelperClass helperClass = new ApptHelperClass(0, 0, 0, 0, 0, 0);
                    reference.child(fdate.toString()).setValue(helperClass);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        slots = new ArrayList<>();

//        ApptHelperClass user;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for ( DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    Toast.makeText(Scheduler.this, fdate+ " "+dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                    if(!fdate.equals(dataSnapshot.getKey())) {
                        continue;
                    }
                    ApptHelperClass user = dataSnapshot.getValue(ApptHelperClass.class);
//                    Toast.makeText(Scheduler.this, "user", Toast.LENGTH_SHORT).show();
                    if(user.getS400() == 0){
                        slots.add("4-4:30");
                    }
                    if(user.getS430() == 0){
                        slots.add("4:30-5");
                    }
                    if(user.getS500() == 0){
                        slots.add("5-5:30");
                    }
                    if(user.getS530() == 0){
                        slots.add("5:30-6");
                    }
                    if(user.getS600() == 0){
                        slots.add("6-6:30");
                    }
                    if(user.getS630() == 0){
                        slots.add("6:30-7");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });




        aas = new ArrayAdapter<>(getApplicationContext(),R.layout.dropdown_item, slots);
        autoCompleteTextView.setAdapter(aas);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        c.set(Calendar.MONTH, month);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        fdate = DateFormat.getDateInstance(DateFormat.MONTH_FIELD).format(c.getTime());
        dat = fdate;
        fdate = fdate.replaceAll("[^a-zA-Z0-9]", "");
//        Toast.makeText(Scheduler.this, fdate, Toast.LENGTH_SHORT).show();
        date.setText(currentDateString);
        slotsCall();
    }

    @Override
    protected void onStart() {
        super.onStart();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }
}