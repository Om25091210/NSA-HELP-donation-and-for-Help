package com.aryomtech.nsabilaspur;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoforGettingBadge extends AppCompatActivity {

    TextView setlevelinfo,showlevel,reach;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infofor_getting_badge);

        Intent gettingbagesdata=getIntent();

        String dataofimageinlevel=gettingbagesdata.getStringExtra("sendinglevelforINFO");
        ImageView setimage=findViewById(R.id.imageView17);
        setlevelinfo=findViewById(R.id.setlevelinfo);
        showlevel=findViewById(R.id.showlevel);
        reach=findViewById(R.id.reach);

        assert dataofimageinlevel != null;
        if(dataofimageinlevel.equals("Share This App")){

            setimage.setImageResource(R.drawable.questionmark);
            reach.setText("Share This App To");
            setlevelinfo.setText("10 People Individually");
            showlevel.setText("\"IMPACT MAKER\"");
        }
        else {
            setlevelinfo.setText(dataofimageinlevel);
            showlevel.setText("LEVEL " + dataofimageinlevel);
        }

        if(dataofimageinlevel.equals("1")){
            setimage.setImageResource(R.drawable.onegray);
        }
        else if(dataofimageinlevel.equals("2")){
            setimage.setImageResource(R.drawable.twogray);
        }
        else if(dataofimageinlevel.equals("5")){
            setimage.setImageResource(R.drawable.threegray);
        }
        else if(dataofimageinlevel.equals("10")){
            setimage.setImageResource(R.drawable.tengray);
        }
        else if(dataofimageinlevel.equals("15")){
            setimage.setImageResource(R.drawable.fifteengray);
        }
        else if(dataofimageinlevel.equals("20")){
            setimage.setImageResource(R.drawable.twentygray);
        }
        else if(dataofimageinlevel.equals("25")){
            setimage.setImageResource(R.drawable.twentyfivegray);
        }
        else if(dataofimageinlevel.equals("30")){
            setimage.setImageResource(R.drawable.thirtygray);
        }
        else if(dataofimageinlevel.equals("35")){
            setimage.setImageResource(R.drawable.thirtyfivegray);
        }
        else if(dataofimageinlevel.equals("40")){
            setimage.setImageResource(R.drawable.fourtygray);
        }
        else if(dataofimageinlevel.equals("45")){
            setimage.setImageResource(R.drawable.fourtyfivegray);
        }
        else if(dataofimageinlevel.equals("50")){
            setimage.setImageResource(R.drawable.fiftygray);
        }

    }
}