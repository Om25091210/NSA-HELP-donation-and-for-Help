package com.aryomtech.nsabilaspur;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;

import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


public class Viewimage extends AppCompatActivity {

    String viewimage;
    ImageView image;
    int pos=0;
    List<String> listofurlimages;
    String positionofimage;
    FirebaseDatabase database;
    DatabaseReference reference;

    FloatingActionButton share,download;
    String ImagePath;

    private static Uri imageUri = null;

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewimage);

        Intent getview=getIntent();

        viewimage=getview.getStringExtra("sendingurlimg");

        positionofimage=getview.getStringExtra("sendingcurrentimageurlpos");
        pos=Integer.parseInt(positionofimage);

        image=findViewById(R.id.Viewimage);
        download=findViewById(R.id.download);

        listofurlimages= new ArrayList<String>();

        transparentStatusAndNavigation();

        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("gallery");
        reference.child("data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int total=dataSnapshot.child("total").getValue(int.class);
                for(int i=1;i<=total;i++) {

                    String img1 = dataSnapshot.child("images").child(i + "").getValue(String.class);
                    listofurlimages.add(img1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        image.setOnTouchListener(new OnSwipeTouchListener(this){
            @Override
            public void onSwipeLeft() {
                pos=pos+1;
                image.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                image.requestLayout();

                if(pos<listofurlimages.size()){
                    image.setImageResource(0);
                   /* try{
                        Glide.with(Viewimage.this).load(listofurlimages.get(pos)).into(image);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                }
                else{
                    pos=listofurlimages.size()-1;
                    /*try{
                        Glide.with(Viewimage.this).load(listofurlimages.get(pos)).into(image);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                }
            }
            @Override
            public void onSwipeRight(){
                pos=pos-1;
                image.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                image.requestLayout();
                if(pos>=0){
                    image.setImageResource(0);
                    /*try{
                        Glide.with(Viewimage.this).load(listofurlimages.get(pos)).into(image);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                }
                else{
                    pos=0;
                   /* try{
                        Glide.with(Viewimage.this).load(listofurlimages.get(pos)).into(image);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                }
            }
        });

        try {
            Glide.with(this).load(viewimage).into(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        share=findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                Bitmap icon = bitmap;
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("*/*");

                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "NSA");
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        values);


                OutputStream outstream;
                try {
                    outstream = getContentResolver().openOutputStream(uri);
                    icon.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
                    outstream.close();
                } catch (Exception e) {
                    System.err.println(e.toString());
                }

                share.putExtra(Intent.EXTRA_STREAM, uri);
                share.putExtra(Intent.EXTRA_TEXT, "NSA : HUMANITY AT WORK");
                startActivity(Intent.createChooser(share, "NSA :Share Image"));
            }
        });


    }

    private void transparentStatusAndNavigation() {
        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            );
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    private void setWindowFlag(final int bits, boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void download(View view) {

        share.setVisibility(View.GONE);
        download.setVisibility(View.GONE);

        View view1 = image;
        view1.setDrawingCacheEnabled(true);

        Bitmap bitmap = Bitmap.createBitmap(view1.getDrawingCache());
        view1.setDrawingCacheEnabled(false);

        String filePath = Environment.getExternalStorageDirectory()+"/Download/"+ Calendar.getInstance().getTime().toString()+".jpg";
        File fileScreenshot = new File(filePath);
        FileOutputStream fileOutputStream = null;
        try {
            Random random=new Random();
            int ran=random.nextInt(999999999);
            fileOutputStream = new FileOutputStream(fileScreenshot);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            ImagePath = MediaStore.Images.Media.insertImage(
                    getContentResolver(),
                    bitmap,
                    "NSA_image"+ran,
                    "NSA_image"

            );
            fileOutputStream.flush();
            fileOutputStream.close();

            share.setVisibility(View.VISIBLE);
            download.setVisibility(View.VISIBLE);
            Toast.makeText(Viewimage.this, "Downloaded Successfully!!", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
        }

        }
}