package com.aryomtech.nsabilaspur;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;


public class HolderDonation extends RecyclerView.ViewHolder {

    View view;
    Button cancel;
    Button desopen;
    int total_responds=0;
    ImageView greentick;
    ImageView taskdonetick;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    FirebaseDatabase databaseD,database;
    DatabaseReference refD,referenceforplay,reflevelbadges,badgedata,reflimitbadges;
    String userdonorid;
    String Confirmedby;
    TextView editText;
    ImageView phonecall;
    String store_event_donation_number;

    int checkcurrentlevel=0;

    FirebaseAuth auth;
    Dialog dialogAnimated;
    FirebaseUser useruuid;
    ImageView imageoffeed;
    long Donorscount=0;
    public HolderDonation(@NonNull View itemView) {
        super(itemView);

        view=itemView;
    }
    public void setView(final Context context, String category, String naam, String address,
                        String phoneno, final String gettingKey, String uniqueId,String pic1url,String pic2url
                        ,String pic3url,String pic4url,String description,String Done,String time){
        TextView locate=view.findViewById(R.id.location);
        TextView cate=view.findViewById(R.id.category);
        TextView Naam=view.findViewById(R.id.naam);
        TextView Num=view.findViewById(R.id.number);
        TextView date=view.findViewById(R.id.date);
        date.setText(time);
        taskdonetick=view.findViewById(R.id.taskdonetick);
        taskdonetick.setVisibility(View.GONE);

        cancel=view.findViewById(R.id.btncan);
        imageoffeed=view.findViewById(R.id.imageoffeed);
        locate.setText(address);
        cate.setText(category);
        Naam.setText(naam);
        Num.setText(phoneno);
        userdonorid=uniqueId;

        auth=FirebaseAuth.getInstance();
        useruuid=auth.getCurrentUser();


        desopen=view.findViewById(R.id.desopen);
        desopen.setVisibility(View.GONE);



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
        else{
            imageoffeed.setImageResource(R.drawable.other);
        }

        if(Done.equals("yes")){
            taskdonetick.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.GONE);
            try {
                desopen.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        }

        databaseD=FirebaseDatabase.getInstance();
        refD=databaseD.getReference().child("DonorsData");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refD.child(gettingKey).child("ConfirmDonation").child(userdonorid).removeValue();
                refD.child(gettingKey).child("weeklyDonation").child(userdonorid).removeValue();
                try {
                    refD.child(useruuid.getUid()).child("EventDonation").child(userdonorid).removeValue();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                context.getSharedPreferences("showlevelanimationuserhasdonated", MODE_PRIVATE).edit()
                        .putString("userhasdonated", "false").apply();

                database = FirebaseDatabase.getInstance();
                referenceforplay = database.getReference().child("DonorsData");

                referenceforplay.child(useruuid.getUid()).child("status").setValue("active");
                Query query = referenceforplay.child(useruuid.getUid());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.child("playData").child("Responds").exists()) {
                            total_responds = (int) dataSnapshot.child("playData").child("Responds").getChildrenCount();
                        }

                        int total_value_of_donation = (int) dataSnapshot.child("ConfirmDonation").getChildrenCount();

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

                        checkcurrentlevel=(storetotallevel);
                        referenceforplay.child(useruuid.getUid()).child("playData").child("badges").removeValue();
                        referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").removeValue();

                        if(dataSnapshot.child("EventDonation").exists()){
                            int event_value_of_donation= (int) dataSnapshot.child("EventDonation").getChildrenCount();
                            store_event_donation_number=event_value_of_donation+"";
                        }
//------------------------------------------------------------------------------------------------------------------------------------------

                        if (checkcurrentlevel== 1) {

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("1").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("1").setValue("Earned");

                        }
                        if (checkcurrentlevel>= 2 && checkcurrentlevel<5) {

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("1").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("1").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("2").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("2").setValue("Earned");
                        }
                        if (checkcurrentlevel >= 5 && checkcurrentlevel < 10) {

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("1").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("1").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("2").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("2").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("5").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("5").setValue("Earned");
                        }
                        if (checkcurrentlevel >= 10 && checkcurrentlevel < 15) {

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("1").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("1").setValue("Earned");

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("2").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("2").setValue("Earned");

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("5").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("5").setValue("Earned");

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("10").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("10").setValue("Earned");
                        }
                        if (checkcurrentlevel >= 15 && checkcurrentlevel < 20) {

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("1").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("1").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("2").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("2").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("5").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("5").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("10").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("10").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("15").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("15").setValue("Earned");
                        }
                        if (checkcurrentlevel >=20 && checkcurrentlevel < 25) {

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("1").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("1").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("2").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("2").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("5").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("5").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("10").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("10").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("15").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("15").setValue("Earned");

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("20").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("20").setValue("Earned");
                        }

                        if (checkcurrentlevel >=25 && checkcurrentlevel < 30) {

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("1").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("1").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("2").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("2").setValue("Earned");

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("5").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("5").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("10").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("10").setValue("Earned");

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("15").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("15").setValue("Earned");

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("20").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("20").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("25").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("25").setValue("Earned");
                        }
                        if (checkcurrentlevel >=30 && checkcurrentlevel < 35) {

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("1").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("1").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("2").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("2").setValue("Earned");

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("5").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("5").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("10").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("10").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("15").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("15").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("20").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("20").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("25").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("25").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("30").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("30").setValue("Earned");
                        }

                        if (checkcurrentlevel >=35 && checkcurrentlevel< 40) {

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("1").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("1").setValue("Earned");

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("2").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("2").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("5").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("5").setValue("Earned");

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("10").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("10").setValue("Earned");

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("15").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("15").setValue("Earned");

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("20").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("20").setValue("Earned");

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("25").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("25").setValue("Earned");

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("30").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("30").setValue("Earned");

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("35").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("35").setValue("Earned");
                        }

                        if (checkcurrentlevel >=40 && checkcurrentlevel < 45) {

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("1").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("1").setValue("Earned");

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("2").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("2").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("5").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("5").setValue("Earned");

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("10").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("10").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("15").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("15").setValue("Earned");

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("20").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("20").setValue("Earned");

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("25").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("25").setValue("Earned");

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("30").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("30").setValue("Earned");

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("35").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("35").setValue("Earned");

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("40").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("40").setValue("Earned");
                        }

                        if (checkcurrentlevel >=45 && checkcurrentlevel < 50) {

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("1").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("1").setValue("Earned");

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("2").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("2").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("5").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("5").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("10").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("10").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("15").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("15").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("20").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("20").setValue("Earned");



                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("25").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("25").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("30").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("30").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("35").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("35").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("40").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("40").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("45").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("45").setValue("Earned");
                        }

                        if (checkcurrentlevel >=50) {

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("1").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("1").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("2").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("2").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("5").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("5").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("10").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("10").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("15").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("15").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("20").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("20").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("25").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("25").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("30").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("30").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("35").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("35").setValue("Earned");

                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("40").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("40").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("45").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("45").setValue("Earned");


                            referenceforplay.child(useruuid.getUid()).child("playData").child("badges").child("50").setValue("Earned");
                            referenceforplay.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child("50").setValue("Earned");
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                database=FirebaseDatabase.getInstance();
                reflevelbadges=database.getReference("limitedlevels");

                reflevelbadges.child("setchanges").child("trigger").setValue("yes");
                Query querylevelbadgecondition=reflevelbadges.child("setchanges");
                querylevelbadgecondition.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String fetchlevel = snapshot.child("level").getValue(String.class);
                        String fetchbadgeurl = snapshot.child("badges").getValue(String.class);
                        String fetchbadgename = snapshot.child("badgename").getValue(String.class);
                        badgedata = database.getReference("DonorsData");
                        // String current_user_level=increase.getText().toString();
                        try {
                            if (checkcurrentlevel >= Integer.parseInt(fetchlevel)) {

                                badgedata.child(useruuid.getUid()).child("playData").child("limitedbadgeData").child(fetchbadgename).setValue(fetchbadgeurl);

                                badgedata.child(useruuid.getUid()).child("playData").child("badges").child(fetchbadgename).setValue("Earned");
                                badgedata.child(useruuid.getUid()).child("playData").child("weeklyProgress").child("badges").child(fetchbadgename).setValue("Earned");

                            } else {
                                badgedata.child(useruuid.getUid()).child("playData").child("limitedbadgeData").child(fetchbadgename).removeValue();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                DatabaseReference limitedreference= databaseD.getReference().child("weeklytotal");

                limitedreference.child("weekchange").child("trigger").setValue("yes");
                Query query1limited=limitedreference.child("weekchange");
                query1limited.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String badgetitle=dataSnapshot.child("badgename").getValue(String.class);
                        DatabaseReference referencefordonorsdata=databaseD.getReference().child("DonorsData");
                        referencefordonorsdata.child(useruuid.getUid()).child("playData").child("limitedbadgeData").child(badgetitle).removeValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                refD=databaseD.getReference().child("Donors");

                storage=FirebaseStorage.getInstance();
                try {
                    storageReference = storage.getReferenceFromUrl(pic1url);
                    storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // File deleted successfully

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Uh-oh, an error occurred!
                            Toast.makeText(context, "onFailure: did not delete file", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();

                }
                try {
                    storageReference = storage.getReferenceFromUrl(pic2url);
                    storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // File deleted successfully

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Uh-oh, an error occurred!
                            Toast.makeText(context, "onFailure: did not delete file", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    storageReference = storage.getReferenceFromUrl(pic3url);
                    storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // File deleted successfully

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Uh-oh, an error occurred!
                            Toast.makeText(context, "onFailure: did not delete file", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    storageReference = storage.getReferenceFromUrl(pic4url);
                    storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // File deleted successfully

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Uh-oh, an error occurred!
                            Toast.makeText(context, "onFailure: did not delete file", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }


                refD.child(userdonorid).removeValue();


            }
        });

    }
}

