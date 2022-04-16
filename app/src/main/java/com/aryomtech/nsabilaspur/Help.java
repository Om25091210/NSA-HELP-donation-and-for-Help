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
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class Help extends AppCompatActivity {

    DatabaseReference ref,reflink;
    FirebaseDatabase database;
    String getlink;

    private BottomNavigationView bottomNavigationView;

    RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    String IndividualsKey,sharecountpreference;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Intent f1 = getIntent();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser userauth = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        ref = database.getReference("VerifiedNGOs");

        Window window = Help.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(Help.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(Help.this, R.color.statusbar));


        recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        IndividualsKey = getSharedPreferences("PREFERENCE1", MODE_PRIVATE)
                .getString("initialize", "NO");


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
        FirebaseRecyclerAdapter<User, authHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<User, authHolder>(
                        User.class,
                        R.layout.contentofhelp,
                        authHolder.class,
                        order
                ) {
                    @Override
                    protected void populateViewHolder(authHolder authholder, User user, int i) {

                        authholder.setView(Help.this, user.getEmailofngo(), user.getNameofngo(),user.getUid(),user.getRespondeddp(),user.getImages(),user.getCategory_of_ngo());

                    }
                };

        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }


}