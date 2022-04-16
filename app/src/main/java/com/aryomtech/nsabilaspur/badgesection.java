package com.aryomtech.nsabilaspur;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class badgesection extends AppCompatActivity {

    private CircleImageView ProfileImage;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    String profilepath;
    ViewPager2 viewPager2;
    boolean connected = false;

    boolean once=true;
    List<Uri> sliderItems;
    String badgename;
    List<String> SliderItemlinks;
    String varlevelforinfo="";

    private SwipeRefreshLayout layoutRefresh;

    DatabaseReference ref;
    private Handler sliderHandler=new Handler();

    FirebaseAuth auth;
    FirebaseUser useruuid;

    ProgressBar seekBar;
    int total_responds=0;

    ImageView  levelbadgeone,levelbadgetwo,levelbadgethree,levelbadgefour;

    FirebaseDatabase database;
    DatabaseReference reference,reflevelbadges,badgedata,checkforuserearnedrewards;

    ImageView one,two,five,ten,fifteen,sharebadges;
    String store_event_donation_number;
    ImageView lockedrewards,earnedrewards;
    String date,time;

    ImageView earnedbadges,seeall;
    List<String> pushkeysEvent;
    ImageView seealllimited;

    TextView totaldonations,increase,totalxp,totalbadges,maxXp,XP;
    String userindividualkey;
    String sharecountpreference;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badgesection);

        Intent getinfo=getIntent();

        seekBar = findViewById(R.id.seekbar);
        seekBar.setMax(100);

        auth=FirebaseAuth.getInstance();
        useruuid=auth.getCurrentUser();

        pushkeysEvent=new ArrayList<String>();
        userindividualkey = getSharedPreferences("PREFERENCE1", MODE_PRIVATE)
                .getString("initialize","NO");


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
                Toast.makeText(badgesection.this, "No NetWork Connection Found!!", Toast.LENGTH_SHORT).show();
            }
        }

        Window window =badgesection.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(badgesection.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(badgesection.this, R.color.statusbar));

        Calendar calobj = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        time=df.format(calobj.getTime());

        lockedrewards=findViewById(R.id.lockedrewards);
        earnedrewards=findViewById(R.id.earnedrewards);
        seealllimited=findViewById(R.id.imageView20);

        seealllimited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(pushkeysEvent.size() ==0)){
                    Intent limitedsellall=new Intent(badgesection.this,specialEdition.class);
                    limitedsellall.putExtra("sendinglistofspecialbadges", (Serializable) pushkeysEvent);
                    startActivity(limitedsellall);
                    finish();
                }
                else{
                    Snackbar.make(findViewById(android.R.id.content),"No Badges Yet!!",Snackbar.LENGTH_LONG).show();
                }

            }
        });
        lockedrewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locked=new Intent(badgesection.this,rewardsection.class);
                startActivity(locked);
                finish();
            }
        });
        earnedrewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                checkforuserearnedrewards=database.getReference("DonorsData");
                checkforuserearnedrewards.child(useruuid.getUid()).child("status").setValue("active");

                Query checknodeofearnedrewards=checkforuserearnedrewards.child(useruuid.getUid());
                checknodeofearnedrewards.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshotearnedrewards) {
                        if(snapshotearnedrewards.child("EarnedRewards").exists()){
                            Intent earned=new Intent(badgesection.this,earnedRewards.class);
                            startActivity(earned);
                            finish();
                        }
                        else{
                            Snackbar.make(findViewById(android.R.id.content),"You Have Not Earned Any Rewards!!",Snackbar.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        seekBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });//for not having seekbar touchable;
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ProfileImage = (CircleImageView) findViewById(R.id.profile_image);
        retrievingPhotofromdatabase();
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        date = getSharedPreferences("Storefirstdata", MODE_PRIVATE)
                .getString("Storedata","Loading");

        TextView datetxt=findViewById(R.id.date);
        totaldonations=findViewById(R.id.totaldonations);
        totalbadges=findViewById(R.id.totalbadges);
        totalbadges.setText("0");
        seeall=findViewById(R.id.seeall);
        seeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent seebadges=new Intent(badgesection.this,seethebadges.class);
                seebadges.putExtra("sendinglevel",increase.getText().toString());
                seebadges.putExtra("sendinguserkey",useruuid.getUid());
                startActivity(seebadges);
                finish();
            }
        });
        earnedbadges=findViewById(R.id.earnedbadges);
        earnedbadges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent earned=new Intent(badgesection.this,earnedbadgeSection.class);
                startActivity(earned);
                finish();
            }
        });


        one=findViewById(R.id.firstbadge);
        two=findViewById(R.id.secondbadge);
        five=findViewById(R.id.fivebadge);
        ten=findViewById(R.id.tenbadge);
        fifteen=findViewById(R.id.fifteenbadge);

        levelbadgeone=findViewById(R.id.levelbadgeone);
        levelbadgetwo=findViewById(R.id.levelbadgetwo);
        levelbadgethree=findViewById(R.id.levelbadgethree);
        levelbadgefour=findViewById(R.id.levelbadgefour);


        XP=findViewById(R.id.XP);
        XP.setText("0");


        sharebadges=findViewById(R.id.sharebadges);

        sharecountpreference= getSharedPreferences("storesharecount", MODE_PRIVATE)
                .getString("sharecount", "0");
        int storesharecount= Integer.parseInt(sharecountpreference);


        maxXp=findViewById(R.id.maxXp);
        maxXp.setText("100");

        totalxp=findViewById(R.id.totalxp);
        totalxp.setText("0");
        increase=findViewById(R.id.increase);
        increase.setText("0");
        totaldonations.setText("0");
        datetxt.setText(date);

        layoutRefresh = this.findViewById(R.id.container);

        layoutRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Intent intent = getIntent();
                finish();
                startActivity(intent);
                layoutRefresh.setRefreshing(false);
            }
        });

        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("DonorsData");

        if(storesharecount==10){

            sharebadges.setImageResource(R.drawable.sharebadge);

            reference.child(useruuid.getUid()).child("playData").child("badges").child("sharebadge").setValue("Earned");

        }
        reference.child(useruuid.getUid()).child("status").setValue("active");

        Query query=reference.child(useruuid.getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    reference.child(useruuid.getUid()).child("playData").child("status").setValue("Confirm");

                    if(dataSnapshot.child("playData").child("StartDate").exists()){
                        String Startdate=dataSnapshot.child("playData").child("StartDate").getValue(String.class);
                        datetxt.setText(Startdate);
                    }
                    else{
                        reference.child(useruuid.getUid()).child("playData").child("StartDate").setValue(date);
                    }
                    if(dataSnapshot.child("playData").child("Responds").exists()) {
                        total_responds = (int) dataSnapshot.child("playData").child("Responds").getChildrenCount();
                    }
                    int total_value_of_donation= (int) dataSnapshot.child("ConfirmDonation").getChildrenCount();
                    if(dataSnapshot.child("EventDonation").exists()){
                        int event_value_of_donation= (int) dataSnapshot.child("EventDonation").getChildrenCount();
                        store_event_donation_number=event_value_of_donation+"";
                    }

                    totaldonations.setText(total_value_of_donation+"");
////////////////////////////////---------------------------------------------------------------------------////////////////////////////////////////////

                    database=FirebaseDatabase.getInstance();
                    reflevelbadges=database.getReference("weeklytotal");

                    reflevelbadges.child("weekchange").child("trigger").setValue("yes");
                    Query querylevelbadgecondition=reflevelbadges.child("weekchange");
                    querylevelbadgecondition.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            String startdate=snapshot.child("startdate").getValue(String.class);
                            String enddate=snapshot.child("enddate").getValue(String.class);
                            badgename=snapshot.child("badgename").getValue(String.class);

                            String fetchlimitofdonation=snapshot.child("limitofdonation").getValue(String.class);
                            String fetchwhichbadge=snapshot.child("badge").getValue(String.class);
                            try {
                                if (store_event_donation_number.equals(fetchlimitofdonation)) {
                                    badgedata = database.getReference("DonorsData");

                                    badgedata.child(useruuid.getUid()).child("playData").child("limitedbadgeData").child(badgename).setValue(fetchwhichbadge);

                                    reference.child(useruuid.getUid()).child("playData").child("badges").child(badgename).setValue("Earned");

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


/////////////////////////////-------------======================================================------------------------------------///////////////
/////////////////////////////////////////-----------------SEEKBAR UPDATE SECTION------------------//////////////////////////////////////////////////
                    if(dataSnapshot.child("playData").child("LEVEL").exists()) {
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
                            reference.child(useruuid.getUid()).child("playData").child("LEVEL").setValue("" +storetotallevel);
                            seekBar.setProgress(0);
                            XP.setText(0+"");
                            Query query1 = reference.child(useruuid.getUid()).child("playData");
                            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                    if (dataSnapshot1.exists()) {
                                        String level1 = dataSnapshot1.child("LEVEL").getValue(String.class);
                                        increase.setText(level1);

                                        ////////////////
                                        database=FirebaseDatabase.getInstance();
                                        reflevelbadges=database.getReference("limitedlevels");

                                        reflevelbadges.child("setchanges").child("trigger").setValue("yes");
                                        Query querylevelbadgecondition=reflevelbadges.child("setchanges");
                                        querylevelbadgecondition.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                String fetchlevel=snapshot.child("level").getValue(String.class);
                                                String fetchbadgeurl=snapshot.child("badges").getValue(String.class);
                                                String fetchbadgename=snapshot.child("badgename").getValue(String.class);
                                                // String current_user_level=increase.getText().toString();
                                                if(Integer.parseInt(level1)>=Integer.parseInt(fetchlevel)){

                                                    badgedata=database.getReference("DonorsData");
                                                    badgedata.child(useruuid.getUid()).child("playData").child("limitedbadgeData").child(fetchbadgename).setValue(fetchbadgeurl);

                                                    reference.child(useruuid.getUid()).child("playData").child("badges").child(fetchbadgename).setValue("Earned");

                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                        /////////////////////
                                        String getlevel=increase.getText().toString();
                                        if (Integer.parseInt(getlevel) == 1) {
                                            one.setImageResource(R.drawable.firstbadges);
                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("1").setValue("Earned");


                                        }
                                        if (Integer.parseInt(getlevel) >= 2 && Integer.parseInt(getlevel)<5) {
                                            one.setImageResource(R.drawable.firstbadges);


                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("1").setValue("Earned");

                                            two.setImageResource(R.drawable.secondbadges);
                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("2").setValue("Earned");


                                        }
                                        if (Integer.parseInt(getlevel)>=5 && Integer.parseInt(getlevel)<10 ) {
                                            one.setImageResource(R.drawable.firstbadges);

                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("1").setValue("Earned");

                                            two.setImageResource(R.drawable.secondbadges);
                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("2").setValue("Earned");

                                            five.setImageResource(R.drawable.fivebadges);
                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("5").setValue("Earned");

                                        }
                                        if(Integer.parseInt(getlevel)>=10 && Integer.parseInt(getlevel)<15){
                                            one.setImageResource(R.drawable.firstbadges);

                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("1").setValue("Earned");

                                            two.setImageResource(R.drawable.secondbadges);
                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("2").setValue("Earned");

                                            five.setImageResource(R.drawable.fivebadges);
                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("5").setValue("Earned");

                                            ten.setImageResource(R.drawable.tenbadges);
                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("10").setValue("Earned");


                                        }
                                        if(Integer.parseInt(getlevel)>=15){
                                            one.setImageResource(R.drawable.firstbadges);

                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("1").setValue("Earned");

                                            two.setImageResource(R.drawable.secondbadges);
                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("2").setValue("Earned");

                                            five.setImageResource(R.drawable.fivebadges);
                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("5").setValue("Earned");

                                            ten.setImageResource(R.drawable.tenbadges);
                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("10").setValue("Earned");

                                            fifteen.setImageResource(R.drawable.fifteenbadges);
                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("15").setValue("Earned");

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        } else {
                            seekBar.setProgress(50);
                            XP.setText(50+"");
                            reference.child(useruuid.getUid()).child("playData").child("status").setValue("Confirm");
                            Query query1 = reference.child(useruuid.getUid()).child("playData");
                            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                    if (dataSnapshot1.exists()) {

                                        int storetotallevel=0;
                                        if(total_responds==0){
                                            storetotallevel=total_value_of_donation *50 / 100;
                                        }
                                        else{
                                            storetotallevel=(total_value_of_donation *50+total_responds*50) / 100;

                                        }

                                        String level1 = storetotallevel+"";
                                        increase.setText(storetotallevel+"");


                                        database=FirebaseDatabase.getInstance();
                                        reflevelbadges=database.getReference("limitedlevels");

                                        reflevelbadges.child("setchanges").child("trigger").setValue("yes");
                                        Query querylevelbadgecondition=reflevelbadges.child("setchanges");
                                        querylevelbadgecondition.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                String fetchlevel=snapshot.child("level").getValue(String.class);
                                                String fetchbadgeurl=snapshot.child("badges").getValue(String.class);
                                                String fetchbadgename=snapshot.child("badgename").getValue(String.class);
                                                // String current_user_level=increase.getText().toString();
                                                if(Integer.parseInt(level1)>=Integer.parseInt(fetchlevel)){

                                                    badgedata=database.getReference("DonorsData");

                                                    badgedata.child(useruuid.getUid()).child("playData").child("limitedbadgeData").child(fetchbadgename).setValue(fetchbadgeurl);

                                                    reference.child(useruuid.getUid()).child("playData").child("badges").child(fetchbadgename).setValue("Earned");
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                        String getlevel=increase.getText().toString();
                                        if (Integer.parseInt(getlevel) == 1) {
                                            one.setImageResource(R.drawable.firstbadges);
                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("1").setValue("Earned");

                                        }
                                        if (Integer.parseInt(getlevel)>= 2 && Integer.parseInt(getlevel)<5) {
                                            one.setImageResource(R.drawable.firstbadges);


                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("1").setValue("Earned");

                                            two.setImageResource(R.drawable.secondbadges);
                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("2").setValue("Earned");


                                        }
                                        if (Integer.parseInt(getlevel)>=5 && Integer.parseInt(getlevel)<10 ) {
                                            one.setImageResource(R.drawable.firstbadges);


                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("1").setValue("Earned");

                                            two.setImageResource(R.drawable.secondbadges);
                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("2").setValue("Earned");

                                            five.setImageResource(R.drawable.fivebadges);
                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("5").setValue("Earned");

                                        }
                                        if(Integer.parseInt(getlevel)>=10 && Integer.parseInt(getlevel)<15){

                                            one.setImageResource(R.drawable.firstbadges);

                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("1").setValue("Earned");

                                            two.setImageResource(R.drawable.secondbadges);
                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("2").setValue("Earned");

                                            five.setImageResource(R.drawable.fivebadges);
                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("5").setValue("Earned");

                                            ten.setImageResource(R.drawable.tenbadges);
                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("10").setValue("Earned");


                                        }
                                        if(Integer.parseInt(getlevel)>=15){
                                            one.setImageResource(R.drawable.firstbadges);
                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("1").setValue("Earned");

                                            two.setImageResource(R.drawable.secondbadges);
                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("2").setValue("Earned");

                                            five.setImageResource(R.drawable.fivebadges);
                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("5").setValue("Earned");

                                            ten.setImageResource(R.drawable.tenbadges);
                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("10").setValue("Earned");

                                            fifteen.setImageResource(R.drawable.fifteenbadges);
                                            reference.child(useruuid.getUid()).child("playData").child("badges").child("15").setValue("Earned");


                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                    else{
                        reference.child(useruuid.getUid()).child("playData").child("LEVEL").setValue("0");
                    }

////////////////////----------------------------------XP section-----------------------------------------///////////////////////////////////////////////////////////////////////////////////////////////////////////

                    int storetotallevel=0;
                    if(total_responds==0){
                        storetotallevel=total_value_of_donation *50;
                    }
                    else{
                        storetotallevel=(total_value_of_donation *50+total_responds*50);

                    }
                    reference.child(useruuid.getUid()).child("playData").child("XP").setValue(storetotallevel);
                    Query query2=reference.child(useruuid.getUid()).child("playData");
                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                            if(dataSnapshot2.exists()) {
                                int totalxps = dataSnapshot2.child("XP").getValue(int.class);
                                totalxp.setText(totalxps+"");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    //////////////////////////////////////////////////////////////////////////////////////////////////
                    reference.child(useruuid.getUid()).child("playData").child("status").setValue("Confirm");
                    Query querybadges = reference.child(useruuid.getUid()).child("playData");
                    querybadges.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshotbadges) {
                            if(dataSnapshotbadges.child("badges").child("sharebadge").exists()){
                                sharebadges.setImageResource(R.drawable.sharebadge);
                            }
                            if(dataSnapshotbadges.child("badges").exists()){
                                int badgescount= (int) dataSnapshotbadges.child("badges").getChildrenCount();

                                totalbadges.setText(badgescount+"");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
///////////////////////////////////////////----------------------------------------------------------///////////////////////////////////////////////

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("DonorsData");

        reference.child(useruuid.getUid()).child("status").setValue("active");
        Query urllist=reference.child(useruuid.getUid());
        urllist.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("playData").child("limitedbadgeData").exists()) {

                    for (DataSnapshot snapshotevent : snapshot.child("playData").child("limitedbadgeData").getChildren()) {
                        String gettingnadgesurlfromEvent = snapshot.child("playData").child("limitedbadgeData").child(snapshotevent.getKey()).getValue(String.class);
                        pushkeysEvent.add(gettingnadgesurlfromEvent);

                    }
                    for(int i=0;i<pushkeysEvent.size();i++){
                        if(i==0){
                            try {
                                Glide.with(badgesection.this).load(pushkeysEvent.get(i)).into(levelbadgeone);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if(i==1){
                            try {
                                Glide.with(badgesection.this).load(pushkeysEvent.get(i)).into(levelbadgetwo);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if(i==2){
                            try {
                                Glide.with(badgesection.this).load(pushkeysEvent.get(i)).into(levelbadgethree);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if(i==3){
                            try {
                                Glide.with(badgesection.this).load(pushkeysEvent.get(i)).into(levelbadgefour);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
////////////////////////=====================LEVEL CONDTION BADGE DATABASE=============================///////////////////////////////////////////////






///////////////////////=======================ViewPager or carousel View==============================///////////////////////////////////////////////////

//=====================================================================================================================================================///////////
    }
    private void retrievingPhotofromdatabase() {

        profilepath="picture/"+useruuid.getUid()+".png";

        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        storageReference=FirebaseStorage.getInstance().getReference().child(profilepath);

        try{
            final File localfile=File.createTempFile(useruuid.getUid(),"png");
            storageReference.getFile(localfile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap= BitmapFactory.decodeFile(localfile.getAbsolutePath());
                            ((ImageView)findViewById(R.id.profile_image)).setImageBitmap(bitmap);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @SuppressLint("UseCompatLoadingForDrawables")
    public void funbadge1(View view) {

        if ((one.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.onegray).getConstantState())) {
            varlevelforinfo="1";

            Intent bridgetoshowactivity=new Intent(badgesection.this,InfoforGettingBadge.class);
            bridgetoshowactivity.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowactivity);
        }
        else{
            varlevelforinfo="1";

            Intent bridgetoshowspecial=new Intent(badgesection.this,BadgeView.class);
            bridgetoshowspecial.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowspecial);
        }

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void funbadge2(View view) {

        if ((two.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.twogray).getConstantState())) {
            varlevelforinfo="2";

            Intent bridgetoshowactivity=new Intent(badgesection.this,InfoforGettingBadge.class);
            bridgetoshowactivity.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowactivity);
        }
        else{
            varlevelforinfo="2";

            Intent bridgetoshowspecial=new Intent(badgesection.this,BadgeView.class);
            bridgetoshowspecial.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowspecial);
        }

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void funbadge5(View view) {

        if ((five.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.threegray).getConstantState())) {
            varlevelforinfo="5";

            Intent bridgetoshowactivity=new Intent(badgesection.this,InfoforGettingBadge.class);
            bridgetoshowactivity.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowactivity);
        }
        else{
            varlevelforinfo="5";

            Intent bridgetoshowspecial=new Intent(badgesection.this,BadgeView.class);
            bridgetoshowspecial.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowspecial);
        }

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void funbadge10(View view) {

        if ((ten.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.tengray).getConstantState())) {
            varlevelforinfo="10";

            Intent bridgetoshowactivity=new Intent(badgesection.this,InfoforGettingBadge.class);
            bridgetoshowactivity.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowactivity);

        }
        else{
            varlevelforinfo="10";

            Intent bridgetoshowspecial=new Intent(badgesection.this,BadgeView.class);
            bridgetoshowspecial.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowspecial);
        }

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void funbadge15(View view) {

        if ((fifteen.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.fifteengray).getConstantState())) {
            varlevelforinfo="15";

            Intent bridgetoshowactivity=new Intent(badgesection.this,InfoforGettingBadge.class);
            bridgetoshowactivity.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowactivity);

        }
        else{
            varlevelforinfo="15";

            Intent bridgetoshowspecial=new Intent(badgesection.this,BadgeView.class);
            bridgetoshowspecial.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowspecial);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void funbadgeshare(View view) {

        if ((sharebadges.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.questionmark).getConstantState())) {
            varlevelforinfo="Share This App";

            Intent bridgetoshowactivity=new Intent(badgesection.this,InfoforGettingBadge.class);
            bridgetoshowactivity.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowactivity);

        }
        else{
            varlevelforinfo="Share This App";

            Intent bridgetoshowspecial=new Intent(badgesection.this,BadgeView.class);
            bridgetoshowspecial.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowspecial);
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
}