package com.aryomtech.nsabilaspur;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class Typeyourown extends AppCompatActivity {

    String getcategory;
    String Type;
    EditText typecategory;
    Button save;

    public static Activity type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typeyourown);

        Intent yourown=getIntent();
        Type=yourown.getStringExtra("sendingtype3002");

        type=this;

        save=findViewById(R.id.save);
        typecategory=findViewById(R.id.typecategory);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String gettypeyourowncategory=typecategory.getText().toString().trim();
                if(!gettypeyourowncategory.equals("") && gettypeyourowncategory.length()>2 && gettypeyourowncategory.length()<=20 && !gettypeyourowncategory.contains("..") && !gettypeyourowncategory.contains(",,")) {
                    Intent newintenttoother = new Intent(Typeyourown.this, giverother.class);
                    newintenttoother.putExtra("sendingcategorycategory", gettypeyourowncategory);
                    newintenttoother.putExtra("sendingtype2003", Type);
                    startActivity(newintenttoother);
                }
                else {
                    Snackbar.make(findViewById(android.R.id.content),"Category cannot be Empty and should use characters between 3-20.",Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }
    @Override
    public void onBackPressed() {

         if (Type.equals("DONATION")) {
                Intent gotohome = new Intent(Typeyourown.this, Donate.class);
                System.out.println("Donate");
                startActivity(gotohome);
                finish();
            } else {
                Intent gotohome = new Intent(Typeyourown.this, HELPsection.class);
                System.out.println("HELP");
                startActivity(gotohome);
                finish();
            }


        super.onBackPressed();


    }
}