package com.aryomtech.nsabilaspur;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.MyViewHolder> {

    ArrayList<Deal> list;
    Dialog dialogAnimated;
    TextView editText;
    Context context;

    public AdapterClass(Context context,ArrayList<Deal> list){
        this.list=list;
        this.context=context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.contentofvendor,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.address.setText(list.get(position).getAddressshop());
        holder.category.setText(list.get(position).getShopcategory());
        holder.listername.setText(list.get(position).getListername());

        holder.desopen.setOnClickListener(new View.OnClickListener() {
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
                editText.setText(list.get(position).getDesciptionshop());
            }
        });

        holder.maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.get(position).getLatitude().equals("") || list.get(position).getLongitude().equals("")){
                    Uri uri=Uri.parse("geo:0,0?q="+list.get(position).getAddressshop());
                    Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                    intent.setPackage("com.google.android.apps.maps");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else {
                    String uri = "http://maps.google.com/maps?daddr=" + list.get(position).getLatitude() + "," + list.get(position).getLongitude() + " (" + "Where the party is at" + ")";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

        if(list.get(position).getPhoto()!=null) {
            holder.addedimg.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            holder.addedimg.requestLayout();
            try {
                Glide.with(context).load(list.get(position).getPhoto()).into(holder.addedimg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        holder.addedimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent show=new Intent(context,vendorsIMAGE.class);
                show.putExtra("showinglocalmalls",list.get(position).getPhoto());
                context.startActivity(show);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView address,category,desopen;
        Button maps;
        ImageView addedimg;
        TextView listername;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            address=itemView.findViewById(R.id.textView20);
            category=itemView.findViewById(R.id.textView18);
            desopen=itemView.findViewById(R.id.desopen);
            maps=itemView.findViewById(R.id.maps);
            addedimg=itemView.findViewById(R.id.addedimg);
            listername=itemView.findViewById(R.id.textView30);
        }
    }
}
