package com.aryomtech.nsabilaspur;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class Holder extends RecyclerView.ViewHolder {

    View view;
    Context context;

    String tm;
    int runanim = 0;

    Button unique_key;
    Button  btnres;

    int total_responds=0;

    private FirebaseAuth mAuth;
    Animation anim, animlevel, animlevelback;
    Button desopen;
    String receiveIdentity;
    String  sending_id="sending_id";
    String  uqid;
    String gettingName;
    String donorname,donorphone;
    String passingLocation="passingLocation";
    String passingCategory="passingCategory";
    String passingDonorName="passingDonorName";
    String passingDonorPhone="passingDonorPhone";
    String sending_key="sending_key";
    String sendingName="sendingName";
    String Token="Token";
    String confirmedBy="confirmedBy";
    FirebaseDatabase database;
    DatabaseReference userReference,verifiedngoreference;
    String sendingKeyOfIndividual="sendingKeyOfIndividual";
    String Indivkey;
    String Posted="NULL";
    ImageView post;
    String keyOfIndividual;
    TextView editText;
    Dialog dialogAnimated;
    String DeviceToken,emailverified;
    ImageView feedImage;
    ///////////////////==============================================================================================
    String hold_key,hold_id;

    private final  int REQUEST_CHECK_CODE=8989;
    private LocationSettingsRequest.Builder builder;

    Button confirm;
    static FirebaseUser useruuid;
    DatabaseReference ref,reftimeline,referencetoTimeLine,referenceforplay,reportRef,developerreference;
    String getName;
    String getDonorName,getDonorPhone,getLocation,getCategory,Tokend,gettingTYPE;
    Member member;
    String show_for_satisfaction="true";
    String Bio;
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

    showingFragment firstFragment;

    View view11,view2;
    ImageView congrats,levelincrease,xpup;
    String dpurl;
    String coverurl;
    Dialog dialogAnimateds;
    Button delete;
    Button cancel;
    Button maps;
    TextView TYPE;

    String reporttm;
    reportData reportData;
    //////////////////===============================================================================================
    int i=0;

    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    public Holder(@NonNull View itemView) {
        super(itemView);

        view=itemView;

    }

    public void setView(final Context context, final String location, final String category,
                        final String task, String key, String uniqueid, final String receiveName,
                        String getIdentity, String DonorName, String DonorPhone, final String indivkey,
                        final String keyfromFirebase, String time, String Token, String postedornot, final String length
            , final String pic1, final String pic2, final String pic3, final String pic4,String Dpurl,String description,
                        String type,String latitude,String longitude){

        //Typecasting variables and declaring values

        TextView locate=view.findViewById(R.id.location);
        TextView cate=view.findViewById(R.id.category);
        TextView Time=view.findViewById(R.id.time);
        TYPE=view.findViewById(R.id.Maincategory);

        ImageView report=view.findViewById(R.id.imageView10);
        firstFragment= new showingFragment();
        //TODO://////////////////////////////////////NULL
        if(type.equals("DONATION")){
            TYPE.setText(type);

        }
        else if(type.equals("HELP"))
        {
            TYPE.setText(type);
        }
        desopen=view.findViewById(R.id.desopen);
        desopen.setVisibility(View.GONE);

        try{
            if(description.equals("")){
                desopen.setVisibility(View.GONE);
            }
            else{
                desopen.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            desopen.setVisibility(View.GONE);
        }


        desopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAnimated=new Dialog(context,R.style.dialogstyle);
                dialogAnimated.setContentView(R.layout.dialogbox);
                dialogAnimated.show();
                TextView txtclose=(TextView)dialogAnimated.findViewById(R.id.txtclose);
                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogAnimated.dismiss();
                    }
                });
                editText=dialogAnimated.findViewById(R.id.editTextTextMultiLine2);
                editText.setText(description);
            }
        });
        Button totalimages=view.findViewById(R.id.totalimages);
        totalimages.setVisibility(View.GONE);

        ImageView profile=view.findViewById(R.id.profilefeed);
        feedImage=view.findViewById(R.id.imageoffeed);

        post=view.findViewById(R.id.postimg);
        Posted=postedornot;

        mAuth=FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        btnres=view.findViewById(R.id.btnres);

        donorname=DonorName;

        emailverified = context.getSharedPreferences("PREFERENCEemail", MODE_PRIVATE)
                .getString("emailverified","NULL");

        Indivkey=indivkey;
        String keyindvi = context.getSharedPreferences("PREFERENCE1", MODE_PRIVATE)
                .getString("initialize","NO");

        reportData=new reportData();
        reportRef=FirebaseDatabase.getInstance().getReference().child("Report");


        try {
            Glide.with(context).load(Dpurl).into(profile);
            //IF the app is crashing on theme change please look after this block.
            //and otherwise also.
        } catch (Exception e) {
            e.printStackTrace();
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        DeviceToken=Token;
        keyOfIndividual=keyfromFirebase;
        //database=FirebaseDatabase.getInstance();
        //ref=database.getReference().child("DonorsData");
        final boolean verifying = context.getSharedPreferences("PREFERENCE2", MODE_PRIVATE)
                .getBoolean("verify", false);

        donorphone=DonorPhone;
        receiveIdentity=getIdentity;
        gettingName = user.getDisplayName();//TODO:special characters.
        uqid=uniqueid;

        //Logic  Block

        if(category.equals("Food")){
            feedImage.setImageResource(R.drawable.food);
        }
        else if(category.equals("Clothes")){
            feedImage.setImageResource(R.drawable.cloth);
        }
        else if(category.equals("Old Newspapers")){
            feedImage.setImageResource(R.drawable.blanket);
        }
        else if(category.equals("Blood")){
            feedImage.setImageResource(R.drawable.blood);
        }
        else  if(category.equals("Educational")){
            feedImage.setImageResource(R.drawable.books);
        }
        else if(category.equals("Sack(बोरा)")){
            feedImage.setImageResource(R.drawable.animals);
        }
        else if(category.equals("Animal Rescue")){
            feedImage.setImageResource(R.drawable.ic_animalrescue);
        }
        else{
            feedImage.setImageResource(R.drawable.other);
        }

        if(verifying){
            receiveIdentity=user.getDisplayName();
        }
        else{
            receiveIdentity="individual";
        }

        //final String keyid=unique_key.getText().toString();

        try {
            if (!postedornot.equals("NULL")) {
                ViewGroup.LayoutParams params = post.getLayoutParams();
                post.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                post.requestLayout();

                Glide.with(context).load(pic1).into(post);
                if (length.equals("1")) {
                    post.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(firstFragment.isAdded())
                            {
                                return; //or return false/true, based on where you are calling from

                            }else {

                                FragmentTransaction t = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                                Bundle b2 = new Bundle();
                                b2.putString("sendingurl1", pic1);
                                b2.putString("sendingTotalImages", length);
                                firstFragment.setArguments(b2);
                                t.add(R.id.relate, firstFragment).addToBackStack(null);
                                t.commit();
                            }
                        }
                    });
                } else if (length.equals("2")) {
                    totalimages.setVisibility(View.VISIBLE);
                    totalimages.setText("+1");
                    post.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(firstFragment.isAdded())
                            {
                                return; //or return false/true, based on where you are calling from

                            }else {
                                FragmentTransaction t = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                                Bundle b2 = new Bundle();
                                b2.putString("sendingurl1", pic1);
                                b2.putString("sendingTotalImages", length);
                                b2.putString("sendingurl2", pic2);
                                firstFragment.setArguments(b2);
                                t.add(R.id.relate, firstFragment).addToBackStack(null);
                                t.commit();
                            }
                        }
                    });
                } else if (length.equals("3")) {
                    totalimages.setVisibility(View.VISIBLE);
                    totalimages.setText("+2");
                    post.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(firstFragment.isAdded())
                            {
                                return; //or return false/true, based on where you are calling from

                            }else {

                                FragmentTransaction t = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                                Bundle b2 = new Bundle();
                                b2.putString("sendingurl1", pic1);
                                b2.putString("sendingTotalImages", length);
                                b2.putString("sendingurl2", pic2);
                                b2.putString("sendingurl3", pic3);
                                firstFragment.setArguments(b2);
                                t.add(R.id.relate, firstFragment).addToBackStack(null);
                                t.commit();
                            }
                        }
                    });
                } else if (length.equals("4")) {
                    totalimages.setVisibility(View.VISIBLE);
                    totalimages.setText("+3");
                    post.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(firstFragment.isAdded())
                            {
                                return; //or return false/true, based on where you are calling from

                            }else {

                                FragmentTransaction t = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                                Bundle b2 = new Bundle();
                                b2.putString("sendingurl1", pic1);
                                b2.putString("sendingTotalImages", length);
                                b2.putString("sendingurl2", pic2);
                                b2.putString("sendingurl3", pic3);
                                b2.putString("sendingurl4", pic4);
                                firstFragment.setArguments(b2);
                                t.add(R.id.relate, firstFragment).addToBackStack(null);
                                t.commit();
                            }

                        }
                    });
                }

            } else if (postedornot.equals("NULL")) {
                ViewGroup.LayoutParams params = post.getLayoutParams();
                totalimages.setVisibility(View.GONE);
                params.height = 0;
                post.requestLayout();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!keyfromFirebase.equals(user.getUid())) {
                    dialogAnimateds = new Dialog(context, R.style.dialogstyletick);
                    dialogAnimateds.setContentView(R.layout.report_post_dialog);
                    dialogAnimateds.show();
                    delete = dialogAnimateds.findViewById(R.id.delete);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String push = reportRef.push().getKey();
                            String DeviceToken = FirebaseInstanceId.getInstance().getToken();

                            Calendar calobj = Calendar.getInstance();
                            DateFormat df = new SimpleDateFormat("dd/MM/yy hh:mm aa");
                            reporttm=df.format(calobj.getTime());

                            reportData.setReportTo(DonorName);
                            reportData.setReportedBy(user.getDisplayName());
                            reportData.setDeviceTokenofreportedByuser(DeviceToken);
                            reportData.setDeviceTokenofreporteduser(Token);
                            reportData.setUidofreporteduser(keyfromFirebase);
                            reportData.setUidofreportedbyuser(user.getUid());
                            reportData.setCaption(description);
                            reportData.setLocation(location);
                            reportData.setUniqueid(uniqueid);
                            reportData.setTime(reporttm);

                            developerreference = FirebaseDatabase.getInstance().getReference().child("ReportTokens");
                            developerreference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot sanp : snapshot.getChildren()) {

                                        String getvalue = snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);

                                        Specific specific = new Specific();
                                        specific.noti(user.getDisplayName(), "has Reported to = " + DonorName + " , Device Token = " + Token + " , Location = " + location + " , uid = " + keyfromFirebase + " , unique Node of Donors  =" + uniqueid, getvalue);

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            reportRef.child(push).setValue(reportData);
                            dialogAnimateds.dismiss();
                        }
                    });
                    cancel = dialogAnimateds.findViewById(R.id.button3);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogAnimateds.dismiss();
                        }
                    });
                }
            }
        });

        btnres.setText(task);
        btnres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!keyfromFirebase.equals(indivkey)) {
                    if (task.equals("Respond") && verifying && emailverified.equals(user.getEmail())) {

                        mAuth=FirebaseAuth.getInstance();
                        useruuid = mAuth.getCurrentUser();

                        LocationRequest request=new LocationRequest()
                                .setFastestInterval(1500)
                                .setInterval(3000)
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        builder=new LocationSettingsRequest.Builder()
                                .addLocationRequest(request);

                        Task<LocationSettingsResponse> result=
                                LocationServices.getSettingsClient(context).checkLocationSettings(builder.build());

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
                                                resolvableApiException.startResolutionForResult((Activity) context,REQUEST_CHECK_CODE);
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

                        getName= gettingName;
                        hold_key=null;
                        hold_id=uqid;

                        getDonorName=donorname;
                        getDonorPhone=donorphone;
                        getLocation=location;
                        getCategory=category;
                        Tokend=DeviceToken;
                        gettingTYPE=type;

                        database=FirebaseDatabase.getInstance();
                        ref=database.getReference().child("Donors");

                        dialogAnimated=new Dialog(context,R.style.dialogstyletick);
                        dialogAnimated.setContentView(R.layout.confirmdialog);
                        dialogAnimated.show();
                        TextView txtclose=(TextView)dialogAnimated.findViewById(R.id.txtclose);
                        txtclose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogAnimated.dismiss();
                            }
                        });
                        confirm=dialogAnimated.findViewById(R.id.confirm);
                        TextView name=dialogAnimated.findViewById(R.id.Namecon);
                        TextView locate=dialogAnimated.findViewById(R.id.locationcon);
                        TextView phone=dialogAnimated.findViewById(R.id.phonecon);
                        maps=dialogAnimated.findViewById(R.id.maps);

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
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                }
                                else {
                                    String uri = "http://maps.google.com/maps?daddr=" + latitude + "," + longitude + " (" + "Where the party is at" + ")";
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                    intent.setPackage("com.google.android.apps.maps");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                }}
                        });

                        confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ref.child(hold_id).child("task").setValue("Confirmed");
                                ref.child(hold_id).child("confirmedby").setValue(getName);

                                ref=database.getReference().child("Users");

                                context.getSharedPreferences("showlevelanimationuserhasdonated", MODE_PRIVATE).edit()
                                        .putString("userhasdonated", "false").apply();

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
                                    member.setTime(time);
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
                                    member.setTime(time);
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

                                        if(dataSnapshot.child("images").exists()){
                                            dpurl=dataSnapshot.child("images").getValue(String.class);

                                        }
                                        else{
                                            dpurl="Null";
                                        }



                                        database=FirebaseDatabase.getInstance();
                                        referencetoTimeLine=database.getReference().child("Timeline");

                                        Calendar calobj = Calendar.getInstance();
                                        DateFormat df = new SimpleDateFormat("dd/MM/yy hh:mm aa");
                                        tm=df.format(calobj.getTime());

                                        member = new Member();
                                        member.setRespondedby(nameofNGO);
                                        member.setRespondeddp(dpurl);
                                        member.setRespondeduid(ngouid);
                                        member.setRespondedto(getDonorName);
                                        member.setRespondedtype(gettingTYPE);
                                        member.setTime(tm);

                                        referencetoTimeLine.child(hold_id).setValue(member);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                dialogAnimated.dismiss();
                                dialogAnimated=new Dialog(context,R.style.dialogstyletick);
                                dialogAnimated.setContentView(R.layout.transparentdialog);
                                dialogAnimated.show();

                                anim = AnimationUtils.loadAnimation(context, R.anim.anim_bottom_to_top);
                                animlevel = AnimationUtils.loadAnimation(context, R.anim.rotate_come);
                                animlevelback = AnimationUtils.loadAnimation(context, R.anim.rotate_back);

                                view11 = dialogAnimated.findViewById(R.id.view11);
                                view11.setVisibility(View.GONE);

                                view2 = dialogAnimated.findViewById(R.id.view16);
                                view2.setVisibility(View.GONE);

                                congrats =dialogAnimated.findViewById(R.id.congrats);
                                congrats.setVisibility(View.GONE);

                                levelincrease = dialogAnimated.findViewById(R.id.levelincrease);
                                levelincrease.setVisibility(View.GONE);

                                xpup = dialogAnimated.findViewById(R.id.xpup);
                                xpup.setVisibility(View.GONE);

                                xpup.setVisibility(View.VISIBLE);
                                MediaPlayer mp = MediaPlayer.create(context, R.raw.xpsound);
                                mp.start();

                                anim.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        xpup.setVisibility(View.GONE);

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
                                                        view11.setVisibility(View.VISIBLE);
                                                        MediaPlayer mp = MediaPlayer.create(context, R.raw.xpsound);
                                                        mp.start();
                                                        runanim = 1;

                                                    } else if (storetotallevel == 2) {
                                                        congrats.setVisibility(View.VISIBLE);
                                                        view11.setVisibility(View.VISIBLE);
                                                        MediaPlayer mp = MediaPlayer.create(context, R.raw.xpsound);
                                                        mp.start();
                                                        runanim = 1;

                                                    } else if (storetotallevel % 5 == 0 && storetotallevel != 0) {

                                                        congrats.setVisibility(View.VISIBLE);
                                                        view11.setVisibility(View.VISIBLE);
                                                        MediaPlayer mp = MediaPlayer.create(context, R.raw.xpsound);
                                                        mp.start();
                                                        runanim = 1;

                                                    }
                                                    if (runanim != 1) {
                                                        MediaPlayer mp1 = MediaPlayer.create(context, R.raw.xpsound);
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
                                                                        dialogAnimated.dismiss();
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
                                                else{
                                                    dialogAnimated.dismiss();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                                xpup.startAnimation(anim);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                                ///////////////////////

                                view11.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        congrats.setVisibility(View.GONE);
                                        view11.setVisibility(View.GONE);

                                        MediaPlayer mp1 = MediaPlayer.create(context, R.raw.xpsound);
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
                                                        dialogAnimated.dismiss();
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


                                //////////////////////
                            }
                        });




                    } else if (task.equals("Confirmed")) {
                        Toast.makeText(context, "Someone has already Responded", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Only Registered NGOs Can Respond!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    if(type.equals("DONATION")){
                        Toast.makeText(context, "You Can't Respond to Your Own Donation!", Toast.LENGTH_SHORT).show();
                    }
                    else if(type.equals("HELP"))
                    {
                        Toast.makeText(context, "You Can't Respond to Your Own Help Request!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        locate.setText(location);
        cate.setText(category);
        Time.setText(time);
        //============================================================================================================================

        //============================================================================================================================
    }

}

