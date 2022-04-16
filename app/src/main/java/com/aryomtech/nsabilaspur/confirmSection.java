package com.aryomtech.nsabilaspur;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.view.QuerySpec;

public class confirmSection extends AppCompatActivity {

    String hold_key,hold_id;

    private final  int REQUEST_CHECK_CODE=8989;
    private LocationSettingsRequest.Builder builder;

    Button confirm;
    private FirebaseAuth mAuth;
    static FirebaseUser useruuid;
    FirebaseDatabase database;
    DatabaseReference ref,reftimeline,referencetoTimeLine;
    String getName;
    String getDonorName,getDonorPhone,getLocation,getCategory,Tokend,gettingTYPE;
    Member member;
    String show_for_satisfaction="true";
    String Bio;
    String TYPE="DONATION";
    String category_of_ngo;
    String addressofNgo;
    String instaid;
    String show="true";
    String Facebookid;
    String Youtubeid;
    String phoneofNgo;
    String latitude="";
    String longitude="";
    String nameofNGO;
    String description;
    String ngouid;
    String websitelink;

    String dpurl;
    String coverurl;
    Button maps;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_section);
        Intent getfeed=getIntent();

        mAuth=FirebaseAuth.getInstance();
        useruuid = mAuth.getCurrentUser();

        MainActivity2.maintwo.finish();

        LocationRequest request=new LocationRequest()
                .setFastestInterval(1500)
                .setInterval(3000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        builder=new LocationSettingsRequest.Builder()
                .addLocationRequest(request);

        Task<LocationSettingsResponse> result=
                LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());

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
                                resolvableApiException.startResolutionForResult(confirmSection.this,REQUEST_CHECK_CODE);
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


        Window window = confirmSection.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(confirmSection.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(confirmSection.this, R.color.statusbar));

        getName=getfeed.getStringExtra("sendingName");
        hold_key=getfeed.getStringExtra("sending_key");
        hold_id=getfeed.getStringExtra("sending_id");

        getDonorName=getfeed.getStringExtra("passingDonorName");
        getDonorPhone=getfeed.getStringExtra("passingDonorPhone");
        getLocation=getfeed.getStringExtra("passingLocation");
        getCategory=getfeed.getStringExtra("passingCategory");
        Tokend=getfeed.getStringExtra("Token");
        gettingTYPE=getfeed.getStringExtra("passingTYPECATEGORY");

        latitude=getfeed.getStringExtra("passinglatitudeofdonor");
        longitude=getfeed.getStringExtra("passinglongitudeofdonor");

        description=getfeed.getStringExtra("passingdescription");
        database=FirebaseDatabase.getInstance();
        ref=database.getReference().child("Donors");


        confirm=findViewById(R.id.confirm);
        TextView name=findViewById(R.id.Namecon);
        TextView locate=findViewById(R.id.locationcon);
        TextView phone=findViewById(R.id.phonecon);
         maps=findViewById(R.id.maps);

        name.setText(getDonorName);
        locate.setText(getLocation);
        phone.setText(getDonorPhone);


        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(latitude.equals("") || longitude.equals("")){
                    Uri uri=Uri.parse("geo:0,0?q="+getLocation);
                    Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                    intent.setPackage("com.google.android.apps.maps");
                    startActivity(intent);
                }
                else {
                    String uri = "http://maps.google.com/maps?daddr=" + latitude + "," + longitude + " (" + "Where the party is at" + ")";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    startActivity(intent);
                }}
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child(hold_id).child("task").setValue("Confirmed");
                ref.child(hold_id).child("confirmedby").setValue(getName);

                ref=database.getReference().child("Users");

                getSharedPreferences("showlevelanimationuserhasdonated", MODE_PRIVATE).edit()
                        .putString("userhasdonated", "true").apply();

                if(gettingTYPE.equals("DONATION")) {
                    String done="no";
                    member = new Member();
                    member.setName(getDonorName);
                    member.setPhone(getDonorPhone);
                    member.setCategory(getCategory);
                    member.setLoc(getLocation);
                    member.setKey(hold_key);
                    member.setDescription(description);
                    member.setLatitude(latitude);
                    member.setLongitude(longitude);
                    member.setUniqueid(hold_id);
                    member.setType(gettingTYPE);
                    member.setDone(done);

                    ref.child(useruuid.getUid()).child(getName).child("ConfirmId").child(hold_id + "").setValue(member);
                }
                else if(gettingTYPE.equals("HELP")){
                    String done="no";
                    member = new Member();
                    member.setName(getDonorName);
                    member.setPhone(getDonorPhone);
                    member.setCategory(getCategory);
                    member.setLoc(getLocation);
                    member.setKey(hold_key);
                    member.setDescription(description);
                    member.setLatitude(latitude);
                    member.setLongitude(longitude);
                    member.setUniqueid(hold_id);
                    member.setType(gettingTYPE);
                    member.setDone(done);

                    ref.child(useruuid.getUid()).child(getName).child("ConfirmIdHelp").child(hold_id + "").setValue(member);
                }


                ref=database.getReference().child("DonorsData");

                ref.child(useruuid.getUid()).child("playData").child("Responds").child(hold_id).setValue("Responded");
                ref.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("Responds").child(hold_id).setValue("Responded");

                Specific specific=new Specific();
                specific.noti(useruuid.getDisplayName()," Has Responded To Your "+gettingTYPE+" REQUEST!!",Tokend);



                reftimeline=database.getReference().child("VerifiedNGOs");

                reftimeline.child(useruuid.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        nameofNGO=dataSnapshot.child("NameOfNGO").getValue(String.class);
                        ngouid=dataSnapshot.child("uid").getValue(String.class);

                        if(dataSnapshot.child("DpUrl").exists()){
                            dpurl=dataSnapshot.child("DpUrl").getValue(String.class);

                        }
                        else{
                            dpurl="Null";
                        }
                        if(dataSnapshot.child("Bio").exists()){
                            Bio=dataSnapshot.child("Bio").getValue(String.class);

                        }
                        else{
                            Bio="";
                        }
                        if(dataSnapshot.child("category_of_ngo").exists()){
                            category_of_ngo=dataSnapshot.child("category_of_ngo").getValue(String.class);

                        }
                        else{
                            category_of_ngo="";
                        }
                        if(dataSnapshot.child("addressofNgo").exists()){
                            addressofNgo=dataSnapshot.child("addressofNgo").getValue(String.class);

                        }
                        else{
                            addressofNgo="Null";
                        }
                        if(dataSnapshot.child("Instagramid").exists()){
                            instaid=dataSnapshot.child("Instagramid").getValue(String.class);

                        }
                        else{
                            instaid="Null";
                        }
                        if(dataSnapshot.child("Facebookid").exists()){
                            Facebookid=dataSnapshot.child("Facebookid").getValue(String.class);

                        }
                        else{
                            Facebookid="Null";
                        }
                        if(dataSnapshot.child("Youtubeid").exists()){
                            Youtubeid=dataSnapshot.child("Youtubeid").getValue(String.class);

                        }
                        else{
                            Youtubeid="Null";
                        }

                        if(dataSnapshot.child("phoneofNgo").exists()){
                            phoneofNgo=dataSnapshot.child("phoneofNgo").getValue(String.class);

                        }
                        else{
                            phoneofNgo="Null";
                        }

                        if(dataSnapshot.child("Websitelink").exists()){
                            websitelink=dataSnapshot.child("Websitelink").getValue(String.class);
                        }
                        else{
                            websitelink="";
                        }


                        database=FirebaseDatabase.getInstance();
                        referencetoTimeLine=database.getReference().child("Timeline");


                        referencetoTimeLine.child(hold_id).child("Respondedby").setValue(nameofNGO);
                        referencetoTimeLine.child(hold_id).child("Respondeddp").setValue(dpurl);
                        referencetoTimeLine.child(hold_id).child("Respondeduid").setValue(ngouid);
                        referencetoTimeLine.child(hold_id).child("Respondedcover").setValue(coverurl);
                        referencetoTimeLine.child(hold_id).child("Respondedto").setValue(getDonorName);
                        referencetoTimeLine.child(hold_id).child("RespondedTYPE").setValue(gettingTYPE);
                        referencetoTimeLine.child(hold_id).child("RespondedBio").setValue(Bio);
                        referencetoTimeLine.child(hold_id).child("Respondedcategory").setValue(category_of_ngo);
                        referencetoTimeLine.child(hold_id).child("Respondedaddress").setValue(addressofNgo);
                        referencetoTimeLine.child(hold_id).child("Respondedinstaid").setValue(instaid);
                        referencetoTimeLine.child(hold_id).child("RespondedFBID").setValue(Facebookid);
                        referencetoTimeLine.child(hold_id).child("RespondedYoutubeId").setValue(Youtubeid);
                        referencetoTimeLine.child(hold_id).child("Respondedphoneno").setValue(phoneofNgo);
                        referencetoTimeLine.child(hold_id).child("Respondedwebsitelink").setValue(websitelink);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                getSharedPreferences("contolling_Animation_on_recreate",MODE_PRIVATE).edit()
                        .putString("Animation_on_create", "true").apply();

                Intent openfeed=new Intent(confirmSection.this,MainActivity2.class);
                openfeed.putExtra("animatingXP",show);
                openfeed.putExtra("sendingTYPE",TYPE);
                openfeed.putExtra("sendingTYPEsatisfaction",show_for_satisfaction);
                startActivity(openfeed);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent openfeed=new Intent(confirmSection.this,MainActivity2.class);
        startActivity(openfeed);
        finish();
        super.onBackPressed();

    }
}