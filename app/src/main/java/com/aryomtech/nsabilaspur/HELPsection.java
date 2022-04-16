package com.aryomtech.nsabilaspur;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
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
import com.google.firebase.storage.StorageReference;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class HELPsection extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;

    FirebaseAuth auth;
    FirebaseUser useruuid;
    String Type="HELP";
    DatabaseReference reflink;
    public static final int REQUEST_CODE = 1;

    FirebaseDatabase database;
    private ImageView Imagedonate;
    String getlink,sharecountpreference;
    int storesharecount=0;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigationMyProfile:
                    Intent profile=new Intent(HELPsection.this,ProfileActivity.class);
                    startActivity(profile);
                    //overridePendingTransition(R.anim.fadein,R.anim.anim);
                    finish();
                    return true;
                case R.id.navigationMyCourses:
                    return true;
                case R.id.navigationHome:
                    Intent home=new Intent(HELPsection.this,MainActivity2.class);
                    startActivity(home);
                    // overridePendingTransition(R.anim.leftanim,R.anim.anim);
                    finish();
                    return true;
                case  R.id.navigationSearch:
                    Intent help=new Intent(HELPsection.this,Donate.class);
                    startActivity(help);
                    //   overridePendingTransition(R.anim.leftanim,R.anim.anim);
                    finish();
                    return true;
                case  R.id.navigationMenu:
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.openDrawer(GravityCompat.START);
                    return true;
            }
            return false;
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_e_l_psection);
        Intent get=getIntent();

        auth=FirebaseAuth.getInstance();
        useruuid=auth.getCurrentUser();


        Window window = HELPsection.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(HELPsection.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(HELPsection.this, R.color.statusbar));

        Imagedonate = findViewById(R.id.donateupload);

        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        storageReference=FirebaseStorage.getInstance().getReference().child("uploadpictures/HELP.png");


        try{
            final File localfile=File.createTempFile("HELP","png");
            storageReference.getFile(localfile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap= BitmapFactory.decodeFile(localfile.getAbsolutePath());
                            ((ImageView)findViewById(R.id.donateupload)).setImageBitmap(bitmap);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        CardView food=findViewById(R.id.HELPFood);

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String givefood="Food";
                Intent food=new Intent(HELPsection.this,giverfood.class);
                food.putExtra("donatefood",givefood);
                food.putExtra("sendingtype2003",Type);
                startActivity(food);
                finish();
            }
        });

        /*CardView cloth=findViewById(R.id.HELPcloth);
        cloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String givecloth="Clothes";
                Intent cloth=new Intent(HELPsection.this,givercloth.class);
                cloth.putExtra("donatecloth",givecloth);
                cloth.putExtra("sendingtype2003",Type);
                startActivity(cloth);
                finish();
            }
        });*/

       /*CardView blanket=findViewById(R.id.HELPblanket);
        blanket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String giveblanket="Old Newspapers";
                Intent blanket=new Intent(HELPsection.this,giverblanket.class);
                blanket.putExtra("donateblanket",giveblanket);
                blanket.putExtra("sendingtype2003",Type);
                startActivity(blanket);
                finish();
            }
        });*/
        CardView blood=findViewById(R.id.HELPblood);
        blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String givebood="Blood";
                Intent blood=new Intent(HELPsection.this,giverblood.class);
                blood.putExtra("donateblood",givebood);
                blood.putExtra("sendingtype2003",Type);
                startActivity(blood);
                finish();
            }
        });
        CardView study=findViewById(R.id.HELPstudy);
        study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String givestudy="Educational";
                Intent study=new Intent(HELPsection.this,giverstudy.class);
                study.putExtra("donatestudy",givestudy);
                study.putExtra("sendingtype2003",Type);
                startActivity(study);
                finish();
            }
        });

        CardView others=findViewById(R.id.HELPother);
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent others=new Intent(HELPsection.this,Typeyourown.class);
                others.putExtra("sendingtype3002",Type);
                startActivity(others);
                finish();
            }
        });

        CardView sack=findViewById(R.id.donatesack);
        sack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String givesack="Sack(बोरा)";
                Intent sack=new Intent(HELPsection.this,giversack.class);
                sack.putExtra("donatesack",givesack);
                sack.putExtra("sendingtype2003",Type);
                startActivity(sack);
                finish();
            }
        });

        CardView rescue=findViewById(R.id.HELPrescue);
        rescue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String giverescue="Animal Rescue";
                Intent rescueanimal=new Intent(HELPsection.this,giverrescue.class);
                rescueanimal.putExtra("donaterescue",giverescue);
                rescueanimal.putExtra("sendingtype2003",Type);
                startActivity(rescueanimal);
                finish();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        bottomNavigationView.setSelectedItemId(R.id.navigationMyCourses);

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                bottomNavigationView.setSelectedItemId(R.id.navigationMyCourses);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            bottomNavigationView.setSelectedItemId(R.id.navigationMyCourses);
        } else {
            Intent gotohome=new Intent(HELPsection.this,MainActivity2.class);
            startActivity(gotohome);
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_faq) {
            database=FirebaseDatabase.getInstance();
            reflink=database.getReference("link");


            reflink.child("app").child("trigger").setValue("yes");
            Query querylink=reflink.child("app");
            querylink.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshotapp) {
                    String linkfaq=dataSnapshotapp.child("nsafaq").getValue(String.class);
                    String url =linkfaq;
                    Uri uriUrl = Uri.parse(url);
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else if(id==R.id.nav_timeline){

            Intent timeline=new Intent(HELPsection.this,Timeline.class);
            startActivity(timeline);
            finish();
        }

        else if(id==R.id.nav_share){


            sharecountpreference = getSharedPreferences("storesharecount", MODE_PRIVATE)
                    .getString("sharecount", "0");

            database = FirebaseDatabase.getInstance();
            reflink = database.getReference("link");

            storesharecount = Integer.parseInt(sharecountpreference) + 1;

            reflink.child("app").child("trigger").setValue("yes");
            Query querylink = reflink.child("app");
            querylink.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshotapp) {
                    getlink = dataSnapshotapp.child("nsa").getValue(String.class);


                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "NSA HELP");
                    String shareMessage = getlink;
                    intent.putExtra(Intent.EXTRA_TEXT, shareMessage);

                    startActivityForResult(Intent.createChooser(intent, "Share by:"),REQUEST_CODE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
         else if (id == R.id.nav_aryom) {
            database=FirebaseDatabase.getInstance();
            reflink=database.getReference("link");

            reflink.child("app").child("trigger").setValue("yes");
            Query querylink=reflink.child("app");
            querylink.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshotapp) {
                    String aboutlink=dataSnapshotapp.child("aboutdevlink").getValue(String.class);
                    String url =aboutlink;
                    Uri uriUrl = Uri.parse(url);
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else if (id == R.id.nav_tc) {

            String url = "https://termsandconditionsnsahelp.blogspot.com/2020/10/terms-conditions-body-font-family.html"; //
            Uri uriUrl = Uri.parse(url);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);

        } else if (id == R.id.nav_privacy) {

            String url = "https://privacypolicynsahelp.blogspot.com/2020/10/privacy-policy-for-nsa-help.html"; //
            Uri uriUrl = Uri.parse(url);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);

        }
        else if(id==R.id.nav_reg){

            database = FirebaseDatabase.getInstance();
            reflink = database.getReference("link");

            reflink.child("app").child("trigger").setValue("yes");
            Query querylink = reflink.child("app");
            querylink.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshotapp) {
                    String aboutlink = dataSnapshotapp.child("registeredNGO").getValue(String.class);
                    String url = aboutlink;
                    Uri uriUrl = Uri.parse(url);
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else if(id==R.id.nav_aboutus) {
            String url = "https://www.nsahelp.in/";
            Uri uriUrl = Uri.parse(url);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
        }
        /*else if (id == R.id.nav_dark_mode) {
            //code for setting dark mode
            //true for dark mode, false for day mode, currently toggling on each click
            String getmode=getSharedPreferences("dark_and_light_mode",MODE_PRIVATE)
                    .getString("lightMode","true");
            if(getmode.equals("true")){

                getSharedPreferences("dark_and_light_mode",MODE_PRIVATE).edit()
                        .putString("lightMode","false").apply();

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                recreate();
            }
            else if(getmode.equals("false")){

                getSharedPreferences("dark_and_light_mode",MODE_PRIVATE).edit()
                        .putString("lightMode","true").apply();

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                recreate();
            }


        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == REQUEST_CODE  && resultCode  == RESULT_OK) {

                getSharedPreferences("storesharecount", MODE_PRIVATE).edit()
                        .putString("sharecount", storesharecount + "").apply();
            }
        } catch (Exception ex) {
            Toast.makeText(HELPsection.this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }

    }
}