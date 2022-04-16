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

public class RequestHelp extends AppCompatActivity{

    private BottomNavigationView bottomNavigationView;
    DatabaseReference reflink;
    FirebaseDatabase database;
    String getlink,sharecountpreference;
    int storesharecount=0;
    public static final int REQUEST_CODE = 1;

    boolean connected = false;

    RecyclerView recyclerviewdonation;
    FirebaseDatabase databaseDonation;
    DatabaseReference referenceDonation;
    String IndividualsKey;
    FirebaseAuth auth;
    FirebaseUser useruuid;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_help);
        auth=FirebaseAuth.getInstance();
        useruuid=auth.getCurrentUser();

        Intent main2=getIntent();

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
                Toast.makeText(RequestHelp.this, "No NetWork Connection Found!!", Toast.LENGTH_SHORT).show();
            }
        }

        Window window = RequestHelp.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(RequestHelp.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(RequestHelp.this, R.color.statusbar));

        databaseDonation=FirebaseDatabase.getInstance();
        referenceDonation=databaseDonation.getReference().child("DonorsData");

        recyclerviewdonation= findViewById(R.id.recyclerrequesthelp);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(false);
        layoutManager.setStackFromEnd(false);
        recyclerviewdonation.setLayoutManager(layoutManager);

        IndividualsKey = getSharedPreferences("PREFERENCE1", MODE_PRIVATE)
                .getString("initialize","NO");


    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            Intent gotohome=new Intent(RequestHelp.this,ProfileActivity.class);
            startActivity(gotohome);
            finish();
            super.onBackPressed();
        }


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

    @Override
    protected void onStart() {
        super.onStart();

        Query descendingorder=referenceDonation.child(useruuid.getUid()).child("ConfirmedHELP").orderByChild("key");

        FirebaseRecyclerAdapter<User,HolderRequestHelp> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<User, HolderRequestHelp>(
                        User.class,
                        R.layout.contentofrequesthelp,
                        HolderRequestHelp.class,
                        descendingorder
                ) {
                    @Override
                    protected void populateViewHolder(HolderRequestHelp HolderRequestHelp, User user, int i) {
                        HolderRequestHelp.setView(RequestHelp.this,user.getCategory(),user.getName(),user.getLoc(),user.getPhone(),useruuid.getUid(),user.getUniqueid(),user.getPic1(),user.getPic2(),user.getPic3(),user.getPic4(),user.getDescription(),user.getDone(),user.getTime());
                    }

                };

        recyclerviewdonation.setAdapter(firebaseRecyclerAdapter);
    }

    public void bck(View view) {
        Intent gotohome=new Intent(RequestHelp.this,ProfileActivity.class);
        startActivity(gotohome);
        finish();
    }
}