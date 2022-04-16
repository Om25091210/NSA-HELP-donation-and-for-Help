package com.aryomtech.nsabilaspur;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PickUpDetails extends AppCompatActivity{

    private BottomNavigationView bottomNavigationView;
    DatabaseReference reflink;
    FirebaseDatabase database;
    String getlink,sharecountpreference;
    public static final int REQUEST_CODE = 1;

    int storesharecount=0;
    Button cancel;
    String getNAME;

    boolean connected = false;

    FirebaseDatabase databasep;
    DatabaseReference refp,resusers;
    RecyclerView recyclerView;
    FirebaseUser user;
    private FirebaseAuth mAuth;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up_details);


        Window window = PickUpDetails.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(PickUpDetails.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(PickUpDetails.this, R.color.statusbar));

        if(!isNetworkAvailable()){
            try {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();

                alertDialog.setTitle("Info");
                alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setCancelable(false);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(!isNetworkAvailable()){
                            alertDialog.dismiss();
                        }
                        else{
                            alertDialog.dismiss();
                        }

                    }
                });

                alertDialog.show();
            } catch (Exception e) {
                Toast.makeText(PickUpDetails.this, "No NetWork Connection Found!!", Toast.LENGTH_SHORT).show();
            }
        }

        mAuth=FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        Intent pickdetails=getIntent();
        getNAME=pickdetails.getStringExtra("sendNAME");

        databasep=FirebaseDatabase.getInstance();
        refp=databasep.getReference().child("Users");


        recyclerView= findViewById(R.id.recyclerpickup);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);


    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            Intent gotohome=new Intent(PickUpDetails.this,ProfileActivity.class);
            startActivity(gotohome);
            finish();
            super.onBackPressed();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        Query descendingorder=refp.child(user.getUid()).child(user.getDisplayName()).child("ConfirmId").orderByChild("key");

        FirebaseRecyclerAdapter<User,HolderPickup> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<User, HolderPickup>(
                        User.class,
                        R.layout.contentofmypickup,
                        HolderPickup.class,
                        descendingorder
                ) {
                    @Override
                    protected void populateViewHolder(HolderPickup holderPickup, User user, int i) {
                        holderPickup.setView(PickUpDetails.this,user.getCategory(),user.getName(),user.getLoc(),user.getPhone(),getNAME,user.getUniqueid(),user.getDescription(),user.getType(),user.getLatitude(),user.getLongitude(),user.getDone(),user.getTime());
                    }

                };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
            return connected;
        }
        else {
            connected = false;
            return connected;
        }
    }


    public void bck(View view) {
        Intent gotohome=new Intent(PickUpDetails.this,ProfileActivity.class);
        startActivity(gotohome);
        finish();
    }
}