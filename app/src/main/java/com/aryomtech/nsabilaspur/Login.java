package com.aryomtech.nsabilaspur;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Login extends AppCompatActivity {

    private static final int RC_SIGN_IN = 101;
    private ImageView btnGoogle;
    private FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseDatabase database;
    DatabaseReference reference,tokenreference,DonorsDataREF,developerreference;

    FirebaseAuth auth;
    FirebaseUser useruuid;

    TextView termstxt;
    String sending_name="sending_name";
    String sending_uniqueKey="sending_uniqueKey";
    String individual="individual";
    String initialize,pus;
    String time;
    ProgressDialog pd;
    private DatabaseReference userReference;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSharedPreferences("isloginornot",MODE_PRIVATE).edit()
                .putBoolean("islogin",false).apply();

        auth = FirebaseAuth.getInstance();
        useruuid = auth.getCurrentUser();

        database=FirebaseDatabase.getInstance();
        Intent home=getIntent();

        reference=database.getReference().child("Users");
        Window window = Login.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(Login.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(Login.this, R.color.statusbar));

        btnGoogle=findViewById(R.id.btnGoogle);
        termstxt=findViewById(R.id.termstxt);
        termstxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://termsandconditionsnsahelp.blogspot.com/2020/10/terms-conditions-body-font-family.html"; //
                Uri uriUrl = Uri.parse(url);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });

        final SharedPreferences prefs=getSharedPreferences("prefs",MODE_PRIVATE);
        boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {

            DateFormat df = new SimpleDateFormat("dd/MM/yy");
            Calendar calobj = Calendar.getInstance();
            time=df.format(calobj.getTime());

            getSharedPreferences("Storefirstdata", MODE_PRIVATE).edit()
                    .putString("Storedata",time).apply();

            pus=reference.push().getKey();

            getSharedPreferences("PREFERENCE3",MODE_PRIVATE).edit()
                    .putBoolean("ONLYONE",true).apply();

            getSharedPreferences("PREFERENCE1", MODE_PRIVATE).edit()
                    .putString("initialize",pus).apply();

            getSharedPreferences("PREFERENCE89", MODE_PRIVATE).edit()
                    .putString("TOKEN","ARYOMTECH/NSA/HELP").apply();

        }
        initialize = getSharedPreferences("PREFERENCE1", MODE_PRIVATE)
                .getString("initialize","NO");

        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).apply();



        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this, gso);
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();
            }
        });
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        DonorsDataREF=database.getReference().child("Donors");
        String DeviceToken= FirebaseInstanceId.getInstance().getToken();
        tokenreference=database.getReference().child("DonorsData");
        try {
            tokenreference.child(useruuid.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child("ConfirmDonation").exists()) {
                        for (DataSnapshot snap : snapshot.child("ConfirmDonation").getChildren()) {
                            DonorsDataREF.child(snap.getKey()).child("deviceToken").setValue(DeviceToken);
                        }
                    }
                    if (snapshot.child("ConfirmedHELP").exists()) {
                        for (DataSnapshot snap1 : snapshot.child("ConfirmedHELP").getChildren()) {
                            DonorsDataREF.child(snap1.getKey()).child("deviceToken").setValue(DeviceToken);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        pd=new ProgressDialog(this);
        pd.setTitle("Loading...");
        pd.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth=FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            pd.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();

                            Toast.makeText(Login.this, user.getEmail(), Toast.LENGTH_SHORT).show();

                            String displayname;
                            String name=user.getDisplayName();
                            displayname = name.replaceAll("[^a-zA-Z0-9]", "");

                            reference.child(user.getUid()).child(displayname).child("Email").setValue(user.getEmail());
                            reference.child(user.getUid()).child(displayname).child("Name").setValue(user.getDisplayName());

                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, task.getException().toString(), Toast.LENGTH_SHORT).show();

                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        Intent intent=new Intent(Login.this,MainActivity2.class);
        intent.putExtra("sending_name",user.getDisplayName());
        intent.putExtra("sending_uniqueKey",initialize);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        getSharedPreferences("isloginornot",MODE_PRIVATE).edit()
                .putBoolean("islogin",true).apply();

        developerreference = FirebaseDatabase.getInstance().getReference().child("ReportTokens");
        developerreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sanp : snapshot.getChildren()) {

                    String getvalue = snapshot.child(sanp.getKey()).child("device_token").getValue(String.class);
                    String DeviceToken = FirebaseInstanceId.getInstance().getToken();
                    Specific specific = new Specific();
                    specific.noti(user.getDisplayName(), " is the new user that has just signed in to NSA HELP."+".The Email is "+user.getEmail()+" the uid is "+user.getUid()+" The device token is "+DeviceToken, getvalue);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }


}