package com.aryomtech.nsabilaspur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import uk.co.senab.photoview.PhotoViewAttacher;

public class vendorsIMAGE extends AppCompatActivity {

    ImageView vendorimages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendors_i_m_a_g_e);

        Intent showtime=getIntent();
        String getphotourl=showtime.getStringExtra("showinglocalmalls");

        vendorimages=findViewById(R.id.imageView24);
        try{
            Glide.with(this).load(getphotourl).into(vendorimages);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PhotoViewAttacher photoAttacher;
        photoAttacher= new PhotoViewAttacher(vendorimages);
        photoAttacher.update();
    }
}