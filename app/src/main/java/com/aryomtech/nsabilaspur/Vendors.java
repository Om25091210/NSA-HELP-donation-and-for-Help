package com.aryomtech.nsabilaspur;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Vendors extends AppCompatActivity {

    DatabaseReference ref;
    FirebaseDatabase database;
    RecyclerView recyclerView;
    View searchtoactivity;
    EditText editsearch;
    ImageView nsalogo;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendors);

        Intent addcard=getIntent();

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Vendors");

        searchtoactivity=findViewById(R.id.view18);
        editsearch=findViewById(R.id.searchtoactivity);
        editsearch.setEnabled(false);
        searchtoactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search=new Intent(Vendors.this,searchActivity.class);
                startActivity(search);
            }
        });
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        nsalogo=findViewById(R.id.imageView33);
        nsalogo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                String url = "https://www.nsahelp.in/";
                Uri uriUrl = Uri.parse(url);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);

                return false;
            }
        });
        Window window = Vendors.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(Vendors.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(Vendors.this, R.color.statusbar));
    }

    public void addcard(View view) {
        Intent setcardactivity=new Intent(Vendors.this,Setcard.class);
        startActivity(setcardactivity);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {

        finish();

        super.onBackPressed();

    }
    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerAdapter<User, vendorHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<User, vendorHolder>(
                        User.class,
                        R.layout.contentofvendor,
                        vendorHolder.class,
                        ref
                ) {
                    @Override
                    protected void populateViewHolder(vendorHolder vendorHolder, User user, int i) {

                        vendorHolder.setView(Vendors.this,user.getDesciptionshop(),user.getShopcategory(),user.getAddressshop(),user.getPhoto(),user.getLatitude(),user.getLongitude(),user.getListername());

                    }
                };

        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    public void bck(View view) {
        finish();
    }
}