package com.aryomtech.nsabilaspur;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;

public class HolderPickuptoHelp extends RecyclerView.ViewHolder {

    View view;
    ImageView greentick;
    ImageView taskdonetick;
    Button cancel;
    private final  int REQUEST_CHECK_CODE=8989;
    private LocationSettingsRequest.Builder builder;


    Button desopen;
    Dialog dialogAnimated;
    TextView editText;
    ImageView imageoffeed;
    ImageView locatemap;
    Button confirmtick;
    FirebaseDatabase databaseH;
    private FirebaseAuth mAuth;
    DatabaseReference refH,respondreference,referencetimeline,reffordonors,ref_for_Donors_data;
    String username,userdonorid;
    ImageView phonecall;
    static FirebaseUser user;
    String latitude="";
    String longitude="";

    public HolderPickuptoHelp(@NonNull View itemView) {
        super(itemView);

        view=itemView;
    }

    public void setView(Context context, String category, String naam, String address, String phoneno, String haveName, String uid,String description,String type,
                        String latitude,String longitude,String Done,String time){

        TextView locate=view.findViewById(R.id.location);
        TextView cate=view.findViewById(R.id.category);
        TextView Naam=view.findViewById(R.id.naam);
        TextView Num=view.findViewById(R.id.number);
        TextView date=view.findViewById(R.id.date);
        date.setText(time);
        cancel=view.findViewById(R.id.btncan);
        imageoffeed=view.findViewById(R.id.imageoffeed);
        phonecall=view.findViewById(R.id.phonecall);
        TextView TYPE=view.findViewById(R.id.Maincategory);
        greentick=view.findViewById(R.id.greentick);
        taskdonetick=view.findViewById(R.id.taskdonetick);
        taskdonetick.setVisibility(View.GONE);

        /*LocationRequest request=new LocationRequest()
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
        });*/

        locatemap=view.findViewById(R.id.locate);
        locatemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(latitude.equals("") || longitude.equals("")){
                    Uri uri=Uri.parse("geo:0,0?q="+address);
                    Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                    intent.setPackage("com.google.android.apps.maps");
                    context.startActivity(intent);
                }
                else {
                    String uri = "http://maps.google.com/maps?daddr=" + latitude + "," + longitude + " (" + "Where the party is at" + ")";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    context.startActivity(intent);
                }
            }
        });

        if(type.equals("DONATION")){
            TYPE.setText(type);
        }
        else if(type.equals("HELP"))
        {
            TYPE.setText(type);
        }

        phonecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri number = Uri.parse("tel:" + phoneno);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                context.startActivity(callIntent);
            }
        });


        mAuth=FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        locate.setText(address);
        cate.setText(category);
        Naam.setText(naam);
        Num.setText(phoneno);
        username=haveName;
        userdonorid=uid;


        desopen=view.findViewById(R.id.desopen);
        desopen.setVisibility(View.GONE);

        if(Done.equals("yes")){
            taskdonetick.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.GONE);
            try {
                desopen.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            phonecall.setVisibility(View.GONE);
            greentick.setVisibility(View.GONE);
        }
        if(Done.equals("no")){
            taskdonetick.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);
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
            phonecall.setVisibility(View.VISIBLE);
            greentick.setVisibility(View.VISIBLE);
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

        if(category.equals("Food")){
            imageoffeed.setImageResource(R.drawable.food);
        }
        else if(category.equals("Clothes")){
            imageoffeed.setImageResource(R.drawable.cloth);
        }
        else if(category.equals("Old Newspapers")){
            imageoffeed.setImageResource(R.drawable.blanket);
        }
        else if(category.equals("Blood")){
            imageoffeed.setImageResource(R.drawable.blood);
        }
        else  if(category.equals("Educational")){
            imageoffeed.setImageResource(R.drawable.books);
        }
        else if(category.equals("Sack(बोरा)")){
            imageoffeed.setImageResource(R.drawable.animals);
        }
        else if(category.equals("Animal Rescue")){
            imageoffeed.setImageResource(R.drawable.ic_animalrescue);
        }
        else{
            imageoffeed.setImageResource(R.drawable.other);
        }
        databaseH=FirebaseDatabase.getInstance();
        refH=databaseH.getReference().child("Users");

        //TODO : category wise photo in the card view and edittext should be replaced by the card view for corner radius
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refH.child(user.getUid()).child(user.getDisplayName()).child("ConfirmIdHelp").child(userdonorid).removeValue();

                refH=databaseH.getReference().child("Donors");
                refH.child(userdonorid).child("task").setValue("Respond");

                context.getSharedPreferences("showlevelanimationuserhasdonated", MODE_PRIVATE).edit()
                        .putString("userhasdonated", "false").apply();

                referencetimeline=databaseH.getReference().child("Timeline");
                referencetimeline.child(userdonorid).removeValue();

                respondreference=databaseH.getReference().child("DonorsData");
                respondreference.child(user.getUid()).child("playData").child("Responds").child(userdonorid).removeValue();
                respondreference.child(user.getUid()).child("playData").child("weeklyProgress").child("Responds").child(userdonorid).removeValue();

                refH.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.child(userdonorid).child("loc").exists()){
                            refH.child(userdonorid).removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
       greentick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogAnimated=new Dialog(context,R.style.dialogstyletick);
                dialogAnimated.setContentView(R.layout.tickdialogbox);
                dialogAnimated.show();
                TextView txtclose=(TextView)dialogAnimated.findViewById(R.id.txtclose);
                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogAnimated.dismiss();
                    }
                });
                confirmtick=dialogAnimated.findViewById(R.id.confirmdialog);
                confirmtick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        taskdonetick.setVisibility(View.VISIBLE);
                        cancel.setVisibility(View.GONE);
                        desopen.setVisibility(View.GONE);
                        phonecall.setVisibility(View.GONE);
                        greentick.setVisibility(View.GONE);

                        refH=databaseH.getReference().child("Users");
                        refH.child(user.getUid()).child(user.getDisplayName()).child("ConfirmIdHelp").child(userdonorid).child("done").setValue("yes");

                        reffordonors=databaseH.getReference().child("Donors");
                        reffordonors.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String donorsuid=snapshot.child(userdonorid).child("individualKey").getValue(String.class);

                                ref_for_Donors_data=databaseH.getReference().child("DonorsData");
                                ref_for_Donors_data.child(donorsuid).child("ConfirmedHELP").child(userdonorid).child("done").setValue("yes");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        dialogAnimated.dismiss();
                    }
                });

            }
        });
    }
}

