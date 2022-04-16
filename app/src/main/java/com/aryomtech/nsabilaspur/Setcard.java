package com.aryomtech.nsabilaspur;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
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
import com.google.firebase.iid.FirebaseInstanceId;
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
import java.util.Locale;
import java.util.UUID;

public class Setcard extends AppCompatActivity implements LocationListener {

    Bitmap bitmap;
    ImageView addcardphoto;
    String latitude="",longitude="";
    EditText name_input,address_input,description_input,lister_name_input;

    private final  int REQUEST_CHECK_CODE=8989;
    private LocationSettingsRequest.Builder builder;

    View view17;
    LocationManager locationManager;
    int upload_check=0;
    String pushkey;
    DatabaseReference ref;
    Button button_location;
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseUser useruuid;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setcard);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("CardRequest");

        auth = FirebaseAuth.getInstance();
        useruuid = auth.getCurrentUser();

        addcardphoto=findViewById(R.id.addcardphoto);
        name_input=findViewById(R.id.shop_adress_input);
        address_input=findViewById(R.id.address_inputcard);
        description_input=findViewById(R.id.description_input);
        lister_name_input=findViewById(R.id.lister_name_input);

        view17=findViewById(R.id.view17);

        //address_input.setEnabled(false);

        if (ContextCompat.checkSelfPermission(Setcard.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Setcard.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }

        view17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Setcard.this, "Detecting Location!", Toast.LENGTH_SHORT).show();
                getLocation();
            }
        });

        LocationRequest request=new LocationRequest()
                .setFastestInterval(1500)
                .setInterval(3000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        builder=new LocationSettingsRequest.Builder()
                .addLocationRequest(request);

        Task<LocationSettingsResponse> result=
                LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    task.getResult(ApiException.class);
                } catch (ApiException e) {
                    switch (e.getStatusCode()){
                        case LocationSettingsStatusCodes
                                .RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException= (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(Setcard.this,REQUEST_CHECK_CODE);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }catch (ClassCastException ex){

                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        {
                            break;
                        }
                    }
                }
            }
        });


        Window window = Setcard.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(Setcard.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(Setcard.this, R.color.statusbar));
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5, Setcard.this);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void onLocationChanged(Location location) {

        latitude= String.valueOf(location.getLatitude());
        longitude= String.valueOf(location.getLongitude());
        try {
            Geocoder geocoder = new Geocoder(Setcard.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);

            address_input.setText(address);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    public void cancelposting(View view) {
        finish();
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
        StorageReference riversRef = FirebaseStorage.getInstance().getReference().child("Vendors/"+randomKey+".png");

        riversRef.putFile(parse)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(
                                new OnCompleteListener<Uri>() {

                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        String fileLink = task.getResult().toString();

                                        ref.child(pushkey).child("photo").setValue(fileLink);

                                        pd.dismiss();
                                        finish();
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

    public void addcardphoto(View view) {

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setActivityTitle("NSA")
                .setAspectRatio(1,1) //You can skip this for free form aspect ratio)
                .start(Setcard.this);
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
                    // You can update this bitmap to your server
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver() , Uri.parse(String.valueOf(resultUri)));
                    addcardphoto.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void postcard(View view) {

        String nameInput=name_input.getText().toString();
        String addressInput=address_input.getText().toString();
        String descriptionInput=description_input.getText().toString();
        String lister_name=lister_name_input.getText().toString();

        pushkey=ref.push().getKey();
        if(!lister_name.equals("") && !nameInput.equals("") && !addressInput.equals("") && !descriptionInput.equals("") && !(addcardphoto.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.addpics).getConstantState())) {
            if (!nameInput.equals("")) {
                ref.child(pushkey).child("shopcategory").setValue(nameInput.trim());
            }
            if(!lister_name.equals("")){
                ref.child(pushkey).child("listername").setValue(lister_name.trim());
                String DeviceToken= FirebaseInstanceId.getInstance().getToken();
                ref.child(pushkey).child("deviceToken").setValue(DeviceToken);
                ref.child(pushkey).child("userUuid").setValue(useruuid.getUid());
            }
            if (!addressInput.equals("")) {

                if(!latitude.equals("") && !longitude.equals("")){
                    ref.child(pushkey).child("latitude").setValue(latitude);
                    ref.child(pushkey).child("longitude").setValue(longitude);
                }
                ref.child(pushkey).child("addressshop").setValue(addressInput.trim());
            }
            if (!descriptionInput.equals("")) {
                ref.child(pushkey).child("desciptionshop").setValue(descriptionInput.trim());
            }
            if (!(addcardphoto.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.addpics).getConstantState())) {
                addcardphoto.invalidate();
                BitmapDrawable drawable6 = (BitmapDrawable) addcardphoto.getDrawable();
                Bitmap bitmap6 = drawable6.getBitmap();
                getImageUri(getApplicationContext(), bitmap6);
            }
        }
        else{
            Snackbar.make(findViewById(android.R.id.content),"Please Fill The Required Information.",Snackbar.LENGTH_LONG).show();
        }
    }

    public void cutcard(View view) {
        finish();
    }
}