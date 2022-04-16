package com.aryomtech.nsabilaspur;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

public class ResultsHolder extends RecyclerView.ViewHolder {

    View views;

    Dialog dialogAnimated;
    Button yes;
    public ResultsHolder(@NonNull View itemView) {
        super(itemView);

        views=itemView;
    }

    public void setView(Context context,String cardphoto,String date,String active,String quizName,String status,String color) {

        ImageView quiz_image=views.findViewById(R.id.quiz_image);
        TextView datetxt=views.findViewById(R.id.textView38);
        TextView live=views.findViewById(R.id.textView2);


        live.setText(status);
        datetxt.setText(date);
        if(color.equals("RoyalPink")){
            live.setTextColor(context.getResources().getColor(R.color.RoyalPink));
            datetxt.setTextColor(context.getResources().getColor(R.color.RoyalPink));
        }
        else if(color.equals("Red")){
            live.setTextColor(context.getResources().getColor(R.color.Red));
            datetxt.setTextColor(context.getResources().getColor(R.color.Red));
        }
        else if(color.equals("Yellow")){
            live.setTextColor(context.getResources().getColor(R.color.Yellow));
            datetxt.setTextColor(context.getResources().getColor(R.color.Yellow));
        }
        else if(color.equals("black")){
            live.setTextColor(context.getResources().getColor(R.color.black));
            datetxt.setTextColor(context.getResources().getColor(R.color.black));
        }
        else if(color.equals("blue")){
            live.setTextColor(context.getResources().getColor(R.color.blue));
            datetxt.setTextColor(context.getResources().getColor(R.color.blue));
        }
        else if(color.equals("purple")){
            live.setTextColor(context.getResources().getColor(R.color.purple));
            datetxt.setTextColor(context.getResources().getColor(R.color.purple));
        }
        else if(color.equals("algaegreen")){
            live.setTextColor(context.getResources().getColor(R.color.algaegreen));
            datetxt.setTextColor(context.getResources().getColor(R.color.algaegreen));
        }
        else if(color.equals("lightblue")){
            live.setTextColor(context.getResources().getColor(R.color.lightblue));
            datetxt.setTextColor(context.getResources().getColor(R.color.lightblue));
        }
        else if(color.equals("redpluspink")){
            live.setTextColor(context.getResources().getColor(R.color.redpluspink));
            datetxt.setTextColor(context.getResources().getColor(R.color.redpluspink));
        }
        else if(color.equals("brown")){
            live.setTextColor(context.getResources().getColor(R.color.brown));
            datetxt.setTextColor(context.getResources().getColor(R.color.brown));
        }
        else if(color.equals("green")){
            live.setTextColor(context.getResources().getColor(R.color.green));
            datetxt.setTextColor(context.getResources().getColor(R.color.green));
        }
        else if(color.equals("orange")){
            live.setTextColor(context.getResources().getColor(R.color.orange));
            datetxt.setTextColor(context.getResources().getColor(R.color.orange));
        }
        else if(color.equals("diwali")){
            live.setTextColor(context.getResources().getColor(R.color.diwali));
            datetxt.setTextColor(context.getResources().getColor(R.color.diwali));
        }
        else if(color.equals("darkblue")){
            live.setTextColor(context.getResources().getColor(R.color.darkblue));
            datetxt.setTextColor(context.getResources().getColor(R.color.darkblue));
        }
        else if(color.equals("trueyellow")){
            live.setTextColor(context.getResources().getColor(R.color.trueyellow));
            datetxt.setTextColor(context.getResources().getColor(R.color.trueyellow));
        }


        LinearLayout card_layout=views.findViewById(R.id.card_layout);


        card_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(live.getText().toString().trim().equals("See Results")) {
                    Intent openleaderboard = new Intent(context, LeaderBoard.class);
                    openleaderboard.putExtra("sendingWhichquizleaderboard",quizName);
                    context.startActivity(openleaderboard);
                }
                else if(!live.getText().toString().trim().equals("See Results") && !live.getText().toString().trim().equals("Live")){
                    dialogAnimated = new Dialog(context, R.style.dialogstyletick);
                    dialogAnimated.setContentView(R.layout.quiz_active);
                    TextView textView36=dialogAnimated.findViewById(R.id.textView36);
                    textView36.setText("Quiz will be Live soon..");
                    yes = dialogAnimated.findViewById(R.id.button2);
                    dialogAnimated.show();
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogAnimated.dismiss();
                        }
                    });
                }
                else if(live.getText().toString().trim().equals("Live")){
                    dialogAnimated = new Dialog(context, R.style.dialogstyletick);
                    dialogAnimated.setContentView(R.layout.quiz_active);
                    yes = dialogAnimated.findViewById(R.id.button2);
                    dialogAnimated.show();
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogAnimated.dismiss();
                        }
                    });
                }
            }
        });

        try{
            if(cardphoto!=null){
                Glide.with(context).load(cardphoto).into(quiz_image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
