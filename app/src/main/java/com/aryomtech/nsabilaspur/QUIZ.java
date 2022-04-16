package com.aryomtech.nsabilaspur;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class QUIZ extends AppCompatActivity {

    DatabaseReference ref;
    FirebaseDatabase database;
    public static Activity quiz;
    RecyclerView recyclerView;

    DatabaseReference reference,submittedusers;

    FirebaseAuth auth;
    FirebaseUser useruuid;

    String active="";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_u_i_z);

        quiz=this;

        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("QUIZ");

        auth = FirebaseAuth.getInstance();
        useruuid = auth.getCurrentUser();

        Window window = QUIZ.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(QUIZ.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(QUIZ.this, R.color.statusbar));

        recyclerView = findViewById(R.id.quiz_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(QUIZ.this,LinearLayoutManager.HORIZONTAL,false);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 active=snapshot.child("active").getValue(String.class);

                Query order=ref.child(active);
                FirebaseRecyclerAdapter<quizData, QuizHolder> firebaseRecyclerAdapter =
                        new FirebaseRecyclerAdapter<quizData, QuizHolder>(
                                quizData.class,
                                R.layout.quiz_card_design,
                                QuizHolder.class,
                                order
                        ) {
                            @Override
                            protected void populateViewHolder(QuizHolder QuizHolder, quizData quizData, int i) {

                                QuizHolder.setView(QUIZ.this,quizData.getQ_pic(),quizData.getQuestion(),quizData.getCorrect(),quizData.getOption1(),quizData.getOption2(),quizData.getOption3(),quizData.getOption4(),quizData.getKey(),active,quizData.getTotal(),quizData.getQuestionNo(),quizData.getSubmit());

                            }
                        };

                recyclerView.setAdapter(firebaseRecyclerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onBackPressed() {

        reference = database.getReference().child("SecondaryQuiz");
        submittedusers=database.getReference().child("SubmittedUsers");

        submittedusers.child(active).child("users").child(useruuid.getUid()).setValue("Submitted");
        reference.child(active).child("users").child(useruuid.getUid()).child("displayname").setValue(useruuid.getEmail());


        finish();

        super.onBackPressed();

    }

    @Override
    protected void onPause() {

        reference = database.getReference().child("SecondaryQuiz");
        submittedusers=database.getReference().child("SubmittedUsers");

        submittedusers.child(active).child("users").child(useruuid.getUid()).setValue("Submitted");
        reference.child(active).child("users").child(useruuid.getUid()).child("displayname").setValue(useruuid.getEmail());


        finish();

        super.onPause();
    }
}