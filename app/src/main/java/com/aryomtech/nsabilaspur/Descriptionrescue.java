package com.aryomtech.nsabilaspur;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class Descriptionrescue extends AppCompatActivity {

    String  description,temploc,tempname,tempphone,tempcategory,seconftimecategory,temptype,typeagin;
    EditText desc;
    Button savedesc;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descriptionrescue);

        Intent getdes=getIntent();
        description=getdes.getStringExtra("sendingdescription");
        temploc=getdes.getStringExtra("sendingtemploc");
        tempname=getdes.getStringExtra("sendingtempname");
        tempphone=getdes.getStringExtra("sendingtempphone");
        tempcategory=getdes.getStringExtra("sendingcategoytemp");
        temptype=getdes.getStringExtra("sendingTypetemp");
        seconftimecategory=getdes.getStringExtra("sendingcategoytempsecondtime");
        typeagin=getdes.getStringExtra("sendingtypeagain");
        try {
            if (!seconftimecategory.equals("null") && !typeagin.equals("null")) {
                tempcategory = seconftimecategory;
                temptype=typeagin;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        Window window = Descriptionrescue.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(Descriptionrescue.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(Descriptionrescue.this, R.color.statusbar));

        desc=findViewById(R.id.des);
        desc.setText(description);
        savedesc=findViewById(R.id.savedesc);

        savedesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getdescriptionfromtext=desc.getText().toString();
                if(!getdescriptionfromtext.equals("") && getdescriptionfromtext.length()<=900){
                    Intent senddesc=new Intent(Descriptionrescue.this,giverblanket.class);
                    senddesc.putExtra("backloctointent",temploc);
                    senddesc.putExtra("backnametointent",tempname);
                    senddesc.putExtra("backphonetointent",tempphone);
                    senddesc.putExtra("backcategorytointent",tempcategory);
                    senddesc.putExtra("backTypetemptointent",temptype);
                    senddesc.putExtra("backdesctointent",getdescriptionfromtext);
                    startActivity(senddesc);
                    finish();
                }
                else{
                    Snackbar.make(findViewById(android.R.id.content),"Character limit 900.",Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

    public void canceldesc(View view) {
        Intent senddesc=new Intent(Descriptionrescue.this,giverblanket.class);
        senddesc.putExtra("backloctointent",temploc);
        senddesc.putExtra("backnametointent",tempname);
        senddesc.putExtra("backphonetointent",tempphone);
        senddesc.putExtra("backcategorytointent",tempcategory);
        senddesc.putExtra("backTypetemptointent",temptype);
        startActivity(senddesc);
        finish();
    }
    @Override
    public void onBackPressed() {
        Intent senddesc=new Intent(Descriptionrescue.this,giverblanket.class);
        senddesc.putExtra("backloctointent",temploc);
        senddesc.putExtra("backnametointent",tempname);
        senddesc.putExtra("backphonetointent",tempphone);
        senddesc.putExtra("backcategorytointent",tempcategory);
        senddesc.putExtra("backTypetemptointent",temptype);
        senddesc.putExtra("backdesctointent",desc.getText().toString());
        startActivity(senddesc);
        finish();
        super.onBackPressed();

    }
}