package com.aryomtech.nsabilaspur;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class seethebadges extends AppCompatActivity {

    String level,userindividualkey;
    FirebaseDatabase database;
    String varlevelforinfo="";

    FirebaseAuth auth;
    FirebaseUser useruuid;

    DatabaseReference reference;
    ImageView one,two,five,ten,fifteen,twenty,twentyfive,thirty,thirtyfive,fourty,fourtyfive,fifty;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seethebadges);

        auth=FirebaseAuth.getInstance();
        useruuid=auth.getCurrentUser();

        Intent getlevel = getIntent();
        level = getlevel.getStringExtra("sendinglevel");
        userindividualkey = getlevel.getStringExtra("sendinguserkey");

        one=findViewById(R.id.one);
        two=findViewById(R.id.two);
        five=findViewById(R.id.five);
        ten=findViewById(R.id.ten);
        fifteen=findViewById(R.id.fifteen);
        twenty=findViewById(R.id.twenty);
        twentyfive=findViewById(R.id.twentyfive);
        thirty=findViewById(R.id.thirty);
        thirtyfive=findViewById(R.id.thirtyfive);
        fourty=findViewById(R.id.fourty);
        fourtyfive=findViewById(R.id.fourtyfive);
        fifty=findViewById(R.id.fifty);

        Window window = seethebadges.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(seethebadges.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(seethebadges.this, R.color.statusbar));

        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("DonorsData");

        if (Integer.parseInt(level) == 1) {
            one.setImageResource(R.drawable.firstbadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("1").setValue("Earned");

        }
        if (Integer.parseInt(level) >= 2 && Integer.parseInt(level)<5) {
            one.setImageResource(R.drawable.firstbadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("1").setValue("Earned");

            two.setImageResource(R.drawable.secondbadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("2").setValue("Earned");
        }
        if (Integer.parseInt(level) >= 5 && Integer.parseInt(level) < 10) {
            one.setImageResource(R.drawable.firstbadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("1").setValue("Earned");

            two.setImageResource(R.drawable.secondbadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("2").setValue("Earned");

            five.setImageResource(R.drawable.fivebadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("5").setValue("Earned");
        }
        if (Integer.parseInt(level) >= 10 && Integer.parseInt(level) < 15) {
            one.setImageResource(R.drawable.firstbadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("1").setValue("Earned");

            two.setImageResource(R.drawable.secondbadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("2").setValue("Earned");

            five.setImageResource(R.drawable.fivebadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("5").setValue("Earned");

            ten.setImageResource(R.drawable.tenbadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("10").setValue("Earned");
        }
        if (Integer.parseInt(level) >= 15 && Integer.parseInt(level) < 20) {
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
        if (Integer.parseInt(level) >=20 && Integer.parseInt(level) < 25) {
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

            twenty.setImageResource(R.drawable.twentybadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("20").setValue("Earned");
        }

        if (Integer.parseInt(level) >=25 && Integer.parseInt(level) < 30) {
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

            twenty.setImageResource(R.drawable.twentybadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("20").setValue("Earned");

            twentyfive.setImageResource(R.drawable.twentyfivebadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("25").setValue("Earned");
        }
        if (Integer.parseInt(level) >=30 && Integer.parseInt(level) < 35) {
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

            twenty.setImageResource(R.drawable.twentybadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("20").setValue("Earned");

            twentyfive.setImageResource(R.drawable.twentyfivebadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("25").setValue("Earned");

            thirty.setImageResource(R.drawable.thirtybadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("30").setValue("Earned");
        }

        if (Integer.parseInt(level) >=35 && Integer.parseInt(level) < 40) {
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

            twenty.setImageResource(R.drawable.twentybadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("20").setValue("Earned");

            twentyfive.setImageResource(R.drawable.twentyfivebadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("25").setValue("Earned");

            thirty.setImageResource(R.drawable.thirtybadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("30").setValue("Earned");

            thirtyfive.setImageResource(R.drawable.thirtyfivebadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("35").setValue("Earned");
        }

        if (Integer.parseInt(level) >=40 && Integer.parseInt(level) < 45) {
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

            twenty.setImageResource(R.drawable.twentybadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("20").setValue("Earned");

            twentyfive.setImageResource(R.drawable.twentyfivebadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("25").setValue("Earned");

            thirty.setImageResource(R.drawable.thirtybadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("30").setValue("Earned");

            thirtyfive.setImageResource(R.drawable.thirtyfivebadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("35").setValue("Earned");

            fourty.setImageResource(R.drawable.fourtybadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("40").setValue("Earned");
        }

        if (Integer.parseInt(level) >=45 && Integer.parseInt(level) < 50) {
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

            twenty.setImageResource(R.drawable.twentybadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("20").setValue("Earned");

            twentyfive.setImageResource(R.drawable.twentyfivebadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("25").setValue("Earned");

            thirty.setImageResource(R.drawable.thirtybadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("30").setValue("Earned");

            thirtyfive.setImageResource(R.drawable.thirtyfivebadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("35").setValue("Earned");

            fourty.setImageResource(R.drawable.fourtybadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("40").setValue("Earned");

            fourtyfive.setImageResource(R.drawable.fourtyfivebadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("45").setValue("Earned");
        }

        if (Integer.parseInt(level) >=50) {
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

            twenty.setImageResource(R.drawable.twentybadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("20").setValue("Earned");

            twentyfive.setImageResource(R.drawable.twentyfivebadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("25").setValue("Earned");

            thirty.setImageResource(R.drawable.thirtybadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("30").setValue("Earned");

            thirtyfive.setImageResource(R.drawable.thirtyfivebadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("35").setValue("Earned");

            fourty.setImageResource(R.drawable.fourtybadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("40").setValue("Earned");

            fourtyfive.setImageResource(R.drawable.fourtyfivebadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("45").setValue("Earned");

            fifty.setImageResource(R.drawable.fiftybadges);
            reference.child(useruuid.getUid()).child("playData").child("badges").child("50").setValue("Earned");

        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void badgefun1(View view) {

        if ((one.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.onegray).getConstantState())) {
            varlevelforinfo="1";

            Intent bridgetoshowactivity=new Intent(seethebadges.this,InfoforGettingBadge.class);
            bridgetoshowactivity.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowactivity);
        }
        else{
            varlevelforinfo="1";

            Intent bridgetoshowspecial=new Intent(seethebadges.this,BadgeView.class);
            bridgetoshowspecial.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowspecial);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void badgefun2(View view) {

        if ((two.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.twogray).getConstantState())) {
            varlevelforinfo="2";

            Intent bridgetoshowactivity=new Intent(seethebadges.this,InfoforGettingBadge.class);
            bridgetoshowactivity.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowactivity);
        }
        else{
            varlevelforinfo="2";

            Intent bridgetoshowspecial=new Intent(seethebadges.this,BadgeView.class);
            bridgetoshowspecial.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowspecial);
        }

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void badgefun3(View view) {

        if ((five.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.threegray).getConstantState())) {
            varlevelforinfo="5";

            Intent bridgetoshowactivity=new Intent(seethebadges.this,InfoforGettingBadge.class);
            bridgetoshowactivity.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowactivity);
        }
        else{
            varlevelforinfo="5";

            Intent bridgetoshowspecial=new Intent(seethebadges.this,BadgeView.class);
            bridgetoshowspecial.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowspecial);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void badgefun10(View view) {

        if ((ten.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.tengray).getConstantState())) {
            varlevelforinfo="10";

            Intent bridgetoshowactivity=new Intent(seethebadges.this,InfoforGettingBadge.class);
            bridgetoshowactivity.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowactivity);
        }
        else{
            varlevelforinfo="10";

            Intent bridgetoshowspecial=new Intent(seethebadges.this,BadgeView.class);
            bridgetoshowspecial.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowspecial);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void badgefun15(View view) {

        if ((fifteen.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.fifteengray).getConstantState())) {
            varlevelforinfo="15";

            Intent bridgetoshowactivity=new Intent(seethebadges.this,InfoforGettingBadge.class);
            bridgetoshowactivity.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowactivity);
        }
        else{
            varlevelforinfo="15";

            Intent bridgetoshowspecial=new Intent(seethebadges.this,BadgeView.class);
            bridgetoshowspecial.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowspecial);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void badgefun20(View view) {

        if ((twenty.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.twentygray).getConstantState())) {
            varlevelforinfo="20";

            Intent bridgetoshowactivity=new Intent(seethebadges.this,InfoforGettingBadge.class);
            bridgetoshowactivity.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowactivity);
        }
        else{
            varlevelforinfo="20";

            Intent bridgetoshowspecial=new Intent(seethebadges.this,BadgeView.class);
            bridgetoshowspecial.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowspecial);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void badgefun25(View view) {

        if ((twentyfive.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.twentyfivegray).getConstantState())) {
            varlevelforinfo="25";

            Intent bridgetoshowactivity=new Intent(seethebadges.this,InfoforGettingBadge.class);
            bridgetoshowactivity.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowactivity);
        }
        else{
            varlevelforinfo="25";

            Intent bridgetoshowspecial=new Intent(seethebadges.this,BadgeView.class);
            bridgetoshowspecial.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowspecial);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void badgefun30(View view) {

        if ((thirty.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.thirtygray).getConstantState())) {
            varlevelforinfo="30";

            Intent bridgetoshowactivity=new Intent(seethebadges.this,InfoforGettingBadge.class);
            bridgetoshowactivity.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowactivity);
        }
        else{
            varlevelforinfo="30";

            Intent bridgetoshowspecial=new Intent(seethebadges.this,BadgeView.class);
            bridgetoshowspecial.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowspecial);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void badgefun35(View view) {

        if ((thirtyfive.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.thirtyfivegray).getConstantState())) {
            varlevelforinfo="35";

            Intent bridgetoshowactivity=new Intent(seethebadges.this,InfoforGettingBadge.class);
            bridgetoshowactivity.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowactivity);
        }
        else{
            varlevelforinfo="35";

            Intent bridgetoshowspecial=new Intent(seethebadges.this,BadgeView.class);
            bridgetoshowspecial.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowspecial);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void badgefun40(View view) {

        if ((fourty.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.fourtygray).getConstantState())) {
            varlevelforinfo="40";

            Intent bridgetoshowactivity=new Intent(seethebadges.this,InfoforGettingBadge.class);
            bridgetoshowactivity.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowactivity);
        }
        else{
            varlevelforinfo="40";

            Intent bridgetoshowspecial=new Intent(seethebadges.this,BadgeView.class);
            bridgetoshowspecial.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowspecial);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void badgefun45(View view) {

        if ((fourtyfive.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.fourtyfivegray).getConstantState())) {
            varlevelforinfo="45";

            Intent bridgetoshowactivity=new Intent(seethebadges.this,InfoforGettingBadge.class);
            bridgetoshowactivity.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowactivity);
        }
        else{
            varlevelforinfo="45";

            Intent bridgetoshowspecial=new Intent(seethebadges.this,BadgeView.class);
            bridgetoshowspecial.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowspecial);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void badgefun50(View view) {

        if ((fifty.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.fiftygray).getConstantState())) {
            varlevelforinfo="50";

            Intent bridgetoshowactivity=new Intent(seethebadges.this,InfoforGettingBadge.class);
            bridgetoshowactivity.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowactivity);
        }
        else{
            varlevelforinfo="50";

            Intent bridgetoshowspecial=new Intent(seethebadges.this,BadgeView.class);
            bridgetoshowspecial.putExtra("sendinglevelforINFO",varlevelforinfo);
            startActivity(bridgetoshowspecial);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {

            Intent gotohome=new Intent(seethebadges.this,badgesection.class);
            startActivity(gotohome);
            super.onBackPressed();
    }
}