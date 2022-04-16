package com.aryomtech.nsabilaspur;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.watermark.androidwm.WatermarkBuilder;
import com.watermark.androidwm.bean.WatermarkImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;


public class Addphotos extends AppCompatActivity {

    private static final String TAG = Addphotos.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;

    FirebaseAuth auth;
    FirebaseUser useruuid;

    Boolean checkOrder=false;
    ImageView pic1;
    Uri mImageUri;
    String dpurl;

    String getOrg="";
    boolean connected = false;
    String tosent="";

    String latitude="",longitude="";
    ArrayList<Uri> urlarray;
    ArrayList<Uri> uriarray;
    int count=0;
    int countervariable=0;
    WatermarkImage watermarkImage;
    int total=0;
    Bitmap bitmap;
    Uri uri;
    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;
    RecyclerView gvGallery;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    ArrayList<Uri> mArrayUri;

    CardView card1,card2,card3,card4;
    ImageView pic2,pic3,pic4;

    boolean once=true;
    boolean postsuccessfully=false;
    boolean onceSelectngo=true;

    String checknum;
    String category;
    String mx;
    String checkloc;
    String holdintentname;
    String taskc;
    String checkname;
    Boolean checkselection=false;
    String individualkey;
    String time;
    String DeviceToken;
    String pushkey;
    String Description;
    String TYPE;
    Member member;
    String receivingIdentity;
    ArrayList<String> str_ArrayList;
    FirebaseDatabase database;
    DatabaseReference ref,reference,referenceFordonorsData;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addphotos);
        auth=FirebaseAuth.getInstance();
        useruuid=auth.getCurrentUser();

        ImagePickerActivity.clearCache(this);

        checkselection = true;

        urlarray= new ArrayList<Uri>();

        card1=findViewById(R.id.cardView1);
        card2=findViewById(R.id.cardView2);
        card3=findViewById(R.id.cardView3);
        card4=findViewById(R.id.cardView4);

        pic1=findViewById(R.id.plus1);
        pic2=findViewById(R.id.plus2);
        pic3=findViewById(R.id.plus3);
        pic4=findViewById(R.id.plus4);

        Intent data=getIntent();

        ref= FirebaseDatabase.getInstance().getReference().child("Donors");
        reference= FirebaseDatabase.getInstance().getReference().child("Accounts");

        checknum=data.getStringExtra("key1");
        category=data.getStringExtra("category");
        mx=data.getStringExtra("sendingmax");
        checkloc=data.getStringExtra("checkloc");
        taskc=data.getStringExtra("sendingtask");
        checkname=data.getStringExtra("key2");
        individualkey=data.getStringExtra("sendingindividualkey");
        time=data.getStringExtra("sendingtime");
        DeviceToken=data.getStringExtra("sendingdevicetoken");
        pushkey=data.getStringExtra("key3");
        receivingIdentity=data.getStringExtra("sendingIdentity");
        holdintentname=data.getStringExtra("intentname");
        Description=data.getStringExtra("sendingdescription2");
        TYPE=data.getStringExtra("sendingthetypeofTYPE");
        latitude=data.getStringExtra("sendinglatitude");
        longitude=data.getStringExtra("sendinglongitude");

        //ArrayList initialization
        str_ArrayList= new ArrayList<String>();

        member=new Member();



        Window window = Addphotos.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(Addphotos.this, R.color.statusbar));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setNavigationBarColor(ContextCompat.getColor(Addphotos.this, R.color.statusbar));
        }

    }

    private void loadProfile1(String url) {
        Log.d(TAG, "Image cache path: " + url);
        try {
            Glide.with(this).load(url)
                    .into(pic1);
            pic1.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadProfile2(String url) {
        Log.d(TAG, "Image cache path: " + url);
        try {
            Glide.with(this).load(url)
                    .into(pic2);
            pic1.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadProfile3(String url) {
        Log.d(TAG, "Image cache path: " + url);
        try {
            Glide.with(this).load(url)
                    .into(pic3);
            pic1.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadProfile4(String url) {
        Log.d(TAG, "Image cache path: " + url);
        try {
            Glide.with(this).load(url)
                    .into(pic4);
            pic1.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                // Set uri as Image in the ImageView:
                try {
                    // You can update this bitmap to your server
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver() , Uri.parse(String.valueOf(resultUri)));
                    if(countervariable==1) {
                        loadProfile1(resultUri.toString());
                        once=true;
                    }
                    else if(countervariable==2)
                    {
                        loadProfile2(resultUri.toString());
                        once=true;
                    }
                    else if(countervariable==3){
                        loadProfile3(resultUri.toString());
                        once=true;
                    }
                    else if(countervariable==4){
                        loadProfile4(resultUri.toString());
                        once=true;
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            checkOrder=false;
        }
    }



    private void uploadPicture(Uri urii,final int checkitems) {

        final ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.setCancelable(false);
        pd.show();
        final String randomKey= UUID.randomUUID().toString();
        StorageReference riversRef = FirebaseStorage.getInstance().getReference().child("Users/"+randomKey+".png");

        riversRef.putFile(urii)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        //TODO:if error is here then find the alternative.
                        try {
                            if (holdintentname.equals("giverblanket")) {
                                giverblanket.fab.finish();
                            } else if (holdintentname.equals("giverblood")) {
                                giverblood.fablood.finish();
                            } else if (holdintentname.equals("givercloth")) {
                                givercloth.fabcloth.finish();
                            } else if (holdintentname.equals("giverfood")) {
                                giverfood.fa.finish();
                            } else if (holdintentname.equals("giverother")) {
                                giverother.other.finish();
                            } else if (holdintentname.equals("giverstudy")) {
                                giverstudy.study.finish();
                            } else if (holdintentname.equals("giversack")) {
                                giversack.fabsack.finish();
                            } else if (holdintentname.equals("giverrescue")) {
                                giverrescue.fabrescue.finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(
                                new OnCompleteListener<Uri>() {

                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        String fileLink = task.getResult().toString();

                                        str_ArrayList.add(fileLink+"");

                                        count=count+1;
                                        try {
                                            pd.dismiss();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        long delayInMillis = 2000;
                                        Timer timer = new Timer();
                                        timer.schedule(new TimerTask() {
                                            @Override
                                            public void run() {
                                                if(count==checkitems){
                                                    FlushToFirebase(str_ArrayList);
                                                }
                                            }
                                        }, delayInMillis);
                                    }
                                });

                        ///
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed To Upload", Toast.LENGTH_LONG).show();


                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot tasksnapshot) {
                double progressPercent=(100.00 * tasksnapshot.getBytesTransferred() / tasksnapshot.getTotalByteCount());
                pd.setMessage("Progress: "+(int) progressPercent+"%");
            }
        });
    }

    private void FlushToFirebase(ArrayList<String> url_arrayList) {
        int length=url_arrayList.size();

        dpurl = getSharedPreferences("dpURL", MODE_PRIVATE)
                .getString("dpurlforstoring","NULL");
        if(length==1){

            String done="no";
            member.setLoc(checkloc);
            member.setName(checkname);
            member.setPhone(checknum);
            member.setCategory(category);
            member.setKey(mx);
            member.setTask(taskc);
            member.setUniqueid(pushkey);
            member.setIndividualKey(useruuid.getUid());
            member.setTime(time);
            member.setDevicetoken(DeviceToken);
            member.setPosted("YES");
            member.setCount(length+"");
            member.setPic1(url_arrayList.get(0));
            member.setPic2("Null");
            member.setPic3("Null");
            member.setPic4("Null");
            member.setDp(dpurl);
            member.setDescription(Description);
            member.setType(TYPE);
            member.setLatitude(latitude);
            member.setLongitude(longitude);
            member.setDone(done);

////======================================================================================================================
            getSharedPreferences("storelocationtemp",MODE_PRIVATE).edit()
                    .putString("id:495004location",checkloc).apply();

            getSharedPreferences("storenametemp",MODE_PRIVATE).edit()
                    .putString("id:1234567890name",checkname).apply();

            getSharedPreferences("storenumbertemp",MODE_PRIVATE).edit()
                    .putString("id:0987456123phone",checknum).apply();
//========================================================================================================================

            ref.child(pushkey).setValue(member);

            referenceFordonorsData= FirebaseDatabase.getInstance().getReference().child("DonorsData");

            if(TYPE.equals("DONATION")) {
                referenceFordonorsData.child(useruuid.getUid()).child("ConfirmDonation").child(pushkey).setValue(member);
            }
            else if(TYPE.equals("HELP")){
                referenceFordonorsData.child(useruuid.getUid()).child("ConfirmedHELP").child(pushkey).setValue(member);
            }

            try {
                if (holdintentname.equals("giverblanket")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                getOrg=snapshot.child(sanp.getKey()).child("organization").getValue(String.class);
                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                try {

                                    if (getOrg.equals("DHITI") && tosent.equals("YES")) {
                                        String getvalue=snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                        Specific specific = new Specific();
                                        specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
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

                } else if (holdintentname.equals("giverblood")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                getOrg=snapshot.child(sanp.getKey()).child("organization").getValue(String.class);
                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                try {
                                    if (getOrg.equals("JAZBAA") && tosent.equals("YES")) {

                                        String getvalue=snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                        Specific specific = new Specific();
                                        specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
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


                }
                else if (holdintentname.equals("giverfood")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                getOrg=snapshot.child(sanp.getKey()).child("organization").getValue(String.class);
                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                try {

                                    if (getOrg.equals("DHITI") && tosent.equals("YES")) {
                                        String getvalue=snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                        Specific specific = new Specific();
                                        specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
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

                } else if (holdintentname.equals("giverother")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                if(tosent.equals("YES")) {
                                    String getvalue = snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                    Specific specific = new Specific();
                                    specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else if (holdintentname.equals("giverstudy")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                getOrg=snapshot.child(sanp.getKey()).child("organization").getValue(String.class);
                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                try {

                                    if (getOrg.equals("DHITI") && tosent.equals("YES")) {
                                        String getvalue=snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                        Specific specific = new Specific();
                                        specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
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

                } else if (holdintentname.equals("giversack")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                getOrg=snapshot.child(sanp.getKey()).child("organization").getValue(String.class);
                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                try {
                                    if (getOrg.equals("BEZUBAAN BSP") && tosent.equals("YES")) {

                                        String getvalue=snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                        Specific specific = new Specific();
                                        specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
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

                } else if (holdintentname.equals("giverrescue")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                getOrg=snapshot.child(sanp.getKey()).child("organization").getValue(String.class);
                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                try {
                                    if (getOrg.equals("BEZUBAAN BSP") && tosent.equals("YES")) {

                                        String getvalue=snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                        Specific specific = new Specific();
                                        specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
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

                }
            } catch (Exception e) {
                e.printStackTrace();
            }




            Intent foodkt=new Intent(Addphotos.this,MainActivity3.class);
            foodkt.putExtra("key1", checknum);
            foodkt.putExtra("category",category);
            foodkt.putExtra("sendingmax", mx);
            foodkt.putExtra("checkloc",checkloc);
            foodkt.putExtra("sendingtask",taskc);
            foodkt.putExtra("key2", checkname);
            foodkt.putExtra("sendingindividualkey", useruuid.getUid());
            foodkt.putExtra("sendingtime", time);
            foodkt.putExtra("sendingdevicetoken", DeviceToken);
            foodkt.putExtra("key3", pushkey);
            foodkt.putExtra("sendingIdentity", receivingIdentity);
            foodkt.putExtra("sendingpushkey", pushkey);
            foodkt.putExtra("sendingthetypeofTYPE",TYPE);
            foodkt.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(foodkt);

            finish();


        }
        else if(length==2){

            String done="no";
            member.setLoc(checkloc);
            member.setName(checkname);
            member.setPhone(checknum);
            member.setCategory(category);
            member.setKey(mx);
            member.setTask(taskc);
            member.setUniqueid(pushkey);
            member.setIndividualKey(useruuid.getUid());
            member.setTime(time);
            member.setDevicetoken(DeviceToken);
            member.setPosted("YES");
            member.setCount(length+"");
            member.setPic1(url_arrayList.get(0));
            member.setPic2(url_arrayList.get(1));
            member.setPic3("Null");
            member.setPic4("Null");
            member.setDp(dpurl);
            member.setDescription(Description);
            member.setType(TYPE);
            member.setLatitude(latitude);
            member.setLongitude(longitude);
            member.setDone(done);

            ref.child(pushkey).setValue(member);


////======================================================================================================================
            getSharedPreferences("storelocationtemp",MODE_PRIVATE).edit()
                    .putString("id:495004location",checkloc).apply();

            getSharedPreferences("storenametemp",MODE_PRIVATE).edit()
                    .putString("id:1234567890name",checkname).apply();

            getSharedPreferences("storenumbertemp",MODE_PRIVATE).edit()
                    .putString("id:0987456123phone",checknum).apply();
//========================================================================================================================

            referenceFordonorsData= FirebaseDatabase.getInstance().getReference().child("DonorsData");

            if(TYPE.equals("DONATION")) {
                referenceFordonorsData.child(useruuid.getUid()).child("ConfirmDonation").child(pushkey).setValue(member);
            }
            else if(TYPE.equals("HELP")){
                referenceFordonorsData.child(useruuid.getUid()).child("ConfirmedHELP").child(pushkey).setValue(member);
            }

            try {
                if (holdintentname.equals("giverblanket")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                getOrg=snapshot.child(sanp.getKey()).child("organization").getValue(String.class);
                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                try {

                                    if (getOrg.equals("DHITI") && tosent.equals("YES")) {
                                        String getvalue=snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                        Specific specific = new Specific();
                                        specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
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

                } else if (holdintentname.equals("giverblood")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                getOrg=snapshot.child(sanp.getKey()).child("organization").getValue(String.class);
                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                try {
                                    if (getOrg.equals("JAZBAA") && tosent.equals("YES")) {

                                        String getvalue=snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                        Specific specific = new Specific();
                                        specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
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


                }
                else if (holdintentname.equals("giverfood")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                getOrg=snapshot.child(sanp.getKey()).child("organization").getValue(String.class);
                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                try {

                                    if (getOrg.equals("DHITI") && tosent.equals("YES")) {
                                        String getvalue=snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                        Specific specific = new Specific();
                                        specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
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

                } else if (holdintentname.equals("giverother")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                if(tosent.equals("YES")) {
                                    String getvalue = snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                    Specific specific = new Specific();
                                    specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else if (holdintentname.equals("giverstudy")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                getOrg=snapshot.child(sanp.getKey()).child("organization").getValue(String.class);
                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                try {

                                    if (getOrg.equals("DHITI") && tosent.equals("YES")) {
                                        String getvalue=snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                        Specific specific = new Specific();
                                        specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
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

                } else if (holdintentname.equals("giversack")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                getOrg=snapshot.child(sanp.getKey()).child("organization").getValue(String.class);
                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                try {
                                    if (getOrg.equals("BEZUBAAN BSP") && tosent.equals("YES")) {

                                        String getvalue=snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                        Specific specific = new Specific();
                                        specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
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

                } else if (holdintentname.equals("giverrescue")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                getOrg=snapshot.child(sanp.getKey()).child("organization").getValue(String.class);
                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                try {
                                    if (getOrg.equals("BEZUBAAN BSP") && tosent.equals("YES")) {

                                        String getvalue=snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                        Specific specific = new Specific();
                                        specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
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

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Intent foodkt=new Intent(Addphotos.this,MainActivity3.class);
            foodkt.putExtra("key1", checknum);
            foodkt.putExtra("category",category);
            foodkt.putExtra("sendingmax", mx);
            foodkt.putExtra("checkloc",checkloc);
            foodkt.putExtra("sendingtask",taskc);
            foodkt.putExtra("key2", checkname);
            foodkt.putExtra("sendingindividualkey", useruuid.getUid());
            foodkt.putExtra("sendingtime", time);
            foodkt.putExtra("sendingdevicetoken", DeviceToken);
            foodkt.putExtra("key3", pushkey);
            foodkt.putExtra("sendingIdentity", receivingIdentity);
            foodkt.putExtra("sendingpushkey", pushkey);
            foodkt.putExtra("sendingthetypeofTYPE",TYPE);
            foodkt.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(foodkt);

            finish();
        }
        else if(length==3){

            String done="no";
            member.setLoc(checkloc);
            member.setName(checkname);
            member.setPhone(checknum);
            member.setCategory(category);
            member.setKey(mx);
            member.setTask(taskc);
            member.setUniqueid(pushkey);
            member.setIndividualKey(useruuid.getUid());
            member.setTime(time);
            member.setDevicetoken(DeviceToken);
            member.setPosted("YES");
            member.setCount(length+"");
            member.setPic1(url_arrayList.get(0));
            member.setPic2(url_arrayList.get(1));
            member.setPic3(url_arrayList.get(2));
            member.setPic4("Null");
            member.setDp(dpurl);
            member.setDescription(Description);
            member.setType(TYPE);
            member.setLatitude(latitude);
            member.setLongitude(longitude);
            member.setDone(done);

            ref.child(pushkey).setValue(member);

            referenceFordonorsData= FirebaseDatabase.getInstance().getReference().child("DonorsData");

////======================================================================================================================
            getSharedPreferences("storelocationtemp",MODE_PRIVATE).edit()
                    .putString("id:495004location",checkloc).apply();

            getSharedPreferences("storenametemp",MODE_PRIVATE).edit()
                    .putString("id:1234567890name",checkname).apply();

            getSharedPreferences("storenumbertemp",MODE_PRIVATE).edit()
                    .putString("id:0987456123phone",checknum).apply();
//========================================================================================================================

            if(TYPE.equals("DONATION")) {
                referenceFordonorsData.child(useruuid.getUid()).child("ConfirmDonation").child(pushkey).setValue(member);
            }
            else if(TYPE.equals("HELP")){
                referenceFordonorsData.child(useruuid.getUid()).child("ConfirmedHELP").child(pushkey).setValue(member);
            }

            try {
                if (holdintentname.equals("giverblanket")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                getOrg=snapshot.child(sanp.getKey()).child("organization").getValue(String.class);
                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                try {

                                    if (getOrg.equals("DHITI") && tosent.equals("YES")) {
                                        String getvalue=snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                        Specific specific = new Specific();
                                        specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
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

                } else if (holdintentname.equals("giverblood")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                getOrg=snapshot.child(sanp.getKey()).child("organization").getValue(String.class);
                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                try {
                                    if (getOrg.equals("JAZBAA") && tosent.equals("YES")) {

                                        String getvalue=snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                        Specific specific = new Specific();
                                        specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
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


                }
                else if (holdintentname.equals("giverfood")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                getOrg=snapshot.child(sanp.getKey()).child("organization").getValue(String.class);
                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                try {

                                    if (getOrg.equals("DHITI") && tosent.equals("YES")) {
                                        String getvalue=snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                        Specific specific = new Specific();
                                        specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
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

                } else if (holdintentname.equals("giverother")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                if(tosent.equals("YES")) {
                                    String getvalue = snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                    Specific specific = new Specific();
                                    specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else if (holdintentname.equals("giverstudy")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                getOrg=snapshot.child(sanp.getKey()).child("organization").getValue(String.class);
                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                try {

                                    if (getOrg.equals("DHITI") && tosent.equals("YES")) {
                                        String getvalue=snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                        Specific specific = new Specific();
                                        specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
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

                } else if (holdintentname.equals("giversack")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                getOrg=snapshot.child(sanp.getKey()).child("organization").getValue(String.class);
                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                try {
                                    if (getOrg.equals("BEZUBAAN BSP") && tosent.equals("YES")) {

                                        String getvalue=snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                        Specific specific = new Specific();
                                        specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
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

                } else if (holdintentname.equals("giverrescue")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                getOrg=snapshot.child(sanp.getKey()).child("organization").getValue(String.class);
                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                try {
                                    if (getOrg.equals("BEZUBAAN BSP") && tosent.equals("YES")) {

                                        String getvalue=snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                        Specific specific = new Specific();
                                        specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
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

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Intent foodkt=new Intent(Addphotos.this,MainActivity3.class);
            foodkt.putExtra("key1", checknum);
            foodkt.putExtra("category",category);
            foodkt.putExtra("sendingmax", mx);
            foodkt.putExtra("checkloc",checkloc);
            foodkt.putExtra("sendingtask",taskc);
            foodkt.putExtra("key2", checkname);
            foodkt.putExtra("sendingindividualkey", useruuid.getUid());
            foodkt.putExtra("sendingtime", time);
            foodkt.putExtra("sendingdevicetoken", DeviceToken);
            foodkt.putExtra("key3", pushkey);
            foodkt.putExtra("sendingIdentity", receivingIdentity);
            foodkt.putExtra("sendingpushkey", pushkey);
            foodkt.putExtra("sendingthetypeofTYPE",TYPE);
            foodkt.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(foodkt);

            finish();
        }
        else if(length==4){

            String done="no";
            member.setLoc(checkloc);
            member.setName(checkname);
            member.setPhone(checknum);
            member.setCategory(category);
            member.setKey(mx);
            member.setTask(taskc);
            member.setUniqueid(pushkey);
            member.setIndividualKey(useruuid.getUid());
            member.setTime(time);
            member.setDevicetoken(DeviceToken);
            member.setPosted("YES");
            member.setCount(length+"");
            member.setPic1(url_arrayList.get(0));
            member.setPic2(url_arrayList.get(1));
            member.setPic3(url_arrayList.get(2));
            member.setPic4(url_arrayList.get(3));
            member.setDp(dpurl);
            member.setDescription(Description);
            member.setType(TYPE);
            member.setLatitude(latitude);
            member.setLongitude(longitude);
            member.setDone(done);

            ref.child(pushkey).setValue(member);

            referenceFordonorsData= FirebaseDatabase.getInstance().getReference().child("DonorsData");

////======================================================================================================================
            getSharedPreferences("storelocationtemp",MODE_PRIVATE).edit()
                    .putString("id:495004location",checkloc).apply();

            getSharedPreferences("storenametemp",MODE_PRIVATE).edit()
                    .putString("id:1234567890name",checkname).apply();

            getSharedPreferences("storenumbertemp",MODE_PRIVATE).edit()
                    .putString("id:0987456123phone",checknum).apply();
//========================================================================================================================

            if(TYPE.equals("DONATION")) {
                referenceFordonorsData.child(useruuid.getUid()).child("ConfirmDonation").child(pushkey).setValue(member);
            }
            else if(TYPE.equals("HELP")){
                referenceFordonorsData.child(useruuid.getUid()).child("ConfirmedHELP").child(pushkey).setValue(member);
            }

            try {
                if (holdintentname.equals("giverblanket")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                getOrg=snapshot.child(sanp.getKey()).child("organization").getValue(String.class);
                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                try {

                                    if (getOrg.equals("DHITI") && tosent.equals("YES")) {
                                        String getvalue=snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                        Specific specific = new Specific();
                                        specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
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

                } else if (holdintentname.equals("giverblood")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                getOrg=snapshot.child(sanp.getKey()).child("organization").getValue(String.class);
                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                try {
                                    if (getOrg.equals("JAZBAA") && tosent.equals("YES")) {

                                        String getvalue=snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                        Specific specific = new Specific();
                                        specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
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


                }
                else if (holdintentname.equals("giverfood")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                getOrg=snapshot.child(sanp.getKey()).child("organization").getValue(String.class);
                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                try {

                                    if (getOrg.equals("DHITI") && tosent.equals("YES")) {
                                        String getvalue=snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                        Specific specific = new Specific();
                                        specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
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

                } else if (holdintentname.equals("giverother")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                if(tosent.equals("YES")) {
                                    String getvalue = snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                    Specific specific = new Specific();
                                    specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else if (holdintentname.equals("giverstudy")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                getOrg=snapshot.child(sanp.getKey()).child("organization").getValue(String.class);
                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                try {

                                    if (getOrg.equals("DHITI") && tosent.equals("YES")) {
                                        String getvalue=snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                        Specific specific = new Specific();
                                        specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
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

                } else if (holdintentname.equals("giversack")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                getOrg=snapshot.child(sanp.getKey()).child("organization").getValue(String.class);
                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                try {
                                    if (getOrg.equals("BEZUBAAN BSP") && tosent.equals("YES")) {

                                        String getvalue=snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                        Specific specific = new Specific();
                                        specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
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

                } else if (holdintentname.equals("giverrescue")) {

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sanp:snapshot.getChildren()){

                                getOrg=snapshot.child(sanp.getKey()).child("organization").getValue(String.class);
                                tosent=snapshot.child(sanp.getKey()).child("toSend").getValue(String.class);

                                try {
                                    if (getOrg.equals("BEZUBAAN BSP") && tosent.equals("YES")) {

                                        String getvalue=snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                                        Specific specific = new Specific();
                                        specific.noti(TYPE, category + " ( " + checkloc.trim() + " ). " + "Click To Respond", getvalue);
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

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Intent foodkt=new Intent(Addphotos.this,MainActivity3.class);
            foodkt.putExtra("key1", checknum);
            foodkt.putExtra("category",category);
            foodkt.putExtra("sendingmax", mx);
            foodkt.putExtra("checkloc",checkloc);
            foodkt.putExtra("sendingtask",taskc);
            foodkt.putExtra("key2", checkname);
            foodkt.putExtra("sendingindividualkey", useruuid.getUid());
            foodkt.putExtra("sendingtime", time);
            foodkt.putExtra("sendingdevicetoken", DeviceToken);
            foodkt.putExtra("key3", pushkey);
            foodkt.putExtra("sendingIdentity", receivingIdentity);
            foodkt.putExtra("sendingpushkey", pushkey);
            foodkt.putExtra("sendingthetypeofTYPE",TYPE);
            foodkt.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(foodkt);

            finish();
        }
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    public void post(View view) {

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
                Snackbar.make(findViewById(android.R.id.content),"No NetWork Connection Found!!",Snackbar.LENGTH_LONG).show();

            }
        }else {
            try {
                if (checkOrder && checkselection && once) {

                    once = false;

                    if (!(pic1.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.addpics).getConstantState())) {

                        pic1.invalidate();
                        BitmapDrawable drawable1 = (BitmapDrawable) pic1.getDrawable();
                        Bitmap bitmap1 = drawable1.getBitmap();
                        getImageUri(getApplicationContext(), bitmap1);
                        total = 1;

                        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibe.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            //deprecated in API 26
                            vibe.vibrate(500);
                        }
                        postsuccessfully = true;

                    }


                    if (!(pic2.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.addpics).getConstantState())) {

                        pic2.invalidate();
                        total = 2;
                        BitmapDrawable drawable2 = (BitmapDrawable) pic2.getDrawable();
                        Bitmap bitmap2 = drawable2.getBitmap();
                        getImageUri(getApplicationContext(), bitmap2);


                    }
                    if (!(pic3.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.addpics).getConstantState())) {

                        total = 3;
                        pic3.invalidate();
                        BitmapDrawable drawable3 = (BitmapDrawable) pic3.getDrawable();
                        Bitmap bitmap3 = drawable3.getBitmap();
                        getImageUri(getApplicationContext(), bitmap3);


                    }
                    if (!(pic4.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.addpics).getConstantState())) {
                        total = 4;
                        pic4.invalidate();
                        BitmapDrawable drawable4 = (BitmapDrawable) pic4.getDrawable();
                        Bitmap bitmap4 = drawable4.getBitmap();
                        getImageUri(getApplicationContext(), bitmap4);


                    }

                    if (postsuccessfully) {
                        postfun();
                    }
                }

            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    private void postfun() {

        int len=urlarray.size();
        for(int i=0;i<len;i++)
        {
            uploadPicture(urlarray.get(i),len);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        final String randomKey= UUID.randomUUID().toString();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, randomKey+"", null);
        urlarray.add(Uri.parse(path));

        return Uri.parse(path);
    }

    public void cancelposting(View view) {
        finish();
    }
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }
    public void onProfileImageClick1(View view) {

        if(checkselection) {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setActivityTitle("NSA")
                    .setAspectRatio(1, 1) //You can skip this for free form aspect ratio)
                    .start(Addphotos.this);

            countervariable = 1;
            checkOrder = true;
        }
        else{
            Snackbar.make(findViewById(android.R.id.content),"Select the Ngo Category First!!",Snackbar.LENGTH_LONG).show();

        }

    }

    public void onProfileImageClick2(View view) {
        if(checkselection) {
            if (pic1.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.addpics).getConstantState()) {
                Snackbar.make(findViewById(android.R.id.content),"Please Select the Image Of First Card first",Snackbar.LENGTH_LONG).show();
            } else {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setActivityTitle("NSA")
                        .setAspectRatio(1, 1) //You can skip this for free form aspect ratio)
                        .start(Addphotos.this);
                countervariable = 2;
                checkOrder = true;
            }
        }
        else{
            Snackbar.make(findViewById(android.R.id.content),"Select the Ngo Category First!!",Snackbar.LENGTH_LONG).show();
        }



    }

    public void onProfileImageClick3(View view) {
        if(checkselection) {
            if (pic1.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.addpics).getConstantState()) {
                Snackbar.make(findViewById(android.R.id.content),"Please Select the Image Of First Card first",Snackbar.LENGTH_LONG).show();

            } else if (pic2.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.addpics).getConstantState()) {
                Snackbar.make(findViewById(android.R.id.content),"Please Select the Image Of Second Card First",Snackbar.LENGTH_LONG).show();

            } else {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setActivityTitle("NSA")
                        .setAspectRatio(1, 1) //You can skip this for free form aspect ratio)
                        .start(Addphotos.this);
                checkOrder = true;
                countervariable = 3;
            }
        }
        else{
            Snackbar.make(findViewById(android.R.id.content),"Select the Ngo Category First!!",Snackbar.LENGTH_LONG).show();
        }



    }

    public void onProfileImageClick4(View view) {
        if (checkselection) {
            if (pic1.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.addpics).getConstantState()) {

                Snackbar.make(findViewById(android.R.id.content),"Please Select the Image Of First Card first",Snackbar.LENGTH_LONG).show();

            } else if (pic2.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.addpics).getConstantState()) {

                Snackbar.make(findViewById(android.R.id.content),"Please Select the Image Of Second Card First",Snackbar.LENGTH_LONG).show();

            } else if (pic3.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.addpics).getConstantState()) {
                Snackbar.make(findViewById(android.R.id.content),"Please Select the Image Of Third Card First",Snackbar.LENGTH_LONG).show();

            } else {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setActivityTitle("NSA")
                        .setAspectRatio(1, 1) //You can skip this for free form aspect ratio)
                        .start(Addphotos.this);

                checkOrder = true;
                countervariable = 4;
            }

        }
        else{
            Snackbar.make(findViewById(android.R.id.content),"Select the Ngo Category First!!",Snackbar.LENGTH_LONG).show();
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