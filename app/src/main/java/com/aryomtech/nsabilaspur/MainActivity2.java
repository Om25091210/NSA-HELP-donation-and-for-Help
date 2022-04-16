package com.aryomtech.nsabilaspur;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.BuildConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

public class MainActivity2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;

    DatabaseReference ref, reflink, deleteeventdata, referenceforplay,verifiedngoreference,userReference,Maintenanceref;
    FirebaseDatabase database;
    String getlink;
    String createdatanode;

    boolean ekbar=true;

    Boolean verify;
    boolean connected = false;
    int storesharecount = 0;

    public static final int REQUEST_CODE = 1;

    ViewPager2 viewPager2;
    Dialog dialogAnimated;
    boolean once=true;
    List<Uri> sliderItems;
    String badgename;
    ImageView dropmeter;
    ImageView scan;
    int total_responds=0;
    ImageView report;
    List<String> SliderItemlinks;
    List<String> SliderItemTexts;
    public static Activity maintwo;
    private Handler sliderHandler=new Handler();

    int runanim = 0;
    int checkforlevelshowing = 0;

    int ek_bhi_nhi=0;
    FirebaseAuth auth;
    FirebaseUser useruuid;

    ImageView xpup;
    Boolean islogin;
    ImageView timeline;
    Animation anim, animlevel, animlevelback;
    Animation animzoom;

    ImageView levelincrease, congrats;

    String fetchno, fetchname, id;
    String showgamemsg;
    String confirmrespondxp;
    LinearLayoutManager lm;
    String checkcongrats;
    String levelup, sharecountpreference;
    RecyclerView recyclerView;
    public static final int REQUEST_GALLERY_IMAGE = 1;
    public static final int REQUEST_IMAGE_CAPTURE = 0;
    String receive_name;
    String getIdentity;
    String IndividualsKey;
    String emailverified;

    String satisfactionxp="";
    View view2;
    String comfirmedvariables;
    View view;
    String Type;
    ImageView nsalogo;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigationMyProfile:
                    Intent profile = new Intent(MainActivity2.this, ProfileActivity.class);
                    startActivity(profile);
                    // overridePendingTransition(R.anim.fadein,R.anim.anim);
                    finish();
                    return true;
                case R.id.navigationMyCourses:
                    Intent help = new Intent(MainActivity2.this, HELPsection.class);
                    startActivity(help);
                    //overridePendingTransition(R.anim.leftanim,R.anim.anim);
                    finish();
                    return true;
                case R.id.navigationHome:
                    return true;
                case R.id.navigationSearch:
                    Intent donate = new Intent(MainActivity2.this, Donate.class);
                    startActivity(donate);
                    // overridePendingTransition(R.anim.fadein,R.anim.anim);
                    finish();
                    return true;
                case R.id.navigationMenu:
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.openDrawer(GravityCompat.START);
                    return true;
            }
            return false;
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        useruuid = auth.getCurrentUser();

        maintwo = this;

        Maintenanceref=FirebaseDatabase.getInstance().getReference().child("Maintenance");
        Maintenanceref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String check_for_maintenance=snapshot.child("isON").getValue(String.class);
                String devuid=snapshot.child("developeruuid").getValue(String.class);

                if(check_for_maintenance.equals("no")){
                    //
                }
                else if(useruuid.getUid().equals(devuid)){
                    Snackbar.make(findViewById(android.R.id.content),"Developer Mode ON. Welcome,Sir....Fix the issues please :)",Snackbar.LENGTH_LONG).show();
                }
                else{
                    Intent maintain=new Intent(MainActivity2.this,Maintenance.class);
                    maintain.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(maintain);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        String oncelogin = getSharedPreferences("onceforloginandcheck", MODE_PRIVATE)
                .getString("showonlyonceinfirstlogin", "true");

        islogin = getSharedPreferences("isloginornot", MODE_PRIVATE)
                .getBoolean("islogin", false);

        if (!islogin && oncelogin.equals("true")) {   // condition true means user is already login
            Intent i = new Intent(MainActivity2.this, Login.class);
            startActivity(i);
            finish();
        } else {
            getSharedPreferences("onceforloginandcheck", MODE_PRIVATE).edit()
                    .putString("showonlyonceinfirstlogin", "false").apply();
        }

//TODO : before final testing make sure that the reference of storage are exactly located in your firebase.just create the storage folders and files:).

        boolean isFirstopen = getSharedPreferences("Openonce", MODE_PRIVATE)
                .getBoolean("isFirstopen", false);
        if (isFirstopen) {

            Toast.makeText(this, "Loading!!", Toast.LENGTH_SHORT).show();

            getSharedPreferences("Openonce", MODE_PRIVATE).edit()
                    .putBoolean("isFirstopen", false).apply();
        }


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
                Toast.makeText(MainActivity2.this, "No NetWork Connection Found!!", Toast.LENGTH_SHORT).show();
            }
        }

        Window window = MainActivity2.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(MainActivity2.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(MainActivity2.this, R.color.statusbar));


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        setContentView(R.layout.activity_main2);
        Intent f1 = getIntent();

        if (ContextCompat.checkSelfPermission(MainActivity2.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity2.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Dexter.withActivity(MainActivity2.this)
                    .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {

                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).check();
        }

        if (ContextCompat.checkSelfPermission(MainActivity2.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity2.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }
//=========================================================================================================================================

//=========================================================================================================================================
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        viewPager2=findViewById(R.id.viewPager2);
        database=FirebaseDatabase.getInstance();
        ref=database.getReference().child("viewimage");
        sliderItems= new ArrayList<>();
        SliderItemlinks=new ArrayList<>();
        SliderItemTexts=new ArrayList<>();

        ref.child("data").child("status").setValue("notconfirm");
        Query queryd=ref.child("data");
        queryd.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshotd) {
                if(dataSnapshotd.exists() && once) {
                    int total=dataSnapshotd.child("total").getValue(int.class);
                    for(int i=1;i<=total;i++) {

                        String img1 = dataSnapshotd.child("images").child(i + "").getValue(String.class);
                        String link1=dataSnapshotd.child("url").child(i+"").getValue(String.class);
                        String texts=dataSnapshotd.child("texts").child(i+"").getValue(String.class);

                        SliderItemlinks.add(link1);
                        sliderItems.add(Uri.parse(img1));
                        SliderItemTexts.add(texts);

                        viewPager2.setAdapter(new SliderAdapter(getApplicationContext(),total,sliderItems,SliderItemlinks,SliderItemTexts,viewPager2));
                    }
                    once=false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r=1- Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable,2800); //Slide duration 2 seconds
            }
        });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        view = findViewById(R.id.view11);
        view.setVisibility(View.GONE);

        view2 = findViewById(R.id.view16);
        view2.setVisibility(View.GONE);

        congrats = findViewById(R.id.congrats);
        congrats.setVisibility(View.GONE);

        levelincrease = findViewById(R.id.levelincrease);
        levelincrease.setVisibility(View.GONE);

        receive_name = f1.getStringExtra("send_name");
        getIdentity = f1.getStringExtra("sendingIdentity");
        Type = f1.getStringExtra("sendingTYPE");


        fetchno = f1.getStringExtra("key1");
        fetchname = f1.getStringExtra("key2");
        id = f1.getStringExtra("key3");

        showgamemsg = f1.getStringExtra("animatingXP");
        satisfactionxp=f1.getStringExtra("sendingTYPEsatisfaction");

        xpup = findViewById(R.id.xpup);
        xpup.setVisibility(View.GONE);
        levelup = f1.getStringExtra("animatinglevel");

        nsalogo=findViewById(R.id.imageView34);
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


        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_bottom_to_top);
        animlevel = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_come);
        animlevelback = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_back);

        dropmeter=findViewById(R.id.dropmeter);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Donors");
//==========------------=======================------------------========================---------------------=========================---------------------=================

        scan=findViewById(R.id.imageView9);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this,ScanQr.class));
            }
        });

        dropmeter.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                dialogAnimated=new Dialog(MainActivity2.this,R.style.dialogstylemeter);
                dialogAnimated.setContentView(R.layout.meterdialog);
                dialogAnimated.show();

                TextView totalhelp=(TextView)dialogAnimated.findViewById(R.id.totalhelp);
                TextView totaldonation=(TextView)dialogAnimated.findViewById(R.id.totaldonations);

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int donation_count=0;
                        int help_count=0;
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            String count = snapshot.child(snap.getKey()).child("type").getValue(String.class);
                            if (count.equals("DONATION")) {
                                donation_count++;

                            } else if (count.equals("HELP")) {
                                help_count++;

                            }
                        }

                        totalhelp.setText(help_count+"");
                        totaldonation.setText(donation_count+"");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



                TextView txtclose=(TextView)dialogAnimated.findViewById(R.id.txtclose);
                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogAnimated.dismiss();
                    }
                });
            }
        });
//==========--------------------------===========================================------------------------================================---------------------------------======
        recyclerView = findViewById(R.id.recyclerview);
        /*recyclerView.setHasFixedSize(true);
        lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false); // last argument (true) is flag for reverse layout
        recyclerView.setLayoutManager(lm);*/
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);
       /* recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    Toast.makeText(MainActivity2.this, "Last", Toast.LENGTH_LONG).show();

                }
            }
        });*/

        /*RecyclerView.SmoothScroller smoothScroller = new
                LinearSmoothScroller(this) {
                    @Override protected int getVerticalSnapPreference() {
                        return LinearSmoothScroller.SNAP_TO_END;
                    }
                };
        smoothScroller.setTargetPosition(36);
        lm.startSmoothScroll(smoothScroller);*/



        IndividualsKey = getSharedPreferences("PREFERENCE1", MODE_PRIVATE)
                .getString("initialize", "NO");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        bottomNavigationView.setSelectedItemId(R.id.navigationHome);

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                bottomNavigationView.setSelectedItemId(R.id.navigationHome);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        database = FirebaseDatabase.getInstance();
        deleteeventdata = database.getReference("DonorsData");


        createdatanode = getSharedPreferences("checkingdatecurrent", MODE_PRIVATE)
                .getString("currentdate", "Null");

        if (createdatanode.equals("false")) {
            deleteeventdata.child(useruuid.getUid()).child("EventDonation").removeValue();
        }

/////////////////////---------------------------------------------------/////////

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                congrats.setVisibility(View.GONE);
                view.setVisibility(View.GONE);

                getSharedPreferences("showlevelanimationuserhasdonated", MODE_PRIVATE).edit()
                        .putString("userhasdonated", "false").apply();

                MediaPlayer mp1 = MediaPlayer.create(MainActivity2.this, R.raw.xpsound);
                mp1.start();
                levelincrease.setVisibility(View.VISIBLE);
                animlevel.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        view2.setVisibility(View.VISIBLE);
                        view2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                levelincrease.setVisibility(View.GONE);
                                view2.setVisibility(View.GONE);
                                checkforlevelshowing = 1;

                            }
                        });

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                levelincrease.startAnimation(animlevel);

            }
        });

        checkshowing();
        String controlingAnimation_on_theme_change=getSharedPreferences("contolling_Animation_on_recreate", MODE_PRIVATE)
                .getString("Animation_on_create", "false");
        try {
            if (checkforlevelshowing != 1 && Type.equals("DONATION") && controlingAnimation_on_theme_change.equals("true") || satisfactionxp.equals("true") && controlingAnimation_on_theme_change.equals("true") ) {

                getSharedPreferences("contolling_Animation_on_recreate", MODE_PRIVATE).edit()
                        .putString("Animation_on_create", "false").apply();

                callxplevelanimation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        database= FirebaseDatabase.getInstance();
        userReference=database.getReference().child("Accounts");

        database = FirebaseDatabase.getInstance();
        verifiedngoreference=database.getReference().child("VerifiedNGOs");
        verifiedngoreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snap:snapshot.getChildren()){
                    if(snap.getKey().equals(useruuid.getUid())){
                        if(snapshot.child(useruuid.getUid()).child("emailOfNGO").getValue(String.class).equals(useruuid.getEmail())) {
                            verify = true;
                            ek_bhi_nhi=1;
                            String uidverifiedcheck = getSharedPreferences("PREFERENCEuidverified456", MODE_PRIVATE)
                                    .getString("uidverified", "null");
                            if (!uidverifiedcheck.equals("null")) {

                                System.out.println("Entered nonduplicate");

                                if (uidverifiedcheck.equals(useruuid.getUid())) {
                                    getSharedPreferences("PREFERENCE2", MODE_PRIVATE).edit()
                                            .putBoolean("verify", verify).apply();

                                    getSharedPreferences("PREFERENCEemail", MODE_PRIVATE).edit()
                                            .putString("emailverified", useruuid.getEmail()).apply();

                                    String DeviceToken = FirebaseInstanceId.getInstance().getToken();
                                    userReference.child(useruuid.getUid()).child("device_token").setValue(DeviceToken);

                                    String org = snapshot.child(useruuid.getUid()).child("organization").getValue(String.class);
                                    userReference.child(useruuid.getUid()).child("organization").setValue(org);

                                    getSharedPreferences("PREFERENCEuidverified456", MODE_PRIVATE).edit()
                                            .putString("uidverified", useruuid.getUid()).apply();

                                    userReference.child(useruuid.getUid()).child("toSend").setValue("YES");
                                } else {
                                    Snackbar.make(findViewById(android.R.id.content), "This Device has been registered with another email.", Snackbar.LENGTH_LONG).show();
                                    verify = false;
                                    getSharedPreferences("PREFERENCE2", MODE_PRIVATE).edit()
                                            .putBoolean("verify", verify).apply();
                                }

                            }
                            else{
                                System.out.println("Entered once");
                                getSharedPreferences("PREFERENCE2", MODE_PRIVATE).edit()
                                        .putBoolean("verify", verify).apply();

                                getSharedPreferences("PREFERENCEemail", MODE_PRIVATE).edit()
                                        .putString("emailverified", useruuid.getEmail()).apply();

                                String DeviceToken = FirebaseInstanceId.getInstance().getToken();
                                userReference.child(useruuid.getUid()).child("device_token").setValue(DeviceToken);

                                String org = snapshot.child(useruuid.getUid()).child("organization").getValue(String.class);
                                userReference.child(useruuid.getUid()).child("organization").setValue(org);

                                getSharedPreferences("PREFERENCEuidverified456", MODE_PRIVATE).edit()
                                        .putString("uidverified", useruuid.getUid()).apply();

                                userReference.child(useruuid.getUid()).child("toSend").setValue("YES");
                            }
                        }
                    }
                }
                if(ek_bhi_nhi==0) {
                    String uidverifieddelete = getSharedPreferences("PREFERENCEuidverified456", MODE_PRIVATE)
                            .getString("uidverified", "null");
                    if (!uidverifieddelete.equals("null")) {
                        System.out.println("Entered no");
                        userReference.child(uidverifieddelete).child("toSend").setValue("NO");
                        verify = false;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void checkshowing() {

        String showlevel = getSharedPreferences("showlevelanimationuserhasdonated", MODE_PRIVATE)
                .getString("userhasdonated", "false");


        if (showlevel.equals("true")) {

            database = FirebaseDatabase.getInstance();
            referenceforplay = database.getReference().child("DonorsData");

            referenceforplay.child(useruuid.getUid()).child("status").setValue("active");
            Query query = referenceforplay.child(useruuid.getUid());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int total_value_of_donation = (int) dataSnapshot.child("ConfirmDonation").getChildrenCount();

                    if(dataSnapshot.child("playData").child("Responds").exists()) {
                        total_responds = (int) dataSnapshot.child("playData").child("Responds").getChildrenCount();

                    }

                    int storechecklevel=0;
                    int storetotallevel=0;
                    if(total_responds==0){
                        storechecklevel=total_value_of_donation *50 % 100;
                        storetotallevel=total_value_of_donation *50 / 100;
                    }
                    else{
                        storechecklevel=(total_value_of_donation *50+total_responds*50) % 100;
                        storetotallevel=(total_value_of_donation *50+total_responds*50) / 100;

                    }
                    if (storechecklevel == 0) {

                        if (storetotallevel == 1) {
                            congrats.setVisibility(View.VISIBLE);
                            view.setVisibility(View.VISIBLE);
                            MediaPlayer mp = MediaPlayer.create(MainActivity2.this, R.raw.xpsound);
                            mp.start();
                            runanim = 1;

                        } else if (storetotallevel == 2) {
                            congrats.setVisibility(View.VISIBLE);
                            view.setVisibility(View.VISIBLE);
                            MediaPlayer mp = MediaPlayer.create(MainActivity2.this, R.raw.xpsound);
                            mp.start();
                            runanim = 1;

                        } else if (storetotallevel % 5 == 0 && storetotallevel != 0) {

                            congrats.setVisibility(View.VISIBLE);
                            view.setVisibility(View.VISIBLE);
                            MediaPlayer mp = MediaPlayer.create(MainActivity2.this, R.raw.xpsound);
                            mp.start();
                            runanim = 1;

                        }
                        if (runanim != 1) {
                            MediaPlayer mp1 = MediaPlayer.create(MainActivity2.this, R.raw.xpsound);
                            mp1.start();

                            getSharedPreferences("showlevelanimationuserhasdonated", MODE_PRIVATE).edit()
                                    .putString("userhasdonated", "false").apply();

                            levelincrease.setVisibility(View.VISIBLE);
                            animlevel.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    view2.setVisibility(View.VISIBLE);
                                    view2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            levelincrease.setVisibility(View.GONE);
                                            view2.setVisibility(View.GONE);
                                            checkforlevelshowing = 1;
                                        }
                                    });

                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            levelincrease.startAnimation(animlevel);

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void callxplevelanimation() {
        try {
            if (showgamemsg.equals("true")) {
                xpup.setVisibility(View.VISIBLE);
                MediaPlayer mp = MediaPlayer.create(this, R.raw.xpsound);
                mp.start();
                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        xpup.setVisibility(View.GONE);


                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                xpup.startAnimation(anim);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    //---------------------------------congratsanimation----------------------------------------------------------

//-------------------------------------------------------------------------------------------------------------------

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_faq) {
            database = FirebaseDatabase.getInstance();
            reflink = database.getReference("link");


            reflink.child("app").child("trigger").setValue("yes");
            Query querylink = reflink.child("app");
            querylink.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshotapp) {
                    String linkfaq = dataSnapshotapp.child("nsafaq").getValue(String.class);
                    String url = linkfaq;
                    Uri uriUrl = Uri.parse(url);
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else if(id==R.id.nav_timeline){

            Intent timeline=new Intent(MainActivity2.this,Timeline.class);
            startActivity(timeline);
            finish();
        }
        else if (id == R.id.nav_share) {

            sharecountpreference = getSharedPreferences("storesharecount", MODE_PRIVATE)
                    .getString("sharecount", "0");

            database = FirebaseDatabase.getInstance();
            reflink = database.getReference("link");

            storesharecount = Integer.parseInt(sharecountpreference) + 1;

            reflink.child("app").child("trigger").setValue("yes");
            Query querylink = reflink.child("app");
            querylink.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshotapp) {
                    getlink = dataSnapshotapp.child("nsa").getValue(String.class);


                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "NSA HELP");
                    String shareMessage = getlink;
                    intent.putExtra(Intent.EXTRA_TEXT, shareMessage);

                    startActivityForResult(Intent.createChooser(intent, "Share by:"),REQUEST_CODE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }
        else if (id == R.id.nav_aryom) {
            database = FirebaseDatabase.getInstance();
            reflink = database.getReference("link");

            reflink.child("app").child("trigger").setValue("yes");
            Query querylink = reflink.child("app");
            querylink.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshotapp) {
                    String aboutlink = dataSnapshotapp.child("aboutdevlink").getValue(String.class);
                    String url = aboutlink;
                    Uri uriUrl = Uri.parse(url);
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else if (id == R.id.nav_tc) {

            String url = "https://termsandconditionsnsahelp.blogspot.com/2020/10/terms-conditions-body-font-family.html"; //
            Uri uriUrl = Uri.parse(url);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);

        } else if (id == R.id.nav_privacy) {

            String url = "https://privacypolicynsahelp.blogspot.com/2020/10/privacy-policy-for-nsa-help.html"; //
            Uri uriUrl = Uri.parse(url);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);

        }
        else if(id==R.id.nav_reg){

            database = FirebaseDatabase.getInstance();
            reflink = database.getReference("link");

            reflink.child("app").child("trigger").setValue("yes");
            Query querylink = reflink.child("app");
            querylink.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshotapp) {
                    String aboutlink = dataSnapshotapp.child("registeredNGO").getValue(String.class);
                    String url = aboutlink;
                    Uri uriUrl = Uri.parse(url);
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else if (id == R.id.nav_aboutus) {
            String url = "https://www.nsahelp.in/";
            Uri uriUrl = Uri.parse(url);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
        } /*else if (id == R.id.nav_dark_mode) {
            //code for setting dark mode
            //true for dark mode, false for day mode, currently toggling on each click
            String getmode=getSharedPreferences("dark_and_light_mode",MODE_PRIVATE)
                    .getString("lightMode","true");
            if(getmode.equals("true")){

                getSharedPreferences("dark_and_light_mode",MODE_PRIVATE).edit()
                        .putString("lightMode","false").apply();

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                recreate();
            }
            else if(getmode.equals("false")){

                getSharedPreferences("dark_and_light_mode",MODE_PRIVATE).edit()
                        .putString("lightMode","true").apply();

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                recreate();
            }


        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<User, Holder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<User, Holder>(
                        User.class,
                        R.layout.layout_news,
                        Holder.class,
                        ref
                ) {
                    @Override
                    protected void populateViewHolder(Holder holder, User user, int i) {

                        holder.setView(MainActivity2.this, user.getLoc(), user.getCategory(), user.getTask(), user.getKey(), user.getUniqueid(), receive_name, getIdentity, user.getName(), user.getPhone(), useruuid.getUid(), user.getIndividualKey(), user.getTime(), user.getDevicetoken(), user.getPosted(), user.getCount(), user.getPic1(), user.getPic2(), user.getPic3(), user.getPic4(), user.getDp(), user.getDescription(), user.getType(),user.getLatitude(),user.getLongitude());

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

    public void badgesection(View view) {
        Intent thethreesection=new Intent(MainActivity2.this,THEthreeSECTIONS.class);
        startActivity(thethreesection);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

   private Runnable sliderRunnable=new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();

        sliderHandler.postDelayed(sliderRunnable,2800);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == REQUEST_CODE  && resultCode  == RESULT_OK) {

                getSharedPreferences("storesharecount", MODE_PRIVATE).edit()
                        .putString("sharecount", storesharecount + "").apply();
            }
        } catch (Exception ex) {
            Toast.makeText(MainActivity2.this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }

    }
}
