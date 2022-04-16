package com.aryomtech.nsabilaspur;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class post extends AppCompatActivity {

    EditText et_tweet_text;
    ImageView postPHOTO;
    Bitmap bitmap;
    ImageView post;
    EditText username;
    FirebaseAuth auth;
    FirebaseUser useruuid;

    Toolbar toolbarWidget;

    int finish=0;
    String pushkey;
    FirebaseDatabase database;
    DatabaseReference reference;
    ImageView profilefeed;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Intent postint=getIntent();

        Window window = post.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(post.this, R.color.talksmoderatedark));
        window.setNavigationBarColor(ContextCompat.getColor(post.this, R.color.talksmoderatedark));

        auth = FirebaseAuth.getInstance();
        useruuid = auth.getCurrentUser();

        String dpgameuser= getSharedPreferences("dpURL",MODE_PRIVATE)
                .getString("dpurlforstoring","Null");

        profilefeed=findViewById(R.id.profilefeed);
        try{
            Glide.with(this).load(dpgameuser).into(profilefeed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        et_tweet_text=findViewById(R.id.et_tweet_text);
        postPHOTO=findViewById(R.id.imageView44);
        post=findViewById(R.id.imageView45);

        username=findViewById(R.id.username);
        String user_name=getSharedPreferences("user_name_post",MODE_PRIVATE)
                .getString("user_name","Anonymous");

        username.setText(user_name);
        username.setEnabled(false);

        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("Talks");


        postPHOTO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setActivityTitle("NSA")
                        .setAspectRatio(1,1) //You can skip this for free form aspect ratio)
                        .start(post.this);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                // Set uri as Image in the ImageView:
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver() , Uri.parse(String.valueOf(resultUri)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                post.setImageBitmap(bitmap);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        final String randomKey= UUID.randomUUID().toString();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, ""+randomKey, null);
        uploadPicture(Uri.parse(path));
        return Uri.parse(path);
    }

    private void uploadPicture(Uri parse) {

        final ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle("Finishing Up!...");
        pd.setCancelable(false);
        pd.show();
        final String randomKey= UUID.randomUUID().toString();
        StorageReference riversRef = FirebaseStorage.getInstance().getReference().child("Talks/"+randomKey+".png");

        riversRef.putFile(parse)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(
                                new OnCompleteListener<Uri>() {

                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        String fileLink = task.getResult().toString();

                                        reference.child(pushkey).child("post").setValue(fileLink);

                                        finish();
                                        Toast.makeText(getApplicationContext(), "Uploaded!!", Toast.LENGTH_LONG).show();
                                        pd.dismiss();
                                    }
                                });
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content),"Image Uploaded.",Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed To Upload", Toast.LENGTH_LONG).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot tasksnapshot) {
                double progressPercent=(100.00 * tasksnapshot.getBytesTransferred() / tasksnapshot.getTotalByteCount());
                pd.setMessage("Progress: "+(int) progressPercent+"%");
            }
        });
    }

    public void POST(View view) {
        String tweet=et_tweet_text.getText().toString();
        String username_input=username.getText().toString();

        pushkey=reference.push().getKey();
        if(!tweet.equals("")){

            if(!username_input.equals("")){
                reference.child(pushkey).child("Name").setValue(username_input.trim());
                reference.child(pushkey).child("search").setValue(username_input.toLowerCase());

                getSharedPreferences("user_name_post",MODE_PRIVATE).edit()
                        .putString("user_name",username_input).apply();
            }
            else{
                reference.child(pushkey).child("Name").setValue("Anonymous");
                reference.child(pushkey).child("search").setValue("anonymous");
            }
            reference.child(pushkey).child("caption").setValue(tweet.trim());
            reference.child(pushkey).child("likes").setValue(0);
            reference.child(pushkey).child("uid").setValue(useruuid.getUid());
            reference.child(pushkey).child("key").setValue(pushkey);
            reference.child(pushkey).child("totalcomments").setValue(0);

            String dpgameuser= getSharedPreferences("dpURL",MODE_PRIVATE)
                    .getString("dpurlforstoring","Null");

            reference.child(pushkey).child("dpurl").setValue(dpgameuser);

            if(bitmap!=null){
                post.invalidate();
                BitmapDrawable drawable6 = (BitmapDrawable) post.getDrawable();
                Bitmap bitmap6 = drawable6.getBitmap();
                getImageUri(getApplicationContext(), bitmap6);
                finish=1;
            }
            if(finish==0){
                finish();
            }
        }
    }

    public void close(View view) {
        finish();
    }
}