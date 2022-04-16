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

public class Upcoming extends AppCompatActivity {

    DatabaseReference ref;
    FirebaseDatabase database;
    public static Activity quiz;
    RecyclerView recyclerView;
    String active="";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("UpcomingQuiz");

        Window window = Upcoming.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(Upcoming.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(Upcoming.this, R.color.statusbar));

        recyclerView = findViewById(R.id.rv_upcoming);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Upcoming.this);
        layoutManager.setReverseLayout(false);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<upcomingeventsData, UpcomingHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<upcomingeventsData, UpcomingHolder>(
                        upcomingeventsData.class,
                        R.layout.upcoming_card_holder,
                        UpcomingHolder.class,
                        ref
                ) {
                    @Override
                    protected void populateViewHolder(UpcomingHolder UpcomingHolder, upcomingeventsData upcomingeventsData, int i) {

                        UpcomingHolder.setView(Upcoming.this,upcomingeventsData.getCardphoto(),upcomingeventsData.getDate(),active,upcomingeventsData.getQuizname(),upcomingeventsData.getStatus(),upcomingeventsData.getColor());

                    }
                };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

}