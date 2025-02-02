package com.example.cobaiot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button buttonSwitchOn, buttonSwitchOff, buttonSwitchOn2, buttonSwitchOff2, buttonServoUp, buttonServoDown;

    private DatabaseReference databaseReference;
    private static final String TAG = "MainActivity";
    private TextView dtl_tanah;
    private TextView dtl_suhu;
    private TextView pompa;
    private TextView pendingin;
    private TextView batasatas;

    private Firebase mRef;
    private Firebase mRef1;
    private Firebase mRef2;
    private Firebase mRef3;
    private Firebase mRef4;

    private Spinner Batas_Atas;
    private String[] batasAtas = {
            "Basah",
            "Sedang"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Batas_Atas = (Spinner) findViewById(R.id.spinner);
        // inisialiasi Array Adapter dengan memasukkan string array di atas
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, batasAtas);
        // mengeset Array Adapter tersebut ke Spinner
        Batas_Atas.setAdapter(adapter);
        // mengeset listener untuk mengetahui saat item dipilih
        Batas_Atas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Aksi yang dilakukan ketika memilih item (diambil dari adapter)
                final DatabaseReference databaseReferenceServo = databaseReference.child("Batas_Atas");
                String m = adapter.getItem(i);
                if (m == "Basah"){
                    databaseReferenceServo.setValue("Basah");
                }
                else {
                    databaseReferenceServo.setValue("Sedang");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        buttonSwitchOn = findViewById(R.id.btn_on);
        buttonSwitchOff = findViewById(R.id.btn_off);
        buttonSwitchOn2 = findViewById(R.id.btn_on2);
        buttonSwitchOff2 = findViewById(R.id.btn_off2);
        buttonServoUp = findViewById(R.id.srv_open);
        buttonServoDown = findViewById(R.id.srv_close);

        dtl_tanah = (TextView) findViewById(R.id.dtl_tanah);
        dtl_suhu = (TextView) findViewById(R.id.dtl_suhu);
        pompa = (TextView) findViewById(R.id.pompa);
        pendingin = (TextView) findViewById(R.id.pendingin);
        batasatas = (TextView) findViewById(R.id.bts_ats);

        mRef = new Firebase("https://relay-99773.firebaseio.com/Kelembaban");
        mRef1 = new Firebase("https://relay-99773.firebaseio.com/Suhu");
        mRef2 = new Firebase("https://relay-99773.firebaseio.com/Status_Pompa");
        mRef3 = new Firebase("https://relay-99773.firebaseio.com/Status_Pendingin");
        mRef4 = new Firebase("https://relay-99773.firebaseio.com/Batas_Atas");

        mRef.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                dtl_tanah.setText(value);
                Log.d(TAG, "Sensor : " + value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mRef1.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                dtl_suhu.setText(value);
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
                    pompa.setText(value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mRef3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                    pendingin.setText(value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        mRef4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                batasatas.setText(value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference databaseReferenceLED = databaseReference.child("Pompa_Air");

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

        final DatabaseReference databaseReferenceLED2 = databaseReference.child("Pendingin");

        buttonSwitchOn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReferenceLED2.setValue(1);
            }
        });

        buttonSwitchOff2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReferenceLED2.setValue(0);
            }
        });

        final DatabaseReference databaseReferenceServo = databaseReference.child("Servo");

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