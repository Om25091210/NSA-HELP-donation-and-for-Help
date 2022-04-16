package com.aryomtech.nsabilaspur;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Results extends AppCompatActivity {

    DatabaseReference ref;
    FirebaseDatabase database;
    public static Activity quiz;
    RecyclerView recyclerView;
    String active="";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("SecondaryQuiz");

        active=getIntent().getStringExtra("sendingCurrentQuiz");


        Window window = Results.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(Results.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(Results.this, R.color.statusbar));

        recyclerView = findViewById(R.id.rv_resuls);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Results.this);
        layoutManager.setReverseLayout(false);
        recyclerView.setLayoutManager(layoutManager);


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<ResultsQuizCardData, ResultsHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ResultsQuizCardData, ResultsHolder>(
                        ResultsQuizCardData.class,
                        R.layout.results_card_holder,
                        ResultsHolder.class,
                        ref
                ) {
                    @Override
                    protected void populateViewHolder(ResultsHolder ResultsHolder, ResultsQuizCardData ResultsQuizCardData, int i) {

                        ResultsHolder.setView(Results.this,ResultsQuizCardData.getCardphoto(),ResultsQuizCardData.getDate(),active,ResultsQuizCardData.getQuizname(),ResultsQuizCardData.getStatus(),ResultsQuizCardData.getColor());

                    }
                };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}