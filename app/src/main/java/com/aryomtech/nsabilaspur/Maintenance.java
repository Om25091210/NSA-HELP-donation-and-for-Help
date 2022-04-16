package com.aryomtech.nsabilaspur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Maintenance extends AppCompatActivity {

    Button ok;
    public static Activity maintenance;

    DatabaseReference Maintenanceref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        maintenance=this;

        TextView msgtext=findViewById(R.id.textView36);

        Maintenanceref=FirebaseDatabase.getInstance().getReference().child("Maintenance");
        Maintenanceref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String message=snapshot.child("message").getValue(String.class);
                msgtext.setText(message);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        try{
            MainActivity2.maintwo.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ok=findViewById(R.id.button2);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        System.exit(0);
    }
}