package com.aryomtech.nsabilaspur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LeaderBoard extends AppCompatActivity {

    DatabaseReference ref;
    FirebaseDatabase database;
    RecyclerView recyclerView;
    SearchView searchView;
    ArrayList<Set<String>> list;
    ArrayList<Collection<Long>> listScore;
    static ArrayList<String> mylist;
    static ArrayList<String> mylistScore;

    boolean once=false;
    ArrayList<Leader> listleader;
    String quizname="";
    Map<String, Long> unsortMap = new HashMap<String, Long>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        ref= FirebaseDatabase.getInstance().getReference().child("SecondaryQuiz");
        recyclerView=findViewById(R.id.leader_rv);
        searchView=findViewById(R.id.searchView);

        quizname=getIntent().getStringExtra("sendingWhichquizleaderboard");

        listleader=new ArrayList<>();

        list=new ArrayList<java.util.Set<String>>();
        listScore=new ArrayList<java.util.Collection<Long>>();
        mylist=new ArrayList<>();
        mylistScore=new ArrayList<>();


        Window window = LeaderBoard.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(LeaderBoard.this, R.color.statusbar));
        window.setNavigationBarColor(ContextCompat.getColor(LeaderBoard.this, R.color.statusbar));

    }
    @Override
    protected void onStart() {
        super.onStart();

        if(ref!=null){

            ref.child(quizname).child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.exists() && !once) {
                        for (DataSnapshot ds : snapshot.getChildren()) {

                            String email = snapshot.child(ds.getKey()).child("displayname").getValue(String.class);

                            if (snapshot.child(ds.getKey()).child("correct").exists()) {
                                Long correct = snapshot.child(ds.getKey()).child("correct").getChildrenCount();
                                unsortMap.put(email, (long) correct);
                            }
                            else{
                                unsortMap.put(email, (long) 0);
                            }


                        }
                        Map<String, Long> sortedMap = sortByValue(unsortMap);
                        printMap(sortedMap);
                        for (int i = 0; i < mylist.size(); i++) {

                            LeaderAdapter LeaderAdapter = new LeaderAdapter(LeaderBoard.this, mylist, mylistScore,1);
                            recyclerView.setAdapter(LeaderAdapter);
                        }
                        once=true;

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(LeaderBoard.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(searchView!=null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    search(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    searchempty(newText);
                    return false;
                }
            });
        }
    }

    private void searchempty(String newText) {

        if(newText.equals("")){
            for (int i = 0; i < mylist.size(); i++) {

                LeaderAdapter LeaderAdapter = new LeaderAdapter(LeaderBoard.this, mylist, mylistScore,1);
                recyclerView.setAdapter(LeaderAdapter);
            }
        }

    }

    private void search(String str) {

        if(mylist.contains(str.toLowerCase().trim())){

            int index=mylist.indexOf(str.toLowerCase().trim());
            LeaderAdapter LeaderAdapter = new LeaderAdapter(LeaderBoard.this, mylist.get(index), mylistScore.get(index), 0);
            recyclerView.setAdapter(LeaderAdapter);
        }
        else{
            Snackbar.make(findViewById(android.R.id.content),"No Match Found.",Snackbar.LENGTH_LONG).show();
        }

    }

    private static Map<String, Long> sortByValue(Map<String, Long> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<String, Long>> list =
                new LinkedList<Map.Entry<String, Long>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<String, Long>>() {
            public int compare(Map.Entry<String, Long> o1,
                               Map.Entry<String, Long> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<String, Long> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Long> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }


        return sortedMap;
    }

    public static <K, V> void printMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            mylist.add(String.valueOf(entry.getKey()));

            mylistScore.add(String.valueOf(entry.getValue()));
        }
        Collections.reverse(mylistScore);
        Collections.reverse(mylist);
    }

}