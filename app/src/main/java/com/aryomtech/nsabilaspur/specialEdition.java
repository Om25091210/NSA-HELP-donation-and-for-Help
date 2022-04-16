package com.aryomtech.nsabilaspur;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
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

import java.util.ArrayList;

public class specialEdition extends AppCompatActivity {

    DatabaseReference ref;
    FirebaseDatabase database;

    String userindividualkey;
    specialHolder specialHolder;

    RecyclerView recyclerView;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_edition);

        Intent f1 = getIntent();

        ArrayList<String> listofspecialbadges = (ArrayList<String>) getIntent().getSerializableExtra("sendinglistofspecialbadges");


        userindividualkey = getSharedPreferences("PREFERENCE1", MODE_PRIVATE)
                .getString("initialize","NO");

        Window window = specialEdition.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(specialEdition.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(specialEdition.this, R.color.statusbar));

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("DonorsData");

        recyclerView = findViewById(R.id.recyclerviewspecialedition);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        specialHolder = new specialHolder(getApplicationContext(),listofspecialbadges);
        recyclerView.setAdapter(specialHolder);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {

        Intent gotohome=new Intent(specialEdition.this,badgesection.class);
        startActivity(gotohome);
        super.onBackPressed();
    }
}