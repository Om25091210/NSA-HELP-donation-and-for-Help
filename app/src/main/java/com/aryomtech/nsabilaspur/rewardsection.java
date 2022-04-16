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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class rewardsection extends AppCompatActivity {


    DatabaseReference ref;
    FirebaseDatabase database;
    List<Uri> sliderItemsRewards;
    RecyclerView recyclerView;
    boolean once=true;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewardsection);

        Intent f1 = getIntent();

        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("rewards");
        sliderItemsRewards= new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerviewreward);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        ref.child("data").child("status").setValue("notconfirm");

        Query query=ref.child("data");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && once) {
                    int total=dataSnapshot.child("total").getValue(int.class);
                    for(int i=1;i<=total;i++) {

                        String img1 = dataSnapshot.child("images").child(i + "").getValue(String.class);
                        sliderItemsRewards.add(Uri.parse(img1));
                        recyclerView.setAdapter(new SliderRewardsAdapter(getApplicationContext(),sliderItemsRewards));

                    }
                    once=false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Window window = rewardsection.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(rewardsection.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(rewardsection.this, R.color.statusbar));


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {

        Intent gotohome=new Intent(rewardsection.this,badgesection.class);
        startActivity(gotohome);
        super.onBackPressed();
    }
}