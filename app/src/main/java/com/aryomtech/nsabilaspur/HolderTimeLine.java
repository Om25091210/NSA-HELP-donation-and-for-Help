package com.aryomtech.nsabilaspur;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class HolderTimeLine extends RecyclerView.ViewHolder {

    View view;
    CircleImageView dpngo;
    TextView nameofNGO;
    View profilesection;
    TextView type;
    TextView nameofdonor,date;
    public HolderTimeLine(@NonNull View itemView) {
        super(itemView);

        view=itemView;
    }

    @SuppressLint("SetTextI18n")
    public void setView(Context context, String respondedTYPE,
                         String respondedby, String respondeddpurl,
                        String respondedto,String time){

        dpngo=view.findViewById(R.id.profilengotime);
        nameofNGO=view.findViewById(R.id.textView9);
        type=view.findViewById(R.id.textView16);
        nameofdonor=view.findViewById(R.id.textView21);
        profilesection=view.findViewById(R.id.view14);
        date=view.findViewById(R.id.textView56);
        date.setText(time);

        try{
            if(respondeddpurl!=null){
                Glide.with(context).load(respondeddpurl).into(dpngo);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        nameofNGO.setText(respondedby.trim());
        if(respondedTYPE.equals("HELP")){
            type.setText("Has Responded To The "+respondedTYPE+" Request");
        }
        else{
            type.setText("Has Responded To The "+respondedTYPE);
        }
        nameofdonor.setText("Of "+respondedto+".");

    }
}
