package com.aryomtech.nsabilaspur;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Timeline extends AppCompatActivity {

    DatabaseReference ref;
    FirebaseDatabase database;
    RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Intent getdatafromtime=getIntent();

        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("Timeline");

        recyclerView = findViewById(R.id.recyclerviewtimeline);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        Window window = Timeline.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(Timeline.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(Timeline.this, R.color.statusbar));


    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<User, HolderTimeLine> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<User, HolderTimeLine>(
                        User.class,
                        R.layout.content_of_timeline,
                        HolderTimeLine.class,
                        ref
                ) {
                    @Override
                    protected void populateViewHolder(HolderTimeLine HolderTimeLine, User user, int i) {

                        HolderTimeLine.setView(Timeline.this,user.getRespondedtype(),user.getRespondedby(),user.getRespondeddp(),user.getRespondedto(),user.getTime());

                    }
                };

        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {

            Intent gotohome=new Intent(Timeline.this,MainActivity2.class);
            startActivity(gotohome);
            super.onBackPressed();
    }
}