package com.aryomtech.nsabilaspur;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.net.URI;
import java.util.ArrayList;

public class specialHolder extends RecyclerView.Adapter<specialHolder.ViewHolder> {

    Context context;

    ArrayList<String> mArrayUri;
    public specialHolder(Context context, ArrayList<String> mArrayUri) {
        this.context=context;
        this.mArrayUri=mArrayUri;
    }

    @NonNull
    @Override
    public specialHolder.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.contentofspecialedition,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull specialHolder.ViewHolder holder, int position) {
        try {
            Glide.with(context).load(mArrayUri.get(position)).into(holder.viewImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mArrayUri.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView viewImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            viewImage=itemView.findViewById(R.id.specialbadges);
        }
    }
}