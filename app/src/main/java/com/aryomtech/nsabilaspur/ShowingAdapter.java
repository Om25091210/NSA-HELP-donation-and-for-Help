package com.aryomtech.nsabilaspur;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aryomtech.mylibrary.DoubleTapListener;
import com.aryomtech.mylibrary.LongPressListener;
import com.aryomtech.mylibrary.TapListener;
import com.aryomtech.mylibrary.Zoomy;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ShowingAdapter extends RecyclerView.Adapter<ShowingAdapter.ViewHolder> {

    Context context;
    ArrayList<String> url;

    public ShowingAdapter(ArrayList<String> url,  Context context) {
        this.context=context;
        this.url=url;
    }


    @NonNull
    @Override
    public ShowingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.dataofshowingimages,parent,false);
        ShowingAdapter.ViewHolder viewHolder = new ShowingAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowingAdapter.ViewHolder holder, int position) {
        try {
            Glide.with(context).load(url.get(position)).into(holder.viewImage);

            Zoomy.Builder builder = new Zoomy.Builder((Activity) context)
                    .target(holder.itemView)
                    .interpolator(new OvershootInterpolator())
                    .tapListener(new TapListener() {
                        @Override
                        public void onTap(View v) {

                        }
                    })
                    .longPressListener(new LongPressListener() {
                        @Override
                        public void onLongPress(View v) {

                        }
                    }).doubleTapListener(new DoubleTapListener() {
                        @Override
                        public void onDoubleTap(View v) {

                        }
                    });

            builder.register();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return url.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView viewImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            viewImage=itemView.findViewById(R.id.attachpic);
        }
    }
}