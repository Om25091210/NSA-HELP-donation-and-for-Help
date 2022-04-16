package com.aryomtech.nsabilaspur;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class LeaderAdapter extends RecyclerView.Adapter<LeaderAdapter.ViewHolder> {

    ArrayList<String> mylist;
    ArrayList<String> mylistScore;
    Context context;
    int check;

    String mystring;
    String myStringscore;

    public LeaderAdapter(LeaderBoard context, ArrayList<String> mylist, ArrayList<String> mylistScore,int check) {
        this.mylist=mylist;
        this.mylistScore= mylistScore;
        this.context=context;
        this.check=check;
    }

    public LeaderAdapter(LeaderBoard context, String s, String s1,int check) {
        this.mystring=s;
        this.myStringscore= s1;
        this.context=context;
        this.check=check;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_of_leaderboard,viewGroup,false);
        return new LeaderAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if(check==1) {
            viewHolder.email.setText(mylist.get(i) + "");
            viewHolder.score.setText("SCORE: "+mylistScore.get(i) + "");

            if (Integer.parseInt(mylistScore.get(i)) == Integer.parseInt(Collections.max(mylistScore))) {
                viewHolder.view19.setVisibility(View.VISIBLE);
            } else {
                viewHolder.view19.setVisibility(View.GONE);
            }
        }
        else{
            viewHolder.email.setText(mystring + "");
            viewHolder.score.setText("SCORE: "+myStringscore + "");
            viewHolder.view19.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if(check==1) {
            return mylist.size();
        }
        else{
            return 1;
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView email, score;
        View view19;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            email=itemView.findViewById(R.id.textView9);
            score=itemView.findViewById(R.id.textView21);
            view19=itemView.findViewById(R.id.view19);
        }
    }

}
