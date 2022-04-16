package com.aryomtech.nsabilaspur;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.Context.MODE_PRIVATE;

public class vendorHolder extends RecyclerView.ViewHolder {

    private static String EmailOFNGO;
    View view;
    Boolean verify;
    Context context;
    private FirebaseAuth mAuth;
    String RegOFNGO;
    Dialog dialogAnimated;
    TextView editText;

    static FirebaseUser user;
    public vendorHolder(@NonNull View itemView) {
        super(itemView);

        view=itemView;

    }

    public void setView(final Context context,String descriptionshop,String shopname,String addressshop,String photo,
                        String latitude,String longitude,String listername) {

        TextView shopcategory=view.findViewById(R.id.textView18);
        TextView addressShop=view.findViewById(R.id.textView20);
        ImageView addedimg=view.findViewById(R.id.addedimg);
        TextView textView30=view.findViewById(R.id.textView30);

        Button desopen=view.findViewById(R.id.desopen);
        Button maps=view.findViewById(R.id.maps);


        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(latitude.equals("") || longitude.equals("")){
                    Uri uri=Uri.parse("geo:0,0?q="+addressshop);
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
                editText.setText(descriptionshop);
            }
        });
        if(photo!=null) {
            addedimg.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            addedimg.requestLayout();
            try {
                Glide.with(context).load(photo).into(addedimg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        addedimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent show=new Intent(context,vendorsIMAGE.class);
                show.putExtra("showinglocalmalls",photo);
                context.startActivity(show);
            }
        });
        shopcategory.setText(shopname);
        addressShop.setText(addressshop);
        textView30.setText(listername);

    }


}

