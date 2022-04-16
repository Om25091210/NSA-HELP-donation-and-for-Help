package com.aryomtech.nsabilaspur;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class THEthreeSECTIONS extends AppCompatActivity {

    CardView talks,polls,quiz,badgessection;
    boolean connected = false;

    public static Activity threesection;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_h_ethree_s_e_c_t_i_o_n_s);

        threesection=this;

        Intent showthethreesection=getIntent();

        if(!isNetworkAvailable()){
            try {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();

                alertDialog.setTitle("Info");
                alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setCancelable(false);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(!isNetworkAvailable()){
                            alertDialog.dismiss();
                        }
                        else{
                            alertDialog.dismiss();
                        }

                    }
                });

                alertDialog.show();
            } catch (Exception e) {
                Toast.makeText(THEthreeSECTIONS.this, "No NetWork Connection Found!!", Toast.LENGTH_SHORT).show();
            }
        }

        Window window = THEthreeSECTIONS.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(THEthreeSECTIONS.this, R.color.talksmoderatedark));
        window.setNavigationBarColor(ContextCompat.getColor(THEthreeSECTIONS.this, R.color.talksmoderatedark));

        talks=findViewById(R.id.talks);
        badgessection=findViewById(R.id.badgesection);
        polls=findViewById(R.id.polls);
        quiz=findViewById(R.id.quizes);

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent quizzzz=new Intent(THEthreeSECTIONS.this,quizPortal.class);
                startActivity(quizzzz);
            }
        });

        polls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent polls=new Intent(THEthreeSECTIONS.this,POLLS.class);
                startActivity(polls);
            }
        });

        badgessection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent badges=new Intent(THEthreeSECTIONS.this,badgesection.class);
                startActivity(badges);
            }
        });

        talks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent talk=new Intent(THEthreeSECTIONS.this,Vendors.class);
                startActivity(talk);
            }
        });
    }


    public boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
            return connected;
        }
        else {
            connected = false;
            return connected;
        }
    }

    public void closecut(View view) {
        finish();
    }
}