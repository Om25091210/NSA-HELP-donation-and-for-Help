package com.aryomtech.nsabilaspur;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class POLLS extends AppCompatActivity {

    DatabaseReference ref;
    FirebaseDatabase database;

    RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_o_l_l_s);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("POLLS");

        Window window = POLLS.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(POLLS.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(POLLS.this, R.color.statusbar));

        recyclerView = findViewById(R.id.polls_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

    }
    @Override
    public void onBackPressed() {

        finish();

        super.onBackPressed();

    }
    @Override
    protected void onStart() {
        super.onStart();

        Query order=ref.orderByChild("key");
        FirebaseRecyclerAdapter<pollsData, pollsHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<pollsData, pollsHolder>(
                        pollsData.class,
                        R.layout.poll_card_design,
                        pollsHolder.class,
                        order
                ) {
                    @Override
                    protected void populateViewHolder(pollsHolder pollsHolder, pollsData pollsData, int i) {

                        pollsHolder.setView(POLLS.this,pollsData.getPolls_pic(),pollsData.getQuestion(),pollsData.getKey(),pollsData.getOption1(),pollsData.getOption2(),pollsData.getOption3(),pollsData.getOption4(),pollsData.getOption5(),pollsData.getOption6(),pollsData.getTotalquestion());

                    }
                };

        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }


}