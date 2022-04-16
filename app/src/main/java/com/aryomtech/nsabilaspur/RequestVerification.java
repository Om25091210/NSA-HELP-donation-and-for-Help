package com.aryomtech.nsabilaspur;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestVerification extends AppCompatActivity {

    Button continueregisteration;
    FirebaseDatabase database;
    DatabaseReference ref;
    String emailverified;
    FirebaseAuth mAuth;
    FirebaseUser useruuid;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_verification);

        mAuth=FirebaseAuth.getInstance();
        useruuid= mAuth.getCurrentUser();

        Window window = RequestVerification.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(RequestVerification.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(RequestVerification.this, R.color.statusbar));

        boolean verifying = getSharedPreferences("PREFERENCE2", MODE_PRIVATE)
                .getBoolean("verify", false);
        emailverified = getSharedPreferences("PREFERENCEemail", MODE_PRIVATE)
                .getString("emailverified","NULL");
        continueregisteration=findViewById(R.id.register);
        continueregisteration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(verifying && emailverified.equals(useruuid.getEmail())) {
                        Toast.makeText(RequestVerification.this, "You are already Verified", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibe.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            //deprecated in API 26
                            vibe.vibrate(500);
                        }
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "aryomtech@gmail.com"));
                        intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
                        intent.putExtra(Intent.EXTRA_TEXT, "your_text");
                        startActivity(intent);
                    }
                } catch(Exception e) {
                    Toast.makeText(RequestVerification.this, "Sorry...You don't have any mail app", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

    }
    @Override
    public void onBackPressed() {

        Intent gotohome=new Intent(RequestVerification.this,ProfileActivity.class);
        startActivity(gotohome);
        finish();
        super.onBackPressed();

    }
}