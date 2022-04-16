package com.aryomtech.nsabilaspur;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class QuizHolder  extends RecyclerView.ViewHolder {

    View view;

    DatabaseReference ref,submittedusers;
    FirebaseDatabase database;


    FirebaseAuth auth;
    FirebaseUser useruuid;
    int i=0;

    public QuizHolder(@NonNull View itemView) {
        super(itemView);

        view=itemView;
    }

    public void setView(Context context,String polls_pic,String question,String correct,String optionfirst,String optionsecond,String optionthird,String optionfourth,String key,
                        String activeQuiz,String total,String questionNo,String submit) {

        TextView ques=view.findViewById(R.id.textView38);
        ImageView photo=view.findViewById(R.id.postimg);

        auth = FirebaseAuth.getInstance();
        useruuid = auth.getCurrentUser();

        TextView option1=view.findViewById(R.id.option1);
        TextView option2=view.findViewById(R.id.option2);
        TextView option3=view.findViewById(R.id.option3);
        TextView option4=view.findViewById(R.id.option4);
        TextView totaltxt=view.findViewById(R.id.textView41);
        TextView quesNOtxt=view.findViewById(R.id.textView43);

        TextView submitxt=view.findViewById(R.id.submit);

        LinearLayout submitlayout=view.findViewById(R.id.submitlayout);

        ConstraintLayout mainlayout=view.findViewById(R.id.constraintsubmit);
        mainlayout.setVisibility(View.GONE);

        submitlayout.setVisibility(View.GONE);

        totaltxt.setText(total);
        quesNOtxt.setText(questionNo);

        option1.setText(optionfirst);
        option2.setText(optionsecond);
        option3.setText(optionthird);
        option4.setText(optionfourth);

        if(submit.equals("yes")){
            submitlayout.setVisibility(View.VISIBLE);
            mainlayout.setVisibility(View.VISIBLE);
            option1.setClickable(false);
            option2.setClickable(false);
            option3.setClickable(false);
            option4.setClickable(false);
        }



        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("SecondaryQuiz");
        submittedusers=database.getReference().child("SubmittedUsers");

        submitxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ref.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        long wrongchildrens=snapshot.child(activeQuiz).child("users").child(useruuid.getUid()).child("wrong").getChildrenCount();
                        long correctchildrens=snapshot.child(activeQuiz).child("users").child(useruuid.getUid()).child("correct").getChildrenCount();
                        if(String.valueOf(wrongchildrens+correctchildrens).equals(total)){

                            submittedusers.child(activeQuiz).child("users").child(useruuid.getUid()).setValue("Submitted");
                            ref.child(activeQuiz).child("users").child(useruuid.getUid()).child("displayname").setValue(useruuid.getEmail());

                            submitxt.setBackgroundResource(R.drawable.submit_gradient);
                            submitxt.setText("SUBMITTED");

                            final ProgressDialog dialog = new ProgressDialog(context);
                            dialog.setTitle("Submitting...");
                            dialog.setMessage("Please wait.");
                            dialog.setIndeterminate(true);
                            dialog.setCancelable(false);
                            long delayInMillis = 2000;
                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    if(i==0) {
                                        i=1;
                                        Intent openplay = new Intent(context, quizPortal.class);
                                        openplay.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        context.startActivity(openplay);
                                        dialog.dismiss();
                                    }

                                }
                            }, delayInMillis);

                        }
                        if(String.valueOf(wrongchildrens).equals(total)){

                            submittedusers.child(activeQuiz).child("users").child(useruuid.getUid()).setValue("Submitted");
                            ref.child(activeQuiz).child("users").child(useruuid.getUid()).child("displayname").setValue(useruuid.getEmail());

                            submitxt.setBackgroundResource(R.drawable.submit_gradient);
                            submitxt.setText("SUBMITTED");

                            final ProgressDialog dialog = new ProgressDialog(context);
                            dialog.setTitle("Submitting...");
                            dialog.setMessage("Please wait.");
                            dialog.setIndeterminate(true);
                            dialog.setCancelable(false);
                            long delayInMillis = 2000;
                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {

                                    if(i==0) {
                                        i=1;
                                        Intent openplay = new Intent(context, quizPortal.class);
                                        openplay.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        context.startActivity(openplay);
                                        dialog.dismiss();
                                    }

                                }
                            }, delayInMillis);
                        }
                        if(String.valueOf(correctchildrens).equals(total)){

                            submittedusers.child(activeQuiz).child("users").child(useruuid.getUid()).setValue("Submitted");
                            ref.child(activeQuiz).child("users").child(useruuid.getUid()).child("displayname").setValue(useruuid.getEmail());

                            submitxt.setBackgroundResource(R.drawable.submit_gradient);
                            submitxt.setText("SUBMITTED");

                            final ProgressDialog dialog = new ProgressDialog(context);
                            dialog.setTitle("Submitting...");
                            dialog.setMessage("Please wait.");
                            dialog.setIndeterminate(true);
                            dialog.setCancelable(false);
                            long delayInMillis = 2000;
                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {

                                    if(i==0) {
                                        i=1;
                                        Intent openplay = new Intent(context, quizPortal.class);
                                        openplay.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        context.startActivity(openplay);
                                        dialog.dismiss();
                                    }

                                }
                            }, delayInMillis);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option1.setBackgroundResource(R.drawable.quiz_butn_bg);
                option2.setBackgroundResource(R.drawable.text_option_bg);
                option3.setBackgroundResource(R.drawable.text_option_bg);
                option4.setBackgroundResource(R.drawable.text_option_bg);
                if(option1.getText().toString().equals(correct)){
                    ref.child(activeQuiz).child("users").child(useruuid.getUid()).child("correct").child(key).setValue(key);
                    ref.child(activeQuiz).child("users").child(useruuid.getUid()).child("wrong").child(key).removeValue();
                }
                else{
                    ref.child(activeQuiz).child("users").child(useruuid.getUid()).child("correct").child(key).removeValue();
                    ref.child(activeQuiz).child("users").child(useruuid.getUid()).child("wrong").child(key).setValue(key);
                }
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option2.setBackgroundResource(R.drawable.quiz_butn_bg);
                option1.setBackgroundResource(R.drawable.text_option_bg);
                option3.setBackgroundResource(R.drawable.text_option_bg);
                option4.setBackgroundResource(R.drawable.text_option_bg);
                if(option2.getText().toString().equals(correct)){
                    ref.child(activeQuiz).child("users").child(useruuid.getUid()).child("correct").child(key).setValue(key);
                    ref.child(activeQuiz).child("users").child(useruuid.getUid()).child("wrong").child(key).removeValue();
                }
                else{
                    ref.child(activeQuiz).child("users").child(useruuid.getUid()).child("correct").child(key).removeValue();
                    ref.child(activeQuiz).child("users").child(useruuid.getUid()).child("wrong").child(key).setValue(key);
                }
            }
        });
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option3.setBackgroundResource(R.drawable.quiz_butn_bg);
                option1.setBackgroundResource(R.drawable.text_option_bg);
                option2.setBackgroundResource(R.drawable.text_option_bg);
                option4.setBackgroundResource(R.drawable.text_option_bg);
                if(option3.getText().toString().equals(correct)){
                    ref.child(activeQuiz).child("users").child(useruuid.getUid()).child("correct").child(key).setValue(key);
                    ref.child(activeQuiz).child("users").child(useruuid.getUid()).child("wrong").child(key).removeValue();
                }
                else{
                    ref.child(activeQuiz).child("users").child(useruuid.getUid()).child("correct").child(key).removeValue();
                    ref.child(activeQuiz).child("users").child(useruuid.getUid()).child("wrong").child(key).setValue(key);
                }
            }
        });
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option4.setBackgroundResource(R.drawable.quiz_butn_bg);
                option1.setBackgroundResource(R.drawable.text_option_bg);
                option2.setBackgroundResource(R.drawable.text_option_bg);
                option3.setBackgroundResource(R.drawable.text_option_bg);
                if(option4.getText().toString().equals(correct)){
                    ref.child(activeQuiz).child("users").child(useruuid.getUid()).child("correct").child(key).setValue(key);
                    ref.child(activeQuiz).child("users").child(useruuid.getUid()).child("wrong").child(key).removeValue();
                }
                else{
                    ref.child(activeQuiz).child("users").child(useruuid.getUid()).child("correct").child(key).removeValue();
                    ref.child(activeQuiz).child("users").child(useruuid.getUid()).child("wrong").child(key).setValue(key);
                }
            }
        });

        ques.setText(question);
        if(submit.equals("yes")){
            submitlayout.setVisibility(View.VISIBLE);
            mainlayout.setVisibility(View.VISIBLE);
            option1.setClickable(false);
            option2.setClickable(false);
            option3.setClickable(false);
            option4.setClickable(false);
        }
        try{
            if(polls_pic!=null) {
                Glide.with(context).load(polls_pic).into(photo);
            }
            else{
                ViewGroup.LayoutParams params = photo.getLayoutParams();
                params.height = 0;
                photo.requestLayout();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
