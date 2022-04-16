package com.aryomtech.nsabilaspur;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.core.content.FileProvider.getUriForFile;

public class ProfileActivity extends AppCompatActivity {

    private CircleImageView ProfileImage;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    DatabaseReference ref,refdonorsdata,Verifiedref,timelineref;
    FirebaseDatabase database;

    int i;
    int k=0;

    View bckhome;
    String userindividualkey;
    TextView home;
    Button btnLogout;
    Bitmap bitmap;
    Button authprofile;
    public static String fileName;
    Button request;
    String containsnum,containsadd;
    String imageString,Userno,Useradd;
    private static final String TAG = Addphotos.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;


    String emailverified,userprofilekey,profilepath;
    private FirebaseAuth mAuth;
    static FirebaseUser useruuid;
    GoogleSignInClient mGoogleSignInClient;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent get=getIntent();

        mAuth=FirebaseAuth.getInstance();
        useruuid = mAuth.getCurrentUser();

        Window window = ProfileActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(ProfileActivity.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(ProfileActivity.this, R.color.statusbar));


        Userno = getSharedPreferences("StoringNumber", MODE_PRIVATE)
                .getString("NumberofUser","Enter your number");

        Useradd = getSharedPreferences("StoringAddress", MODE_PRIVATE)
                .getString("addressofUser","Enter your address");

        userindividualkey= getSharedPreferences("PREFERENCE1", MODE_PRIVATE)
                .getString("initialize","NO");


        ProfileImage = (CircleImageView) findViewById(R.id.profile_image);

        database=FirebaseDatabase.getInstance();
        refdonorsdata=database.getReference().child("DonorsData");

        Verifiedref=FirebaseDatabase.getInstance().getReference().child("VerifiedNGOs");
        timelineref=FirebaseDatabase.getInstance().getReference().child("Timeline");

        userprofilekey = getSharedPreferences("PREFERENCE1", MODE_PRIVATE)
                .getString("initialize","NO");
        profilepath="picture/"+useruuid.getUid()+".png";


        /*RequestOptions requestOptions=new RequestOptions(); for gmailphoto
        requestOptions.placeholder(R.drawable.nsaicon);
        Glide.with(ProfileActivity.this)
                .load(user.getPhotoUrl())
                .apply(requestOptions)
                .into(ProfileImage);*/

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
                            ((ImageView)findViewById(R.id.profile_image)).setImageBitmap(bitmap);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setActivityTitle("NSA")
                            .setAspectRatio(1,1) //You can skip this for free form aspect ratio)
                            .start(ProfileActivity.this);
            }
        });

        TextView emailofprofile=findViewById(R.id.emailofprofile);
        TextView nameofprofile=findViewById(R.id.nameofprofile);

        try {
            emailofprofile.setText(useruuid.getEmail());
            nameofprofile.setText(useruuid.getDisplayName());
        } catch (Exception e) {
            e.printStackTrace();
            emailofprofile.setText(" please Login");
        }

        btnLogout=findViewById(R.id.btnLogout);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this, gso);


       /* request=findViewById(R.id.register);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveTOrequestregistration=new Intent(ProfileActivity.this,RequestVerification.class);
                startActivity(moveTOrequestregistration);
                finish();
            }
        });*/


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                Intent login=new Intent(ProfileActivity.this, Login.class);
                startActivity(login);
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
                ProfileImage.setImageBitmap(bitmap);
                ProfileImage.invalidate();
                BitmapDrawable drawable6 = (BitmapDrawable) ProfileImage.getDrawable();
                Bitmap bitmap6 = drawable6.getBitmap();
                getImageUri(getApplicationContext(), bitmap6);

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
        pd.setTitle("Uploading Image...");
        pd.setCancelable(false);
        pd.show();
        final String randomKey= UUID.randomUUID().toString();
        StorageReference riversRef = FirebaseStorage.getInstance().getReference().child(profilepath);

        riversRef.putFile(parse)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(
                                new OnCompleteListener<Uri>() {

                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        String fileLink = task.getResult().toString();

                                        getSharedPreferences("dpURL",MODE_PRIVATE).edit()
                                                .putString("dpurlforstoring",fileLink).apply();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                        refdonorsdata.child(useruuid.getUid()).child("ConfirmDonation").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                database=FirebaseDatabase.getInstance();
                                                ref=database.getReference("Donors");
                                                for(DataSnapshot snap:dataSnapshot.getChildren()){
                                                    ref.child(snap.getKey()).child("dp").setValue(fileLink);
                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                        refdonorsdata.child(useruuid.getUid()).child("ConfirmedHELP").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                database=FirebaseDatabase.getInstance();
                                                ref=database.getReference("Donors");
                                                for(DataSnapshot snap:snapshot.getChildren()){
                                                    ref.child(Objects.requireNonNull(snap.getKey())).child("dp").setValue(fileLink);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                        Verifiedref.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if(snapshot.child(useruuid.getUid()).exists()){
                                                  Verifiedref.child(useruuid.getUid()).child("images").setValue(fileLink);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                        timelineref.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if(snapshot.exists()) {
                                                    for (DataSnapshot walkthroughkeys : snapshot.getChildren()) {
                                                        if (snapshot.child(walkthroughkeys.getKey()).child("respondeduid").getValue(String.class).equals(useruuid.getUid())) {
                                                            timelineref.child(walkthroughkeys.getKey()).child("respondeddp").setValue(fileLink);
                                                        }
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                        long delayInMillis = 1000;
                                        Timer timer = new Timer();
                                        timer.schedule(new TimerTask() {
                                            @Override
                                            public void run() {
                                                pd.dismiss();
                                            }
                                        }, delayInMillis);
                                    }
                                });
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

    private void signOut() {

        getSharedPreferences("onceforloginandcheck", MODE_PRIVATE).edit()
                .putString("showonlyonceinfirstlogin", "true").apply();

        getSharedPreferences("isloginornot",MODE_PRIVATE).edit()
                .putBoolean("islogin",false).apply();

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ProfileActivity.this, "SignOut Successfully", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
    }

    public void donationSection(View view) {
        Intent sections=new Intent(ProfileActivity.this,MyDonation.class);

        startActivity(sections);
        finish();
    }

    public void Pickupsection(View view) {
        boolean verifying = getSharedPreferences("PREFERENCE2", MODE_PRIVATE)
                .getBoolean("verify", false);

        emailverified = getSharedPreferences("PREFERENCEemail", MODE_PRIVATE)
                .getString("emailverified","NULL");


        if(verifying && emailverified.equals(useruuid.getEmail())) {

            Intent sections = new Intent(ProfileActivity.this, PickUpDetails.class);
            startActivity(sections);
            finish();
        }
        else{
            Snackbar.make(findViewById(android.R.id.content),"Only Registered NGO Can Open This Section.",Snackbar.LENGTH_LONG).show();
        }
    }

    public void bck(View view) {
        Intent profile=new Intent(ProfileActivity.this,MainActivity2.class);
        startActivity(profile);
        overridePendingTransition(R.anim.leftanim,R.anim.anim);
        finish();
    }
    @Override
    public void onBackPressed() {

        Intent gotohome=new Intent(ProfileActivity.this,MainActivity2.class);
        startActivity(gotohome);
        finish();
        super.onBackPressed();

    }

    public void requestHelp(View view) {
        Intent rqhelps=new Intent(ProfileActivity.this,RequestHelp.class);

        startActivity(rqhelps);
        finish();

    }

    public void tohelp(View view) {
        boolean verifying = getSharedPreferences("PREFERENCE2", MODE_PRIVATE)
                .getBoolean("verify", false);

        emailverified = getSharedPreferences("PREFERENCEemail", MODE_PRIVATE)
                .getString("emailverified","NULL");


        if(verifying && emailverified.equals(useruuid.getEmail())) {

            Intent sections = new Intent(ProfileActivity.this, Tohelp.class);
            startActivity(sections);
            finish();
        }
        else{
            Snackbar.make(findViewById(android.R.id.content),"Only Registered NGO Can Open This Section.",Snackbar.LENGTH_LONG).show();
        }
    }

    public void generate(View view) {

        boolean verifying = getSharedPreferences("PREFERENCE2", MODE_PRIVATE)
                .getBoolean("verify", false);

        emailverified = getSharedPreferences("PREFERENCEemail", MODE_PRIVATE)
                .getString("emailverified","NULL");


        if(verifying && emailverified.equals(useruuid.getEmail())) {

            Intent sections = new Intent(ProfileActivity.this, QR.class);
            startActivity(sections);
        }
        else{
            Snackbar.make(findViewById(android.R.id.content),"Only Registered NGO Can Generate Their QR Code.",Snackbar.LENGTH_LONG).show();
        }

    }
}