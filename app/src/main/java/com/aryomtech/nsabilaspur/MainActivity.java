package com.aryomtech.nsabilaspur;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    VideoView videoView;
    ImageView imageView;

    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseUser checkuser;
    DatabaseReference reference,reflevelbadges;
    String time,key,timecurrent;
    Animation anim;
    public static Activity splash;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //////////////-------//////////////-----------RESET------------//////////////----------////////////
        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("DonorsData");
        auth=FirebaseAuth.getInstance();
        checkuser=auth.getCurrentUser();

        splash=this;

        Calendar calobj = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        time=df.format(calobj.getTime());

        boolean reset=getSharedPreferences("resetonce",MODE_PRIVATE)
                .getBoolean("reset",true);


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        getSharedPreferences("Openonce", MODE_PRIVATE).edit()
                .putBoolean("isFirstopen", true).apply();

        Window window = MainActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(MainActivity.this, R.color.statusbar));

        database=FirebaseDatabase.getInstance();
        reflevelbadges=database.getReference().child("weeklytotal");

        reflevelbadges.child("weekchange").child("trigger").setValue("yes");
        Query querylevelbadgecondition=reflevelbadges.child("weekchange");
        querylevelbadgecondition.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /*yaha start donation wala database ayga*/
                String startdate = snapshot.child("startdate").getValue(String.class);
                String enddate = snapshot.child("enddate").getValue(String.class);
                if (!startdate.equals("Null") && !enddate.equals("Null")) {
                    String GivenData = enddate.trim();
                    Date Date = new Date(GivenData);


                    String GivenDataend = startdate.trim();
                    Date Dateend = new Date(GivenDataend);

                    DateFormat df = new SimpleDateFormat("dd/MM/yy ");
                    Calendar calobj = Calendar.getInstance();
                    timecurrent = df.format(calobj.getTime());
                    String checktime=timecurrent;
                    Date check=new Date(checktime);
                    try {

                        if (startdate.trim().equals(timecurrent.trim())) {

                            getSharedPreferences("checkingdatecurrent", MODE_PRIVATE).edit()
                                    .putString("currentdate", "true").apply();


                        } else if (timecurrent.trim().equals(enddate.trim())) {

                            getSharedPreferences("checkingdatecurrent", MODE_PRIVATE).edit()
                                    .putString("currentdate", "false").apply();

                        } else if (check.before(Date) && check.after(Dateend)) {


                            getSharedPreferences("checkingdatecurrent", MODE_PRIVATE).edit()
                                    .putString("currentdate", "true").apply();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        imageView=(ImageView)findViewById(R.id.imageView4);
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                boolean isFirstLogin = getSharedPreferences("PREFERENCE3", MODE_PRIVATE)
                        .getBoolean("ONLYONE", false);
                if(isFirstLogin){

                    Intent main2open=new Intent(MainActivity.this,MainActivity2.class);
                    main2open.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(main2open);
                    finish();
                }
                else{
                    startActivity(new Intent(MainActivity.this,Login.class));
                    finish();
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(anim);
    }

}