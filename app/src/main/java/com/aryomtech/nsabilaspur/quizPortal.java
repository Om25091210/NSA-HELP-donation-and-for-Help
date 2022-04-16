package com.aryomtech.nsabilaspur;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class quizPortal extends AppCompatActivity {

    CardView play,results,upcoming;

    DatabaseReference ref;
    FirebaseDatabase database;

    FirebaseAuth auth;
    FirebaseUser useruuid;

    Dialog dialogAnimated;
    Button yes;
    String currentquiz="";
    String submitted="";
    String show="";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_portal);

        play=findViewById(R.id.play);
        results=findViewById(R.id.Results);
        upcoming=findViewById(R.id.upcoming);

        try {
            QUIZ.quiz.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

        auth = FirebaseAuth.getInstance();
        useruuid = auth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("SubmittedUsers");

        dialogAnimated = new Dialog(quizPortal.this, R.style.dialogstyletick);
        dialogAnimated.setContentView(R.layout.play_dialog_layout);
        yes = dialogAnimated.findViewById(R.id.button2);

        Window window = quizPortal.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(quizPortal.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(quizPortal.this, R.color.statusbar));

        callfordatacheck();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(submitted.equals("yes")){
                        try {
                            dialogAnimated.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogAnimated.dismiss();
                            }
                        });

                    }
                    else if(submitted.equals("no"))
                    {
                        Intent openplay=new Intent(quizPortal.this,QUIZ.class);
                        openplay.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(openplay);
                        finish();
                    }

            }
        });

        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent openplay=new Intent(quizPortal.this,Results.class);
                openplay.putExtra("sendingCurrentQuiz",currentquiz);
                startActivity(openplay);

            }
        });

        upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openplay=new Intent(quizPortal.this,Upcoming.class);
                startActivity(openplay);
            }
        });
    }

    @Override
    public void onBackPressed() {

        try {
            QUIZ.quiz.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onBackPressed();
    }

    public void closecut(View view) {
        finish();
    }

    public void callfordatacheck(){

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String active=dataSnapshot.child("active").getValue(String.class);
                currentquiz=active;
                if(dataSnapshot.child(active).child("users").child(useruuid.getUid()).exists()){
                    submitted="yes";
                }
                else{
                    submitted="no";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}