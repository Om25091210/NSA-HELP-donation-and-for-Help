package com.aryomtech.nsabilaspur;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;


public class HolderRequestHelp extends RecyclerView.ViewHolder {

    View view;
    Button cancel;
    ImageView greentick;
    ImageView taskdonetick;

    Button desopen;
    Dialog dialogAnimated;
    TextView editText;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    FirebaseDatabase databaseD, database;
    DatabaseReference refD, referenceforplay, reflevelbadges, badgedata, reflimitbadges;
    String userdonorid;
    ImageView phonecall;
    String Confirmedby;
    String store_event_donation_number;

    int checkcurrentlevel = 0;

    FirebaseAuth auth;
    FirebaseUser useruuid;
    ImageView imageoffeed;
    long Donorscount = 0;

    public HolderRequestHelp(@NonNull View itemView) {
        super(itemView);

        view = itemView;
    }

    public void setView(final Context context, String category, String naam, String address,
                        String phoneno, final String gettingKey, String uniqueId, String pic1url, String pic2url
            , String pic3url, String pic4url,String description,String Done,String time) {
        TextView locate = view.findViewById(R.id.location);
        TextView cate = view.findViewById(R.id.category);
        TextView Naam = view.findViewById(R.id.naam);
        TextView Num = view.findViewById(R.id.number);
        TextView date=view.findViewById(R.id.date);
        date.setText(time);
        taskdonetick=view.findViewById(R.id.taskdonetick);
        taskdonetick.setVisibility(View.GONE);

        cancel = view.findViewById(R.id.btncan);
        imageoffeed = view.findViewById(R.id.imageoffeed);
        locate.setText(address);
        cate.setText(category);
        Naam.setText(naam);
        Num.setText(phoneno);
        userdonorid = uniqueId;

        auth = FirebaseAuth.getInstance();
        useruuid = auth.getCurrentUser();


        desopen=view.findViewById(R.id.desopen);
        desopen.setVisibility(View.GONE);


        if(Done.equals("yes")){
            taskdonetick.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.GONE);
            try {
                desopen.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(Done.equals("no")){
            taskdonetick.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);
            try{
                if(description.equals("")){
                    desopen.setVisibility(View.GONE);
                }
                else{
                    desopen.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                desopen.setVisibility(View.GONE);
            }
        }

        desopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAnimated=new Dialog(context,R.style.dialogstyle);
                dialogAnimated.setContentView(R.layout.dialogbox);
                dialogAnimated.show();
                TextView txtclose=(TextView)dialogAnimated.findViewById(R.id.txtclose);
                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogAnimated.dismiss();
                    }
                });
                editText=dialogAnimated.findViewById(R.id.editTextTextMultiLine2);
                editText.setText(description);
            }
        });

        if (category.equals("Food")) {
            imageoffeed.setImageResource(R.drawable.food);
        } else if (category.equals("Clothes")) {
            imageoffeed.setImageResource(R.drawable.cloth);
        } else if (category.equals("Old Newspapers")) {
            imageoffeed.setImageResource(R.drawable.blanket);
        } else if (category.equals("Blood")) {
            imageoffeed.setImageResource(R.drawable.blood);
        } else if (category.equals("Educational")) {
            imageoffeed.setImageResource(R.drawable.books);
        }
        else if(category.equals("Sack(बोरा)")){
            imageoffeed.setImageResource(R.drawable.animals);
        }
        else if(category.equals("Animal Rescue")){
            imageoffeed.setImageResource(R.drawable.ic_animalrescue);
        }
        else {
            imageoffeed.setImageResource(R.drawable.other);
        }
        databaseD = FirebaseDatabase.getInstance();
        refD = databaseD.getReference().child("DonorsData");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refD.child(gettingKey).child("ConfirmedHELP").child(userdonorid).removeValue();


                refD=databaseD.getReference().child("Donors");

                storage=FirebaseStorage.getInstance();
                try {
                    storageReference = storage.getReferenceFromUrl(pic1url);
                    storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // File deleted successfully

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Uh-oh, an error occurred!
                            Toast.makeText(context, "onFailure: did not delete file", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();

                }
                try {
                    storageReference = storage.getReferenceFromUrl(pic2url);
                    storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // File deleted successfully

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Uh-oh, an error occurred!
                            Toast.makeText(context, "onFailure: did not delete file", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    storageReference = storage.getReferenceFromUrl(pic3url);
                    storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // File deleted successfully

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Uh-oh, an error occurred!
                            Toast.makeText(context, "onFailure: did not delete file", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    storageReference = storage.getReferenceFromUrl(pic4url);
                    storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // File deleted successfully

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Uh-oh, an error occurred!
                            Toast.makeText(context, "onFailure: did not delete file", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }


                refD.child(userdonorid).removeValue();

            }
        });

    }
}
