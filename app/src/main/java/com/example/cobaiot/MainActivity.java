package com.example.cobaiot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

//    Button buttonSwitchOn, buttonSwitchOff;
    TextView tess;
    ToggleButton toggle;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tess = findViewById(R.id.tes);
        toggle = findViewById(R.id.toogle);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("LED_Status");
        tess.setText(databaseReference.toString());

        if (databaseReference.toString() != null){
            toggle.setChecked(true);
        }
        else {
            toggle.setChecked(false);

        };

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked  ){
                    tess.setText("checked");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            snapshot.getChildren();
                            if(snapshot != null){
                                toggle.setChecked(true);
//                                databaseReference.push().setValue("1");
                                databaseReference.removeValue();
//                                databaseReference.setValue("1");
                                tess.setText("1");

                            }
                            else {
//                                tess.setText("snapshot.getValue().toString()");
                                Toast.makeText(getApplicationContext(),"kosong",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                else{
                    databaseReference.removeValue();
                    tess.setText("unchecked");
//                    toggle.setChecked(false);




                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            snapshot.getChildren();
                            if(snapshot != null){
                                toggle.setChecked(true);
//                                databaseReference.push().setValue("1");
                                databaseReference.removeValue();
//                                databaseReference.setValue("1");
                                tess.setText("kosong");

//                                Toast.makeText(getApplicationContext(),snapshot.getValue().toString(),Toast.LENGTH_LONG).show();

                            }
                            else {
//                                tess.setText("snapshot.getValue().toString()");
                                Toast.makeText(getApplicationContext(),"kosong",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });




//                    Toast.makeText(getApplicationContext(),"kosong",Toast.LENGTH_SHORT).show();
//

                }
            }
        });




    }}
