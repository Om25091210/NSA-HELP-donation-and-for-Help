package com.aryomtech.nsabilaspur;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SliderRewardsAdapter extends RecyclerView.Adapter<SliderRewardsAdapter.SliderViewHolder> {

    private List<Uri> sliderRewards;
    Context context;

    SliderRewardsAdapter(Context context, List<Uri> sliderRewards) {
        this.sliderRewards = sliderRewards;
        this.context=context;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.data_rewards_section,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        try {
            Glide.with(context).load(sliderRewards.get(position)).into(holder.rewardimg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return sliderRewards.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder{

        private ImageView rewardimg;

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            rewardimg=itemView.findViewById(R.id.rewardimg);

        }
        void setImage(SliderItem sliderItem){
            // If you want to display image from the internet,
            // You can put code here using glide or picasso.
            Glide.with(context).load(sliderRewards).into(rewardimg);
        }
    }
}