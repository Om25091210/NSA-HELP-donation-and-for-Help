package com.aryomtech.nsabilaspur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class QrResult extends AppCompatActivity {

    DatabaseReference Verifiedref;
    FirebaseDatabase database;

    String profilepath;
    String uid_of_ngo;

    ImageView profile_image;
    TextView name,email,organization;

    Dialog dialogAnimated;
    Button yes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_result);

        Intent getvalueof_Qr=getIntent();
        uid_of_ngo=getvalueof_Qr.getStringExtra("sendingresultofscanning");

        name=findViewById(R.id.textView8);
        email=findViewById(R.id.textView32);
        organization=findViewById(R.id.textView37);
        profile_image=findViewById(R.id.profile_image);

        database=FirebaseDatabase.getInstance();
        Verifiedref=database.getReference().child("VerifiedNGOs");

        dialogAnimated = new Dialog(this, R.style.dialogstyletick);
        dialogAnimated.setContentView(R.layout.fraud_ngo);
        yes = dialogAnimated.findViewById(R.id.button2);

        Verifiedref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (snapshot.child(uid_of_ngo).exists()) {
                        String ngo_name = snapshot.child(uid_of_ngo).child("NameOfNGO").getValue(String.class);
                        String ngo_email = snapshot.child(uid_of_ngo).child("emailOfNGO").getValue(String.class);
                        String ngo_organization = snapshot.child(uid_of_ngo).child("organization").getValue(String.class);
                        String image = snapshot.child(uid_of_ngo).child("images").getValue(String.class);

                        name.setText(ngo_name);
                        email.setText(ngo_email);
                        organization.setText(ngo_organization);

                        try {
                            Glide.with(QrResult.this).load(image).into(profile_image);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        TextView info = findViewById(R.id.textView51);
                        info.setVisibility(View.GONE);
                        name.setVisibility(View.GONE);
                        email.setVisibility(View.GONE);
                        organization.setVisibility(View.GONE);
                        dialogAnimated.show();
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogAnimated.dismiss();
                            }
                        });

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    TextView info = findViewById(R.id.textView51);
                    info.setVisibility(View.GONE);
                    name.setVisibility(View.GONE);
                    email.setVisibility(View.GONE);
                    organization.setVisibility(View.GONE);
                    dialogAnimated.show();
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogAnimated.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}