package com.aryomtech.nsabilaspur;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class earnedRewards extends AppCompatActivity {

    DatabaseReference ref;
    FirebaseDatabase database;
    List<Uri> sliderItemsearnedrewards;
    boolean once=true;

    String userindividualkey;
    RecyclerView recyclerView;
    FirebaseAuth auth;
    FirebaseUser useruuid;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earned_rewards);

        auth=FirebaseAuth.getInstance();
        useruuid=auth.getCurrentUser();

        Window window = earnedRewards.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(earnedRewards.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(earnedRewards.this, R.color.statusbar));

        Intent earn=getIntent();

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("DonorsData");
        sliderItemsearnedrewards= new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerviewearnedreward);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        ref.child(useruuid.getUid()).child("EarnedRewards").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && once) {
                    int total=dataSnapshot.child("total").getValue(int.class);
                    for(int i=1;i<=total;i++) {

                        String img1 = dataSnapshot.child("images").child(i + "").getValue(String.class);
                        sliderItemsearnedrewards.add(Uri.parse(img1));
                        recyclerView.setAdapter(new SliderRewardsAdapter(getApplicationContext(),sliderItemsearnedrewards));

                    }
                    once=false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {

        Intent gotohome=new Intent(earnedRewards.this,badgesection.class);
        startActivity(gotohome);
        super.onBackPressed();
    }

}