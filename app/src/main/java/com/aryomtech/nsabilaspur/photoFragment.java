package com.aryomtech.nsabilaspur;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;


import com.aryomtech.mylibrary.DoubleTapListener;
import com.aryomtech.mylibrary.LongPressListener;
import com.aryomtech.mylibrary.TapListener;
import com.aryomtech.mylibrary.Zoomy;
import com.bumptech.glide.Glide;

public class photoFragment extends Fragment {

    ImageView photo;
    String post;
    public photoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_photo, container, false);

        photo=view.findViewById(R.id.imageView52);

        post = getArguments().getString("sendingphoto");

        try{
            Glide.with(this).load(post).into(photo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Zoomy.Builder builder = new Zoomy.Builder((Activity)getContext())
                .target(photo)
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

        return view;
    }
}