package com.aryomtech.nsabilaspur;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class searchActivity extends AppCompatActivity {


    DatabaseReference ref;
    ArrayList<Deal> list;
    RecyclerView recyclerView;
    SearchView searchView;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ref= FirebaseDatabase.getInstance().getReference().child("Vendors");
        recyclerView=findViewById(R.id.rv);
        searchView=findViewById(R.id.searchView);

        list=new ArrayList<>();

        Window window = searchActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(searchActivity.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(searchActivity.this, R.color.statusbar));


    }
    @Override
    protected void onStart() {
        super.onStart();

        if(ref!=null){

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.exists()){
                        for(DataSnapshot ds:snapshot.getChildren()){
                            list.add(ds.getValue(Deal.class));
                        }
                        AdapterClass adapterClass=new AdapterClass(searchActivity.this,list);
                        recyclerView.setAdapter(adapterClass);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(searchActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(searchView!=null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }
    }

    private void search(String str) {

        ArrayList<Deal> mylist=new ArrayList<>();
        for(Deal object:list){

            if(object.getAddressshop().toLowerCase().contains(str.toLowerCase().trim()))
            {
                mylist.add(object);
            }
            else if(object.getShopcategory().toLowerCase().contains(str.toLowerCase().trim())){
                mylist.add(object);
            }
            else if(object.getDesciptionshop().toLowerCase().contains(str.toLowerCase().trim())){
                mylist.add(object);
            }
        }
        AdapterClass adapterClass=new AdapterClass(searchActivity.this,mylist);
        recyclerView.setAdapter(adapterClass);
    }

    public void bck(View view) {
        finish();
    }
}