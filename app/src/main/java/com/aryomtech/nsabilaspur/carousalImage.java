package com.aryomtech.nsabilaspur;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class carousalImage extends AppCompatActivity {

    ImageView Viewimage;
    String totalurls,particularurl;
    List<String> storerestricturls;
    List<String> storerestricttexts;

    List<String> check;
    Button learn_more;
    int position;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carousal_image);
        Intent holdintent=getIntent();
        particularurl=holdintent.getStringExtra("sendingparticulaurl");

        Window window = carousalImage.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(carousalImage.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(carousalImage.this, R.color.statusbar));

        totalurls=holdintent.getStringExtra("totalurls");

        Viewimage=findViewById(R.id.Viewimage);
        learn_more=findViewById(R.id.imageView15);

        storerestricturls= new ArrayList<String>();
        storerestricttexts=new ArrayList<String>();

        check= new ArrayList<String>();
        check.add(particularurl);
        try {
            Glide.with(this).load(particularurl).into(Viewimage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> fetchingimagesurl = (ArrayList<String>) getIntent().getSerializableExtra("sendinglistofspecialbadges");
        ArrayList<String> fetchinglinksurl = (ArrayList<String>) getIntent().getSerializableExtra("sendinglistoflinks");
        ArrayList<String> fetchingtexts = (ArrayList<String>) getIntent().getSerializableExtra("sendinglistoftexts");

        for(int restrict=0;restrict<Integer.parseInt(totalurls);restrict++){
            storerestricturls.add(fetchingimagesurl.get(restrict));
            storerestricttexts.add(fetchingtexts.get(restrict));
        }
        for(int getpos=0;getpos<Integer.parseInt(totalurls);getpos++){

            if(Uri.parse(check.get(0)).equals(storerestricturls.get(getpos))){
                position=getpos;

            }
        }
        learn_more.setText(fetchingtexts.get(position));
        System.out.println(fetchingtexts);
        learn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int getpos=0;getpos<Integer.parseInt(totalurls);getpos++){

                    if(Uri.parse(check.get(0)).equals(storerestricturls.get(getpos))){
                        int position=getpos;

                        String url =fetchinglinksurl.get(position); //
                        Uri uriUrl = Uri.parse(url);
                        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                        startActivity(launchBrowser);

                    }
                }
            }
        });



    }
}