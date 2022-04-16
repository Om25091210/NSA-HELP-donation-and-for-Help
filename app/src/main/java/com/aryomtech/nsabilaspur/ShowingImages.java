package com.aryomtech.nsabilaspur;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

public class ShowingImages extends AppCompatActivity {

    RecyclerView showingimages;

    String url1;
    String url2;
    String url3;
    String url4;
    String totalimages;
    ArrayList<String> urlarray;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showing_images);

        Window window = ShowingImages.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(ShowingImages.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(ShowingImages.this, R.color.statusbar));

        Intent data=getIntent();
        urlarray= new ArrayList<String>();

        url1=data.getStringExtra("sendingurl1");
        url2=data.getStringExtra("sendingurl2");
        url3=data.getStringExtra("sendingurl3");
        url4=data.getStringExtra("sendingurl4");
        totalimages=data.getStringExtra("sendingTotalImages");

        showingimages= findViewById(R.id.recyclerviewShowingImages);
        showingimages.setHasFixedSize(true);
        showingimages.setLayoutManager( new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        int len=Integer.parseInt(totalimages);
        if(len==1){
            urlarray.add(url1);
        }
        if(len==2){
            urlarray.add(url1);
            urlarray.add(url2);
        }
        else if(len==3){
            urlarray.add(url1);
            urlarray.add(url2);
            urlarray.add(url3);
        }
        else if(len==4){
            urlarray.add(url1);
            urlarray.add(url2);
            urlarray.add(url3);
            urlarray.add(url4);
        }

        ShowingAdapter ShowingAdapter = new ShowingAdapter(urlarray,ShowingImages.this);
        showingimages.setAdapter(ShowingAdapter);
    }
}