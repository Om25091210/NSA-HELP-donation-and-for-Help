package com.aryomtech.nsabilaspur;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.transition.Slide;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.Serializable;
import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    private List<Uri> sliderItems;
    private List<String> sliderItemlinks;
    private List<String> SliderItemTexts;
    private ViewPager2 viewPager2;
    Context context;
    int totalurl;

    SliderAdapter(Context applicationContext, int total, List<Uri> sliderItems, List<String> sliderItemlinks,List<String> itemtexts, ViewPager2 viewPager2) {
        this.sliderItemlinks=sliderItemlinks;
        this.totalurl=total;
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
        this.context=applicationContext;
        this.SliderItemTexts=itemtexts;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slide_item_container,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        try {
            Glide.with(context).load(sliderItems.get(position)).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goforshowingviewpagerimage=new Intent(context,carousalImage.class);
                goforshowingviewpagerimage.putExtra("sendingparticulaurl",sliderItems.get(position)+"");
                goforshowingviewpagerimage.putExtra("totalurls",totalurl+"");
                goforshowingviewpagerimage.putExtra("sendinglistofspecialbadges", (Serializable) sliderItems);
                goforshowingviewpagerimage.putExtra("sendinglistoflinks", (Serializable) sliderItemlinks);
                goforshowingviewpagerimage.putExtra("sendinglistoftexts", (Serializable) SliderItemTexts);

                goforshowingviewpagerimage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(goforshowingviewpagerimage);
            }
        });

        if(position==sliderItems.size()-2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder{

        private RoundedImageView imageView;

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageSlide);

        }
        void setImage(SliderItem sliderItem){
            // If you want to display image from the internet,
            // You can put code here using glide or picasso.
            try {
                Glide.with(context).load(sliderItems).into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            sliderItems.addAll(sliderItems);
            notifyDataSetChanged();
        }
    };
}

