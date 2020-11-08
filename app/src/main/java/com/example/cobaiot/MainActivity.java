package com.example.cobaiot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button buttonSwitchOn, buttonSwitchOff, buttonServoUp, buttonServoDown;

    private DatabaseReference databaseReference;
    private static final String TAG = "MainActivity";
    private TextView dtl;
    private TextView tv_lampu;

    private Firebase mRef;
    private Firebase mRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSwitchOn = findViewById(R.id.btn_on);
        buttonSwitchOff = findViewById(R.id.btn_off);
        buttonServoUp = findViewById(R.id.srv_up);
        buttonServoDown = findViewById(R.id.srv_down);

        dtl = (TextView) findViewById(R.id.dtl);
        tv_lampu = (TextView) findViewById(R.id.tv_lampu);

        mRef = new Firebase("https://relay-99773.firebaseio.com/Moisture");
        mRef2 = new Firebase("https://relay-99773.firebaseio.com/Status_Led");

        mRef.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                dtl.setText(value);
                Log.d(TAG, "Sensor : " + value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                    tv_lampu.setText(value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference databaseReferenceLED = databaseReference.child("LED_Status");

        buttonSwitchOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReferenceLED.setValue(1);
            }
        });

        buttonSwitchOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReferenceLED.setValue(0);
            }
        });

        final DatabaseReference databaseReferenceServo = databaseReference.child("Servo_Status");

        buttonServoUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReferenceServo.setValue(1);
            }
        });

        buttonServoDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReferenceServo.setValue(0);
            }
        });
    }
}