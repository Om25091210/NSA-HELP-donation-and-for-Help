package com.aryomtech.nsabilaspur;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class giverfood extends AppCompatActivity implements LocationListener {

    private final  int REQUEST_CHECK_CODE=8989;
    private LocationSettingsRequest.Builder builder;

    View view;
    ImageView locatemapimg;
    ProgressDialog pd;
    LocationManager locationManager;
    String latitude="",longitude="";
    String category,time;
    EditText loc,name,phone;
    String checkname,checknum;
    String dpurl;

    String key1="key1";
    String key2="key2";
    String key3="key3";
    public static Activity fa;


    FirebaseDatabase database;
    DatabaseReference ref,reference,referenceFordonorsData;
    long maxid=0;
    ImageView donor_confirm;
    Member member;
    String individualsKey;
    ImageView giverbg,post;
    String pushKey;
    String receivingIdentity;
    ConstraintLayout give;
    String sendingIdentity="sendingIdentity";
    String DeviceToken,aryomTOKEN;

    boolean connected = false;
    String tosent="";

    FirebaseAuth auth;
    FirebaseUser useruuid;

    String getOrg="";
    String Type;
    ImageView description;
    String holddescription="";
    String holdloc="",holdname="",holdphone="",holdcategory="",holdtype="";
    ImageView addpic,fill,cancel,bgbg;
    ImageView locationtxt,nametxt,numtxt;
    long key=999999999;

    int i;
    int k=0;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giverfood);
        Intent secf=getIntent();
        receivingIdentity=secf.getStringExtra("Sending_identity");
        fa = this;
        category=secf.getStringExtra("donatefood");
        Type=secf.getStringExtra("sendingtype2003");
        auth=FirebaseAuth.getInstance();
        useruuid=auth.getCurrentUser();

        DeviceToken = FirebaseInstanceId.getInstance().getToken();

        Window window = giverfood.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(giverfood.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(giverfood.this, R.color.statusbar));

        loc=findViewById(R.id.loc);
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        donor_confirm=findViewById(R.id.donar_confirm);
        locationtxt=findViewById(R.id.locationtext);
        nametxt=findViewById(R.id.nametxt);
        numtxt=findViewById(R.id.numtxt);
        fill=findViewById(R.id.fillyour);
        cancel=findViewById(R.id.cancel);
        bgbg=findViewById(R.id.bgbg);

        view=findViewById(R.id.view5);
        locatemapimg=findViewById(R.id.locatemapimg);

        bgbg.setVisibility(View.GONE);
        giverbg=findViewById(R.id.bg);
        give=findViewById(R.id.give);
        post=findViewById(R.id.post);
        cancel.setVisibility(View.GONE);
        giverbg.setVisibility(View.GONE);
        post.setVisibility(View.GONE);

        addpic=findViewById(R.id.addpic);
        description=findViewById(R.id.description);

        holddescription=secf.getStringExtra("backdesctointent");
        holdloc=secf.getStringExtra("backloctointent");
        holdname=secf.getStringExtra("backnametointent");
        holdphone=secf.getStringExtra("backphonetointent");
        holdcategory=secf.getStringExtra("backcategorytointent");
        holdtype=secf.getStringExtra("backTypetemptointent");

        if(holdloc!=null && holdname!=null && holdphone!=null) {
            loc.setText(holdloc);
            name.setText(holdname);
            phone.setText(holdphone);
        }
        else{

            String previouslocation= getSharedPreferences("storelocationtemp",MODE_PRIVATE)
                    .getString("id:495004location","");

            String previousname= getSharedPreferences("storenametemp",MODE_PRIVATE)
                    .getString("id:1234567890name","");

            String previousnumber= getSharedPreferences("storenumbertemp",MODE_PRIVATE)
                    .getString("id:0987456123phone","");

            loc.setText(previouslocation);
            name.setText(previousname);
            phone.setText(previousnumber);
        }

        if(holdtype==null) {
            if (Type.equals("DONATION")) {
                donor_confirm.setImageResource(R.drawable.donate);
            } else {
                donor_confirm.setImageResource(R.drawable.askforhelp);
            }
        }
        else{
            if (holdtype.equals("DONATION")) {
                donor_confirm.setImageResource(R.drawable.donate);
            } else {
                donor_confirm.setImageResource(R.drawable.askforhelp);
            }
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LocationRequest request=new LocationRequest()
                        .setFastestInterval(1500)
                        .setInterval(3000)
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                builder=new LocationSettingsRequest.Builder()
                        .addLocationRequest(request);

                Task<LocationSettingsResponse> result=
                        LocationServices.getSettingsClient(giverfood.this).checkLocationSettings(builder.build());

                result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                        try {
                            task.getResult(ApiException.class);
                        } catch (ApiException e) {
                            switch (e.getStatusCode()){
                                case LocationSettingsStatusCodes
                                        .RESOLUTION_REQUIRED:

                                    try {
                                        ResolvableApiException resolvableApiException= (ResolvableApiException) e;
                                        resolvableApiException.startResolutionForResult(giverfood.this,REQUEST_CHECK_CODE);
                                    } catch (IntentSender.SendIntentException ex) {
                                        ex.printStackTrace();
                                    }catch (ClassCastException ex){

                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                {
                                    break;
                                }
                            }
                        }
                    }
                });

                pd=new ProgressDialog(giverfood.this);
                pd.setTitle("Detecting Location!...");
                pd.show();
                getLocation();
            }
        });
        locatemapimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LocationRequest request=new LocationRequest()
                        .setFastestInterval(1500)
                        .setInterval(3000)
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                builder=new LocationSettingsRequest.Builder()
                        .addLocationRequest(request);

                Task<LocationSettingsResponse> result=
                        LocationServices.getSettingsClient(giverfood.this).checkLocationSettings(builder.build());

                result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                        try {
                            task.getResult(ApiException.class);
                        } catch (ApiException e) {
                            switch (e.getStatusCode()){
                                case LocationSettingsStatusCodes
                                        .RESOLUTION_REQUIRED:

                                    try {
                                        ResolvableApiException resolvableApiException= (ResolvableApiException) e;
                                        resolvableApiException.startResolutionForResult(giverfood.this,REQUEST_CHECK_CODE);
                                    } catch (IntentSender.SendIntentException ex) {
                                        ex.printStackTrace();
                                    }catch (ClassCastException ex){

                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                {
                                    break;
                                }
                            }
                        }
                    }
                });

                pd=new ProgressDialog(giverfood.this);
                pd.setTitle("Detecting Location!...");
                pd.show();
                getLocation();
            }
        });


        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temploc=loc.getText().toString();
                String tempname=name.getText().toString();
                String tempphone=phone.getText().toString();
                Intent des=new Intent(giverfood.this,Descriptionsectionfood.class);
                des.putExtra("sendingdescription",holddescription);
                des.putExtra("sendingtypeagain",holdtype);
                des.putExtra("sendingtemploc",temploc);
                des.putExtra("sendingtempname",tempname);
                des.putExtra("sendingcategoytemp",category);
                des.putExtra("sendingTypetemp",Type);
                des.putExtra("sendingcategoytempsecondtime",holdcategory);

                des.putExtra("sendingtempphone",tempphone);
                startActivity(des);
                finish();
            }
        });
        aryomTOKEN= getSharedPreferences("PREFERENCE89", MODE_PRIVATE)
                .getString("TOKEN","NO");

        member=new Member();
        ref=database.getInstance().getReference().child("Donors");
        reference=database.getInstance().getReference().child("Accounts");
        DateFormat df = new SimpleDateFormat("dd/MM/yy hh:mm aa");

        Calendar calobj = Calendar.getInstance();
        time=df.format(calobj.getTime());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    maxid=(int)dataSnapshot.getChildrenCount();


                }
                else{
                    //
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //
            }
        });
        donor_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkloc=loc.getText().toString();
                checkname=name.getText().toString();
                checknum=phone.getText().toString();
                int len_num=checknum.length();
                if(!checknum.isEmpty()) {
                    if (!checkname.isEmpty()) {
                        if (!checkloc.isEmpty()) {
                            if (len_num == 10) {
                                if (CheckingName(checkname)) {

                                    if(!isNetworkAvailable()){
                                        try {
                                            AlertDialog alertDialog = new AlertDialog.Builder(giverfood.this).create();

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
                                            Snackbar.make(findViewById(android.R.id.content),"No NetWork Connection Found!!",Snackbar.LENGTH_LONG).show();

                                        }
                                    }
                                    else {
                                        try {
                                            if (!holdcategory.equals("null") && !holdtype.equals("null")) {
                                                category = holdcategory;
                                                Type = holdtype;
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        dpurl = getSharedPreferences("dpURL", MODE_PRIVATE)
                                                .getString("dpurlforstoring", "NULL");

                                        individualsKey = getSharedPreferences("PREFERENCE1", MODE_PRIVATE)
                                                .getString("initialize", "NO");
                                        String mx = key - maxid + "";

                                        String task = "Respond";

//==================================STORE DATA OF EDIT TEXT=====================================================================================================================================================

                                        getSharedPreferences("storelocationtemp", MODE_PRIVATE).edit()
                                                .putString("id:495004location", loc.getText().toString()).apply();

                                        getSharedPreferences("storenametemp", MODE_PRIVATE).edit()
                                                .putString("id:1234567890name", checkname).apply();

                                        getSharedPreferences("storenumbertemp", MODE_PRIVATE).edit()
                                                .putString("id:0987456123phone", checknum).apply();

                                        String previouslatitude = getSharedPreferences("storelatitude", MODE_PRIVATE)
                                                .getString("id:789321654latitude", latitude);

                                        String previouslongitude = getSharedPreferences("storelongitude", MODE_PRIVATE)
                                                .getString("id:321987654longitude", longitude);
//=======================================================================================================================================================================================

                                        pushKey = ref.push().getKey();

                                        String done = "no";
                                        member.setLoc(loc.getText().toString());//
                                        member.setName(checkname);//
                                        member.setPhone(checknum);//
                                        member.setCategory(category);//
                                        member.setKey(mx);//
                                        member.setTask(task);//
                                        member.setUniqueid(pushKey);//
                                        member.setIndividualKey(useruuid.getUid());//
                                        member.setTime(time);//
                                        member.setDevicetoken(DeviceToken);//
                                        member.setPosted("NULL");
                                        member.setDp(dpurl);
                                        member.setDescription(holddescription);
                                        member.setType(Type);
                                        member.setLatitude(previouslatitude);
                                        member.setLongitude(previouslongitude);
                                        member.setDone(done);


                                        ref.child(pushKey).setValue(member);

                                        referenceFordonorsData = FirebaseDatabase.getInstance().getReference().child("DonorsData");


                                        if (Type.equals("DONATION")) {
                                            referenceFordonorsData.child(useruuid.getUid()).child("ConfirmDonation").child(pushKey).setValue(member);
                                        } else if (Type.equals("HELP")) {
                                            referenceFordonorsData.child(useruuid.getUid()).child("ConfirmedHELP").child(pushKey).setValue(member);
                                        }

                                        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            vibe.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                                        } else {
                                            //deprecated in API 26
                                            vibe.vibrate(500);
                                        }

                                        reference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot sanp : snapshot.getChildren()) {

                                                    getOrg = snapshot.child(sanp.getKey()).child("organization").getValue(String.class);
                                                    tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                                    try {
                                                        if (getOrg.equals("DHITI") && tosent.equals("YES")) {

                                                            String getvalue = snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                                            Specific specific = new Specific();
                                                            specific.noti(Type, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
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

                                        Intent foodkt = new Intent(giverfood.this, MainActivity3.class);
                                        foodkt.putExtra("key1", checknum);
                                        foodkt.putExtra("category", category);
                                        foodkt.putExtra("sendingmax", mx);
                                        foodkt.putExtra("checkloc", checkloc);
                                        foodkt.putExtra("sendingtask", task);
                                        foodkt.putExtra("key2", checkname);
                                        foodkt.putExtra("sendingindividualkey", useruuid.getUid());
                                        foodkt.putExtra("sendingtime", time);
                                        foodkt.putExtra("sendingdevicetoken", DeviceToken);
                                        foodkt.putExtra("key3", pushKey);
                                        foodkt.putExtra("sendingIdentity", receivingIdentity);
                                        foodkt.putExtra("sendingpushkey", pushKey);
                                        foodkt.putExtra("sendingthetypeofTYPE", Type);
                                        startActivity(foodkt);
                                        finish();
                                    }
                                } else {
                                    name.setError("Field invalid");
                                }
                            } else {
                                phone.setError("Invalid Input");
                            }
                        }
                        else{
                            loc.setError("This Field Cannot be Empty");
                        }
                    }
                    else{
                        name.setError("This Field Cannot be Empty");
                    }
                }
                else{
                    phone.setError("This Field Cannot be Empty");

                }


            }
        });



        addpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String checkloc=loc.getText().toString();
                checkname=name.getText().toString();
                checknum=phone.getText().toString();
                int len_num=checknum.length();
                if(!checknum.isEmpty()) {
                    if (!checkname.isEmpty()) {
                        if (!checkloc.isEmpty()) {
                            if (len_num == 10) {
                                if (CheckingName(checkname)) {

                                    try {
                                        if (!holdcategory.equals("null") && !holdtype.equals("null")) {
                                            category = holdcategory;
                                            Type=holdtype;
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    individualsKey= getSharedPreferences("PREFERENCE1", MODE_PRIVATE)
                                            .getString("initialize","NO");

                                    String previouslatitude= getSharedPreferences("storelatitude",MODE_PRIVATE)
                                            .getString("id:789321654latitude",latitude);

                                    String previouslongitude= getSharedPreferences("storelongitude",MODE_PRIVATE)
                                            .getString("id:321987654longitude",longitude);

                                    String mx = key - maxid + "";

                                    String task="Respond";

                                    pushKey=ref.push().getKey();
                                    String giver="giverfood";
                                    Intent foodkt=new Intent(giverfood.this,Addphotos.class);
                                    foodkt.putExtra("key1", checknum);
                                    foodkt.putExtra("category",category);
                                    foodkt.putExtra("sendingmax", mx);//
                                    foodkt.putExtra("checkloc",checkloc);
                                    foodkt.putExtra("sendingtask",task);
                                    foodkt.putExtra("key2", checkname);
                                    foodkt.putExtra("sendingindividualkey", useruuid.getUid());
                                    foodkt.putExtra("sendingtime", time);
                                    foodkt.putExtra("sendingdevicetoken", DeviceToken);
                                    foodkt.putExtra("key3", pushKey);
                                    foodkt.putExtra("intentname",giver);
                                    foodkt.putExtra("sendingIdentity", receivingIdentity);
                                    foodkt.putExtra("sendingdescription2",holddescription);
                                    foodkt.putExtra("sendingthetypeofTYPE",Type);
                                    foodkt.putExtra("sendinglatitude",previouslatitude);
                                    foodkt.putExtra("sendinglongitude",previouslongitude);

                                    startActivity(foodkt);

                                } else {
                                    name.setError("Field invalid");
                                }
                            } else {
                                phone.setError("Invalid Input");
                            }
                        }
                        else{
                            loc.setError("This Field Cannot be Empty");
                        }
                    }
                    else{
                        name.setError("This Field Cannot be Empty");
                    }
                }
                else{
                    phone.setError("This Field Cannot be Empty");

                }
            }
        });


    }
    @SuppressLint("MissingPermission")
    private void getLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5, giverfood.this);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void onLocationChanged(Location location) {

        latitude= String.valueOf(location.getLatitude());
        longitude= String.valueOf(location.getLongitude());
        try {
            Geocoder geocoder = new Geocoder(giverfood.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);

            getSharedPreferences("storelatitude",MODE_PRIVATE).edit()
                    .putString("id:789321654latitude",latitude).apply();

            getSharedPreferences("storelongitude",MODE_PRIVATE).edit()
                    .putString("id:321987654longitude",longitude).apply();

            pd.dismiss();
            loc.setText(address);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onBackPressed() {
        if(holdtype==null) {
            if (Type.equals("DONATION")) {
                Intent gotohome = new Intent(giverfood.this, Donate.class);
                System.out.println("Donate");
                startActivity(gotohome);
                finish();
            } else {
                Intent gotohome = new Intent(giverfood.this, HELPsection.class);
                System.out.println("HELP");
                startActivity(gotohome);
                finish();
            }
        }
        else{
            if (holdtype.equals("DONATION")) {
                Intent gotohome = new Intent(giverfood.this, Donate.class);
                System.out.println("Donate");
                startActivity(gotohome);
                finish();
            } else {
                Intent gotohome = new Intent(giverfood.this, HELPsection.class);
                System.out.println("HELP");
                startActivity(gotohome);
                finish();
            }
        }

        super.onBackPressed();

    }


    public boolean CheckingName(String name){

        for(i=0;i<name.length();i++){
            char c=name.charAt(i);
            String cs=c+"";
            if(cs.equals("1") || cs.equals("2") || cs.equals("3") || cs.equals("4") || cs.equals("5") || cs.equals("6") || cs.equals("7") || cs.equals("8") || cs.equals("9") || cs.equals("0") || cs.equals("@") || cs.equals("!") || cs.equals("#") || cs.equals("$") || cs.equals("%") || cs.equals("^") || cs.equals("&") || cs.equals("*") || cs.equals("(") || cs.equals(")") || cs.equals("-") || cs.equals("=") || cs.equals("+") || cs.equals("{") || cs.equals("}") || cs.equals("[") || cs.equals("]") || cs.equals(";") || cs.equals(":") || cs.equals("'") || cs.equals("\"") || cs.equals("|") || cs.equals("\\") || cs.equals("/") || cs.equals(",") || cs.equals(".") || cs.equals("<") || cs.equals(">") || cs.equals("~") || cs.equals("`")){
                k=1;

            }

        }
        if(k==0){
            return true;
        }
        else{
            k=0;
            return false;

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