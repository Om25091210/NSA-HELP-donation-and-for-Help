package com.aryomtech.nsabilaspur;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class pollsHolder extends RecyclerView.ViewHolder {

    View view;
    ImageView poll_photo;

    long click=0;
    long clickon1=0;
    long clickon2=0;
    long clickon3=0;
    long clickon4=0;
    long clickon5=0;
    long clickon6=0;

    long per1=0;
    long per2=0;
    long per3=0;
    long per4=0;
    long per5=0;
    long per6=0;


    boolean notanswered=true;
    DatabaseReference ref,reference;
    FirebaseDatabase database;

    FirebaseAuth auth;
    FirebaseUser useruuid;

    LinearLayout layout3,layout4,layout5,layout6;

    public pollsHolder(@NonNull View itemView) {
        super(itemView);

        view=itemView;
    }

    @SuppressLint("SetTextI18n")
    public void setView(Context context, String photourl, String question,String key,String option1,String option2,String option3,String option4,String option5,String option6,long totalquestion) {



        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("POLLS");
        reference=database.getReference().child("SecondaryPolls");

        auth = FirebaseAuth.getInstance();
        useruuid = auth.getCurrentUser();

        poll_photo=view.findViewById(R.id.postimg);
        TextView ques=view.findViewById(R.id.textView38);

        ProgressBar progressBar1=view.findViewById(R.id.progressBar3);
        ProgressBar progressBar2=view.findViewById(R.id.progressBar4);
        ProgressBar progressBar3=view.findViewById(R.id.progressBar5);
        ProgressBar progressBar4=view.findViewById(R.id.progressBar6);
        ProgressBar progressBar5=view.findViewById(R.id.progressBar7);
        ProgressBar progressBar6=view.findViewById(R.id.progressBar8);


        View view1=view.findViewById(R.id.view24);
        View view2=view.findViewById(R.id.view25);
        View view3=view.findViewById(R.id.view26);
        View view4=view.findViewById(R.id.view27);
        View view5=view.findViewById(R.id.view28);
        View view6=view.findViewById(R.id.view29);

        TextView pbTxt1=view.findViewById(R.id.textView39);
        TextView pbTxt2=view.findViewById(R.id.textView42);
        TextView pbTxt3=view.findViewById(R.id.textView44);
        TextView pbTxt4=view.findViewById(R.id.textView46);
        TextView pbTxt5=view.findViewById(R.id.textView48);
        TextView pbTxt6=view.findViewById(R.id.textView50);

        TextView options1=view.findViewById(R.id.option1);
        TextView options2=view.findViewById(R.id.option2);
        TextView options3=view.findViewById(R.id.optiontext3);
        TextView options4=view.findViewById(R.id.option4);
        TextView options5=view.findViewById(R.id.option5);
        TextView options6=view.findViewById(R.id.option6);

        options1.setText(option1);
        options2.setText(option2);

        if(option3!=null)
            options3.setText(option3);
        if(option4!=null)
            options4.setText(option4);
        if(option5!=null)
            options5.setText(option5);
        if(option6!=null)
            options6.setText(option6);

        layout3=view.findViewById(R.id.layout3);
        layout4=view.findViewById(R.id.layout4);
        layout5=view.findViewById(R.id.layout5);
        layout6=view.findViewById(R.id.layout6);

        layout3.setVisibility(View.GONE);
        layout4.setVisibility(View.GONE);
        layout5.setVisibility(View.GONE);
        layout6.setVisibility(View.GONE);

        progressBar1.setVisibility(View.GONE);
        progressBar2.setVisibility(View.GONE);
        progressBar3.setVisibility(View.GONE);
        progressBar4.setVisibility(View.GONE);
        progressBar5.setVisibility(View.GONE);
        progressBar6.setVisibility(View.GONE);

        pbTxt1.setText("  ");
        pbTxt2.setText("  ");
        pbTxt3.setText("  ");
        pbTxt4.setText("  ");
        pbTxt5.setText("  ");
        pbTxt6.setText("  ");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clickon1=dataSnapshot.child(key).child("clickon1").getValue(long.class);
                clickon2=dataSnapshot.child(key).child("clickon2").getValue(long.class);

                if(dataSnapshot.child(key).child("Users").child(useruuid.getUid()).exists()) {
                    notanswered = false;
                    int total = dataSnapshot.child(key).child("total").getValue(int.class);
                    if (total == 2) {

                        double erp1 = dataSnapshot.child(key).child("clickon1").getValue(long.class);
                        double erp2 = dataSnapshot.child(key).child("clickon2").getValue(long.class);
                        double erp3 = (double) per3;
                        double erp4 = (double) per4;
                        double erp5 = (double) per5;
                        double erp6 = (double) per6;


                        double valueofexp = (erp1) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);


                        double value2 = (erp2) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);



                        pbTxt1.setText(Math.round(valueofexp) + "%");
                        pbTxt2.setText(Math.round(value2) + "%");


                        progressBar1.setVisibility(View.VISIBLE);
                        progressBar2.setVisibility(View.VISIBLE);
                        progressBar3.setVisibility(View.GONE);
                        progressBar4.setVisibility(View.GONE);
                        progressBar5.setVisibility(View.GONE);
                        progressBar6.setVisibility(View.GONE);

                        progressBar1.setProgress((int) Math.round(valueofexp));
                        progressBar2.setProgress((int) Math.round(value2));

                        if(Math.round(valueofexp)==0){
                            progressBar1.setVisibility(View.GONE);
                        }
                        if(Math.round(value2)==0){
                            progressBar2.setVisibility(View.GONE);
                        }

                    }
                    if (total == 3) {

                        double erp1 = dataSnapshot.child(key).child("clickon1").getValue(long.class);
                        double erp2 = dataSnapshot.child(key).child("clickon2").getValue(long.class);
                        double erp3 = dataSnapshot.child(key).child("clickon3").getValue(long.class);
                        double erp4 = (double) per4;
                        double erp5 = (double) per5;
                        double erp6 = (double) per6;


                        double valueofexp = (erp1) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                        progressBar1.setProgress((int) Math.round(valueofexp));

                        double value2 = (erp2) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                        progressBar2.setProgress((int) Math.round(value2));

                        double value3 = (erp3) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                        progressBar3.setProgress((int) Math.round(value3));


                        progressBar1.setVisibility(View.VISIBLE);
                        progressBar2.setVisibility(View.VISIBLE);
                        progressBar3.setVisibility(View.VISIBLE);
                        progressBar4.setVisibility(View.GONE);
                        progressBar5.setVisibility(View.GONE);
                        progressBar6.setVisibility(View.GONE);

                        pbTxt1.setText(Math.round(valueofexp) + "%");
                        pbTxt2.setText(Math.round(value2) + "%");
                        pbTxt3.setText(Math.round(value3) + "%");

                        if(Math.round(valueofexp)==0){
                            progressBar1.setVisibility(View.GONE);
                        }
                        if(Math.round(value2)==0){
                            progressBar2.setVisibility(View.GONE);
                        }
                        if(Math.round(value3)==0){
                            progressBar3.setVisibility(View.GONE);
                        }


                    }
                    if (total == 4) {

                        double erp1 = dataSnapshot.child(key).child("clickon1").getValue(long.class);
                        double erp2 = dataSnapshot.child(key).child("clickon2").getValue(long.class);
                        double erp3 = dataSnapshot.child(key).child("clickon3").getValue(long.class);
                        double erp4 = dataSnapshot.child(key).child("clickon4").getValue(long.class);
                        double erp5 = (double) per5;
                        double erp6 = (double) per6;

                        double valueofexp = (erp1) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                        progressBar1.setProgress((int) Math.round(valueofexp));

                        double value2 = (erp2) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                        progressBar2.setProgress((int) Math.round(value2));

                        double value3 = (erp3) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                        progressBar3.setProgress((int) Math.round(value3));

                        double value4 = (erp4) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                        progressBar4.setProgress((int) Math.round(value4));


                        progressBar1.setVisibility(View.VISIBLE);
                        progressBar2.setVisibility(View.VISIBLE);
                        progressBar3.setVisibility(View.VISIBLE);
                        progressBar4.setVisibility(View.VISIBLE);
                        progressBar5.setVisibility(View.GONE);
                        progressBar6.setVisibility(View.GONE);


                        pbTxt1.setText(Math.round(valueofexp) + "%");
                        pbTxt2.setText(Math.round(value2) + "%");
                        pbTxt3.setText(Math.round(value3) + "%");
                        pbTxt4.setText(Math.round(value4) + "%");

                        if(Math.round(valueofexp)==0){
                            progressBar1.setVisibility(View.GONE);
                        }
                        if(Math.round(value2)==0){
                            progressBar2.setVisibility(View.GONE);
                        }
                        if(Math.round(value3)==0){
                            progressBar3.setVisibility(View.GONE);
                        }
                        if(Math.round(value4)==0){
                            progressBar4.setVisibility(View.GONE);
                        }

                    }
                    if (total == 5) {

                        double erp1 = dataSnapshot.child(key).child("clickon1").getValue(long.class);
                        double erp2 = dataSnapshot.child(key).child("clickon2").getValue(long.class);
                        double erp3 = dataSnapshot.child(key).child("clickon3").getValue(long.class);
                        double erp4 = dataSnapshot.child(key).child("clickon4").getValue(long.class);
                        double erp5 = dataSnapshot.child(key).child("clickon5").getValue(long.class);
                        double erp6 = (double) per6;

                        double valueofexp = (erp1) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                        progressBar1.setProgress((int) Math.round(valueofexp));

                        double value2 = (erp2) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                        progressBar2.setProgress((int) Math.round(value2));

                        double value3 = (erp3) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                        progressBar3.setProgress((int) Math.round(value3));

                        double value4 = (erp4) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                        progressBar4.setProgress((int) Math.round(value4));

                        double value5 = (erp5) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                        progressBar5.setProgress((int) Math.round(value5));


                        progressBar1.setVisibility(View.VISIBLE);
                        progressBar2.setVisibility(View.VISIBLE);
                        progressBar3.setVisibility(View.VISIBLE);
                        progressBar4.setVisibility(View.VISIBLE);
                        progressBar5.setVisibility(View.VISIBLE);
                        progressBar6.setVisibility(View.GONE);


                        pbTxt1.setText(Math.round(valueofexp) + "%");
                        pbTxt2.setText(Math.round(value2) + "%");
                        pbTxt3.setText(Math.round(value3) + "%");
                        pbTxt4.setText(Math.round(value4) + "%");
                        pbTxt5.setText(Math.round(value5) + "%");

                        if(Math.round(valueofexp)==0){
                            progressBar1.setVisibility(View.GONE);
                        }
                        if(Math.round(value2)==0){
                            progressBar2.setVisibility(View.GONE);
                        }
                        if(Math.round(value3)==0){
                            progressBar3.setVisibility(View.GONE);
                        }
                        if(Math.round(value4)==0){
                            progressBar4.setVisibility(View.GONE);
                        }
                        if(Math.round(value5)==0){
                            progressBar5.setVisibility(View.GONE);
                        }

                    }
                    if (total == 6) {

                        double erp1 = dataSnapshot.child(key).child("clickon1").getValue(long.class);
                        double erp2 = dataSnapshot.child(key).child("clickon2").getValue(long.class);
                        double erp3 = dataSnapshot.child(key).child("clickon3").getValue(long.class);
                        double erp4 = dataSnapshot.child(key).child("clickon4").getValue(long.class);
                        double erp5 = dataSnapshot.child(key).child("clickon5").getValue(long.class);
                        double erp6 = dataSnapshot.child(key).child("clickon6").getValue(long.class);

                        double valueofexp = (erp1) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                        progressBar1.setProgress((int) Math.round(valueofexp));

                        double value2 = (erp2) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                        progressBar2.setProgress((int) Math.round(value2));

                        double value3 = (erp3) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                        progressBar3.setProgress((int) Math.round(value3));

                        double value4 = (erp4) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                        progressBar4.setProgress((int) Math.round(value4));

                        double value5 = (erp5) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                        progressBar5.setProgress((int) Math.round(value5));

                        double value6 = (erp6) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                        progressBar6.setProgress((int) Math.round(value6));

                        progressBar1.setVisibility(View.VISIBLE);
                        progressBar2.setVisibility(View.VISIBLE);
                        progressBar3.setVisibility(View.VISIBLE);
                        progressBar4.setVisibility(View.VISIBLE);
                        progressBar5.setVisibility(View.VISIBLE);
                        progressBar6.setVisibility(View.VISIBLE);



                        pbTxt1.setText(Math.round(valueofexp) + "%");
                        pbTxt2.setText(Math.round(value2) + "%");
                        pbTxt3.setText(Math.round(value3) + "%");
                        pbTxt4.setText(Math.round(value4) + "%");
                        pbTxt5.setText(Math.round(value5) + "%");
                        pbTxt6.setText(Math.round(value6) + "%");

                        if(Math.round(valueofexp)==0){
                            progressBar1.setVisibility(View.GONE);
                        }
                        if(Math.round(value2)==0){
                            progressBar2.setVisibility(View.GONE);
                        }
                        if(Math.round(value3)==0){
                            progressBar3.setVisibility(View.GONE);
                        }
                        if(Math.round(value4)==0){
                            progressBar4.setVisibility(View.GONE);
                        }
                        if(Math.round(value5)==0){
                            progressBar5.setVisibility(View.GONE);
                        }
                        if(Math.round(value6)==0){
                            progressBar6.setVisibility(View.GONE);
                        }
                    }
                }
                else{
                    progressBar1.setProgress(0);
                    pbTxt1.setText("  ");

                    progressBar2.setProgress(0);
                    pbTxt2.setText("  ");

                    progressBar3.setProgress(0);
                    pbTxt3.setText("  ");

                    progressBar4.setProgress(0);
                    pbTxt4.setText("  ");

                    progressBar5.setProgress(0);
                    pbTxt5.setText("  ");

                    progressBar6.setProgress(0);
                    pbTxt6.setText("  ");
                }

                if(dataSnapshot.child(key).child("clickon3").exists()){
                    clickon3=dataSnapshot.child(key).child("clickon3").getValue(long.class);
                    layout3.setVisibility(View.VISIBLE);
                }
                if(dataSnapshot.child(key).child("clickon4").exists()){
                    clickon4=dataSnapshot.child(key).child("clickon4").getValue(long.class);
                    layout4.setVisibility(View.VISIBLE);
                }
                if(dataSnapshot.child(key).child("clickon5").exists()){
                    clickon5=dataSnapshot.child(key).child("clickon5").getValue(long.class);
                    layout5.setVisibility(View.VISIBLE);
                }
                if(dataSnapshot.child(key).child("clickon6").exists()){
                    clickon6=dataSnapshot.child(key).child("clickon6").getValue(long.class);
                    layout6.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
/////TODO://////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notanswered) {

                    click = clickon1 + 1;
                    reference.child(key).child("clickon1").setValue(click);
                    reference.child(key).child("Users").child(useruuid.getUid()).setValue("answered");

                    double erp1= (double) click;
                    double erp2= (double) clickon2;
                    double erp3= (double) clickon3;
                    double erp4= (double) clickon4;
                    double erp5= (double) clickon5;
                    double erp6= (double) clickon6;

                    progressBar1.setVisibility(View.VISIBLE);
                    progressBar2.setVisibility(View.VISIBLE);
                    progressBar3.setVisibility(View.VISIBLE);
                    progressBar4.setVisibility(View.VISIBLE);
                    progressBar5.setVisibility(View.VISIBLE);
                    progressBar6.setVisibility(View.VISIBLE);

                    double valueofexp = (erp1) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar1.setProgress(Integer.parseInt(String.valueOf(Math.round(valueofexp))));


                    double value2 = (erp2) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar2.setProgress((int)Math.round(value2));


                    double value3 = (erp3) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar3.setProgress((int) Math.round(value3));


                    double value4 = (erp4) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar4.setProgress((int) Math.round(value4));


                    double value5 = (erp5) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar5.setProgress((int)Math.round(value5));


                    double value6 = (erp6) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar6.setProgress((int)Math.round(value6));


                    if(Math.round(valueofexp)+Math.round(value2)+Math.round(value3)+Math.round(value4)+Math.round(value5)+Math.round(value6)>100) {
                        pbTxt1.setText(valueofexp + "%");
                        pbTxt2.setText(value2 + "%");
                        pbTxt3.setText(value3 + "%");
                        pbTxt4.setText(value4 + "%");
                        pbTxt5.setText(value5 + "%");
                        pbTxt6.setText(value6 + "%");
                    }else{
                        pbTxt1.setText(Math.round(valueofexp) + "%");
                        pbTxt2.setText(Math.round(value2) + "%");
                        pbTxt3.setText(Math.round(value3) + "%");
                        pbTxt4.setText(Math.round(value4) + "%");
                        pbTxt5.setText(Math.round(value5) + "%");
                        pbTxt6.setText(Math.round(value6) + "%");
                    }
                }

            }
        });
////////TODO://////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notanswered) {
                    click = clickon2 + 1;
                    reference.child(key).child("clickon2").setValue(click);
                    reference.child(key).child("Users").child(useruuid.getUid()).setValue("answered");

                    double erp1= (double) clickon1;
                    double erp2= (double) click;
                    double erp3= (double) clickon3;
                    double erp4= (double) clickon4;
                    double erp5= (double) clickon5;
                    double erp6= (double) clickon6;

                    progressBar1.setVisibility(View.VISIBLE);
                    progressBar2.setVisibility(View.VISIBLE);
                    progressBar3.setVisibility(View.VISIBLE);
                    progressBar4.setVisibility(View.VISIBLE);
                    progressBar5.setVisibility(View.VISIBLE);
                    progressBar6.setVisibility(View.VISIBLE);

                    double valueofexp = (erp1) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar1.setProgress(Integer.parseInt(String.valueOf(Math.round(valueofexp))));

                    double value2 = (erp2) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar2.setProgress((int)Math.round(value2));

                    double value3 = (erp3) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar3.setProgress((int) Math.round(value3));

                    double value4 = (erp4) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar4.setProgress((int) Math.round(value4));

                    double value5 = (erp5) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar5.setProgress((int)Math.round(value5));


                    double value6 = (erp6) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar6.setProgress((int)Math.round(value6));

                    if(Math.round(valueofexp)+Math.round(value2)+Math.round(value3)+Math.round(value4)+Math.round(value5)+Math.round(value6)>100) {
                        pbTxt1.setText(valueofexp + "%");
                        pbTxt2.setText(value2 + "%");
                        pbTxt3.setText(value3 + "%");
                        pbTxt4.setText(value4 + "%");
                        pbTxt5.setText(value5 + "%");
                        pbTxt6.setText(value6 + "%");
                    }else{
                        pbTxt1.setText(Math.round(valueofexp) + "%");
                        pbTxt2.setText(Math.round(value2) + "%");
                        pbTxt3.setText(Math.round(value3) + "%");
                        pbTxt4.setText(Math.round(value4) + "%");
                        pbTxt5.setText(Math.round(value5) + "%");
                        pbTxt6.setText(Math.round(value6) + "%");
                    }

                }
            }
        });
///////TODO:///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notanswered) {
                    click = clickon3 + 1;
                    reference.child(key).child("clickon3").setValue(click);
                    reference.child(key).child("Users").child(useruuid.getUid()).setValue("answered");

                    progressBar1.setVisibility(View.VISIBLE);
                    progressBar2.setVisibility(View.VISIBLE);
                    progressBar3.setVisibility(View.VISIBLE);
                    progressBar4.setVisibility(View.VISIBLE);
                    progressBar5.setVisibility(View.VISIBLE);
                    progressBar6.setVisibility(View.VISIBLE);

                    double erp1= (double) clickon1;
                    double erp2= (double) clickon2;
                    double erp3= (double) click;
                    double erp4= (double) clickon4;
                    double erp5= (double) clickon5;
                    double erp6= (double) clickon6;

                    double valueofexp = (erp1) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar1.setProgress(Integer.parseInt(String.valueOf(Math.round(valueofexp))));

                    double value2 = (erp2) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar2.setProgress((int)Math.round(value2));

                    double value3 = (erp3) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar3.setProgress((int) Math.round(value3));

                    double value4 = (erp4) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar4.setProgress((int) Math.round(value4));

                    double value5 = (erp5) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar5.setProgress((int)Math.round(value5));


                    double value6 = (erp6) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar6.setProgress((int)Math.round(value6));


                    if(Math.round(valueofexp)+Math.round(value2)+Math.round(value3)+Math.round(value4)+Math.round(value5)+Math.round(value6)>100) {
                        pbTxt1.setText(valueofexp + "%");
                        pbTxt2.setText(value2 + "%");
                        pbTxt3.setText(value3 + "%");
                        pbTxt4.setText(value4 + "%");
                        pbTxt5.setText(value5 + "%");
                        pbTxt6.setText(value6 + "%");
                    }else{
                        pbTxt1.setText(Math.round(valueofexp) + "%");
                        pbTxt2.setText(Math.round(value2) + "%");
                        pbTxt3.setText(Math.round(value3) + "%");
                        pbTxt4.setText(Math.round(value4) + "%");
                        pbTxt5.setText(Math.round(value5) + "%");
                        pbTxt6.setText(Math.round(value6) + "%");
                    }

                }
            }
        });
///////TODO:///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notanswered) {
                    click = clickon4 + 1;
                    reference.child(key).child("clickon4").setValue(click);
                    reference.child(key).child("Users").child(useruuid.getUid()).setValue("answered");

                    double erp1= (double) clickon1;
                    double erp2= (double) clickon2;
                    double erp3= (double) clickon3;
                    double erp4= (double) click;
                    double erp5= (double) clickon5;
                    double erp6= (double) clickon6;

                    progressBar1.setVisibility(View.VISIBLE);
                    progressBar2.setVisibility(View.VISIBLE);
                    progressBar3.setVisibility(View.VISIBLE);
                    progressBar4.setVisibility(View.VISIBLE);
                    progressBar5.setVisibility(View.VISIBLE);
                    progressBar6.setVisibility(View.VISIBLE);

                    double valueofexp = (erp1) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar1.setProgress(Integer.parseInt(String.valueOf(Math.round(valueofexp))));


                    double value2 = (erp2) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar2.setProgress((int)Math.round(value2));


                    double value3 = (erp3) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar3.setProgress((int) Math.round(value3));


                    double value4 = (erp4) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar4.setProgress((int) Math.round(value4));


                    double value5 = (erp5) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar5.setProgress((int)Math.round(value5));



                    double value6 = (erp6) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar6.setProgress((int)Math.round(value6));

                    if(Math.round(valueofexp)+Math.round(value2)+Math.round(value3)+Math.round(value4)+Math.round(value5)+Math.round(value6)>100) {
                        pbTxt1.setText(valueofexp + "%");
                        pbTxt2.setText(value2 + "%");
                        pbTxt3.setText(value3 + "%");
                        pbTxt4.setText(value4 + "%");
                        pbTxt5.setText(value5 + "%");
                        pbTxt6.setText(value6 + "%");
                    }else{
                        pbTxt1.setText(Math.round(valueofexp) + "%");
                        pbTxt2.setText(Math.round(value2) + "%");
                        pbTxt3.setText(Math.round(value3) + "%");
                        pbTxt4.setText(Math.round(value4) + "%");
                        pbTxt5.setText(Math.round(value5) + "%");
                        pbTxt6.setText(Math.round(value6) + "%");
                    }

                }
            }
        });
///////TODO:///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        view5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notanswered) {
                    click = clickon5 + 1;
                    reference.child(key).child("clickon5").setValue(click);
                    reference.child(key).child("Users").child(useruuid.getUid()).setValue("answered");

                    progressBar1.setVisibility(View.VISIBLE);
                    progressBar2.setVisibility(View.VISIBLE);
                    progressBar3.setVisibility(View.VISIBLE);
                    progressBar4.setVisibility(View.VISIBLE);
                    progressBar5.setVisibility(View.VISIBLE);
                    progressBar6.setVisibility(View.VISIBLE);

                    double erp1= (double) clickon1;
                    double erp2= (double) clickon2;
                    double erp3= (double) clickon3;
                    double erp4= (double) clickon4;
                    double erp5= (double) click;
                    double erp6= (double) clickon6;

                    double valueofexp = (erp1) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar1.setProgress(Integer.parseInt(String.valueOf(Math.round(valueofexp))));


                    double value2 = (erp2) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar2.setProgress((int)Math.round(value2));


                    double value3 = (erp3) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar3.setProgress((int) Math.round(value3));


                    double value4 = (erp4) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar4.setProgress((int) Math.round(value4));


                    double value5 = (erp5) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar5.setProgress((int)Math.round(value5));



                    double value6 = (erp6) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar6.setProgress((int)Math.round(value6));

                    if(Math.round(valueofexp)+Math.round(value2)+Math.round(value3)+Math.round(value4)+Math.round(value5)+Math.round(value6)>100) {
                        pbTxt1.setText(valueofexp + "%");
                        pbTxt2.setText(value2 + "%");
                        pbTxt3.setText(value3 + "%");
                        pbTxt4.setText(value4 + "%");
                        pbTxt5.setText(value5 + "%");
                        pbTxt6.setText(value6 + "%");
                    }else{
                        pbTxt1.setText(Math.round(valueofexp) + "%");
                        pbTxt2.setText(Math.round(value2) + "%");
                        pbTxt3.setText(Math.round(value3) + "%");
                        pbTxt4.setText(Math.round(value4) + "%");
                        pbTxt5.setText(Math.round(value5) + "%");
                        pbTxt6.setText(Math.round(value6) + "%");
                    }

                }
            }
        });
///////TODO:///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        view6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notanswered) {
                    click = clickon6 + 1;
                    reference.child(key).child("clickon6").setValue(click);
                    reference.child(key).child("Users").child(useruuid.getUid()).setValue("answered");

                    progressBar1.setVisibility(View.VISIBLE);
                    progressBar2.setVisibility(View.VISIBLE);
                    progressBar3.setVisibility(View.VISIBLE);
                    progressBar4.setVisibility(View.VISIBLE);
                    progressBar5.setVisibility(View.VISIBLE);
                    progressBar6.setVisibility(View.VISIBLE);

                    double erp1= (double) clickon1;
                    double erp2= (double) clickon2;
                    double erp3= (double) clickon3;
                    double erp4= (double) clickon4;
                    double erp5= (double) clickon5;
                    double erp6= (double) click;

                    double valueofexp = (erp1) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar1.setProgress(Integer.parseInt(String.valueOf(Math.round(valueofexp))));

                    double value2 = (erp2) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar2.setProgress((int)Math.round(value2));


                    double value3 = (erp3) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar3.setProgress((int) Math.round(value3));


                    double value4 = (erp4) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar4.setProgress((int) Math.round(value4));


                    double value5 = (erp5) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar5.setProgress((int)Math.round(value5));


                    double value6 = (erp6) * (100) / (erp1 + erp2 + erp3 + erp4 + erp5 + erp6);
                    progressBar6.setProgress((int)Math.round(value6));

                    if(Math.round(valueofexp)+Math.round(value2)+Math.round(value3)+Math.round(value4)+Math.round(value5)+Math.round(value6)>100) {
                        pbTxt1.setText(valueofexp + "%");
                        pbTxt2.setText(value2 + "%");
                        pbTxt3.setText(value3 + "%");
                        pbTxt4.setText(value4 + "%");
                        pbTxt5.setText(value5 + "%");
                        pbTxt6.setText(value6 + "%");
                    }else{
                        pbTxt1.setText(Math.round(valueofexp) + "%");
                        pbTxt2.setText(Math.round(value2) + "%");
                        pbTxt3.setText(Math.round(value3) + "%");
                        pbTxt4.setText(Math.round(value4) + "%");
                        pbTxt5.setText(Math.round(value5) + "%");
                        pbTxt6.setText(Math.round(value6) + "%");
                    }

                }
            }
        });
///////TODO:///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ques.setText(question);
        if(photourl!=null){
            try{
                Glide.with(context).load(photourl).into(poll_photo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            ViewGroup.LayoutParams params = poll_photo.getLayoutParams();
            params.height = 0;
            poll_photo.requestLayout();
        }
    }
}
