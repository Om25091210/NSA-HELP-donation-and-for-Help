package com.aryomtech.nsabilaspur;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class BadgeView extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser useruuid;
    String profilepath;
    String ImagePath;
    ImageView img1,img2,img3,img4,img5;



    Button shareachievement,download;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    CircleImageView profile_imageachieve;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge_view);

        ImageView img = (ImageView)findViewById(R.id.imageView31);
        img1 = (ImageView)findViewById(R.id.imageView36);
        img2 = (ImageView)findViewById(R.id.imageView37);
        img3 = (ImageView)findViewById(R.id.imageView38);
        img4 = (ImageView)findViewById(R.id.imageView39);
        img5 = (ImageView)findViewById(R.id.imageView41);


        Animation aniSlide = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoomin);



        img.startAnimation(aniSlide);
        img1.startAnimation(aniSlide);
        img2.startAnimation(aniSlide);
        img3.startAnimation(aniSlide);
        img4.startAnimation(aniSlide);
        img5.startAnimation(aniSlide);

        aniSlide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                MediaPlayer mp = MediaPlayer.create(BadgeView.this, R.raw.xpsound);
                mp.start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                img.setVisibility(View.GONE);
                img1.setVisibility(View.GONE);
                img2.setVisibility(View.GONE);
                img3.setVisibility(View.GONE);
                img4.setVisibility(View.GONE);
                img5.setVisibility(View.GONE);
                //animatebottomfire();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



        Window window = BadgeView.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(BadgeView.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(BadgeView.this, R.color.statusbar));


        ActivityCompat.requestPermissions(this,new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        auth=FirebaseAuth.getInstance();
        useruuid=auth.getCurrentUser();

        Intent gettingbagesdata=getIntent();

        String dataoflevelimage=gettingbagesdata.getStringExtra("sendinglevelforINFO");

        ImageView setimage=findViewById(R.id.imageView16);
        profile_imageachieve=findViewById(R.id.profile_imageachieve);
        TextView user_name=findViewById(R.id.textView14);

        shareachievement=findViewById(R.id.shareachievement);
        download=findViewById(R.id.download);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareachievement.setVisibility(View.GONE);
                download.setVisibility(View.GONE);

                View view1 = getWindow().getDecorView().getRootView();
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

                    shareachievement.setVisibility(View.VISIBLE);
                    download.setVisibility(View.VISIBLE);
                    Toast.makeText(BadgeView.this, "Downloaded Successfully!!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        shareachievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view1 = getWindow().getDecorView().getRootView();
                view1.setDrawingCacheEnabled(true);

                shareachievement.setVisibility(View.GONE);
                download.setVisibility(View.GONE);

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

                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

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

                shareachievement.setVisibility(View.VISIBLE);
                download.setVisibility(View.VISIBLE);
            }
        });

        user_name.setText(useruuid.getDisplayName());

        if(dataoflevelimage.equals("1")){
            setimage.setImageResource(R.drawable.firstbadges);
        }
        else if(dataoflevelimage.equals("2")){
            setimage.setImageResource(R.drawable.secondbadges);
        }
        else if(dataoflevelimage.equals("5")){
            setimage.setImageResource(R.drawable.fivebadges);
        }
        else if(dataoflevelimage.equals("10")){
            setimage.setImageResource(R.drawable.tenbadges);
        }
        else if(dataoflevelimage.equals("15")){
            setimage.setImageResource(R.drawable.fifteenbadges);
        }
        else if(dataoflevelimage.equals("Share This App")){
            setimage.setImageResource(R.drawable.sharebadge);
        }
        else if(dataoflevelimage.equals("20")){
            setimage.setImageResource(R.drawable.twentybadges);
        }
        else if(dataoflevelimage.equals("25")){
            setimage.setImageResource(R.drawable.twentyfivebadges);
        }
        else if(dataoflevelimage.equals("30")){
            setimage.setImageResource(R.drawable.thirtybadges);
        }
        else if(dataoflevelimage.equals("35")){
            setimage.setImageResource(R.drawable.thirtyfivebadges);
        }
        else if(dataoflevelimage.equals("40")){
            setimage.setImageResource(R.drawable.fourtybadges);
        }
        else if(dataoflevelimage.equals("45")){
            setimage.setImageResource(R.drawable.fourtyfivebadges);
        }
        else if(dataoflevelimage.equals("50")){
            setimage.setImageResource(R.drawable.fiftybadges);
        }

        retrievingPhotofromdatabase();
    }


    private void retrievingPhotofromdatabase() {


        profilepath="picture/"+useruuid.getUid()+".png";

        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        storageReference=FirebaseStorage.getInstance().getReference().child(profilepath);


        try{
            final File localfile=File.createTempFile(useruuid.getUid(),"png");
            storageReference.getFile(localfile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap= BitmapFactory.decodeFile(localfile.getAbsolutePath());
                            ((ImageView)findViewById(R.id.profile_imageachieve)).setImageBitmap(bitmap);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}