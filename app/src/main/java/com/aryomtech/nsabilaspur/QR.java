package com.aryomtech.nsabilaspur;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QR extends AppCompatActivity {

    ImageView qrImage;
    private FirebaseAuth mAuth;
    static FirebaseUser useruuid;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r);


        mAuth=FirebaseAuth.getInstance();
        useruuid = mAuth.getCurrentUser();


        Window window = QR.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(QR.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(QR.this, R.color.statusbar));

        qrImage = findViewById(R.id.qrPlaceHolder);
        QRGEncoder qrgEncoder = new QRGEncoder(useruuid.getUid(),null, QRGContents.Type.TEXT,500);
        try {
            Bitmap qrBits = qrgEncoder.encodeAsBitmap();
            qrImage.setImageBitmap(qrBits);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}