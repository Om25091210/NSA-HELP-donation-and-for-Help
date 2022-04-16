package com.aryomtech.nsabilaspur;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import static android.content.Context.MODE_PRIVATE;

public class authHolder extends RecyclerView.ViewHolder {

    private static String EmailOFNGO;
    View view;
    private FirebaseAuth mAuth;


    static FirebaseUser user;
    public authHolder(@NonNull View itemView) {
        super(itemView);

        view=itemView;

    }

    public void setView(final Context context, String emailOFngo,
                        final String nameOFngo, final String uid,
                        final String dpurl,
                        String postedimages, final String category_of_ngo) {

        EmailOFNGO=emailOFngo;
        mAuth=FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        View view15=view.findViewById(R.id.view15);
        TextView namengotxt=view.findViewById(R.id.nameofngo);
        TextView workcategory=view.findViewById(R.id.workcategory);

        workcategory.setText(category_of_ngo);

        ImageView dpNGO=view.findViewById(R.id.profilengo);
        try {
            if (!postedimages.equals("Null")) {
                Glide.with(context).load(dpurl).into(dpNGO);

            } else if (postedimages.equals("Null")) {
                dpNGO.setImageResource(R.drawable.nsadp);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        namengotxt.setText(nameOFngo);




    }


}

