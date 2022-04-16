package com.aryomtech.nsabilaspur;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;


public class showingFragment extends Fragment {

    RecyclerView showingimages;

    String url1;
    String url2;
    String url3;
    String url4;
    String totalimages;
    ArrayList<String> urlarray;


    public showingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_showing, container, false);
        urlarray= new ArrayList<String>();

        Bundle b3=getArguments();
        url1=b3.getString("sendingurl1");
        url1=b3.getString("sendingurl1");
        url2=b3.getString("sendingurl2");
        url3=b3.getString("sendingurl3");
        url4=b3.getString("sendingurl4");
        totalimages=b3.getString("sendingTotalImages");

        showingimages= view.findViewById(R.id.recyclerviewShowingImages);
        showingimages.setHasFixedSize(true);
        showingimages.setLayoutManager( new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        int len=Integer.parseInt(totalimages);
        if(len==1){
            urlarray.add(url1);
        }
        if(len==2){
            urlarray.add(url1);
            urlarray.add(url2);
        }
        else if(len==3){
            urlarray.add(url1);
            urlarray.add(url2);
            urlarray.add(url3);
        }
        else if(len==4){
            urlarray.add(url1);
            urlarray.add(url2);
            urlarray.add(url3);
            urlarray.add(url4);
        }

        ShowingAdapter ShowingAdapter = new ShowingAdapter(urlarray,getContext());
        showingimages.setAdapter(ShowingAdapter);
        return view;
    }

}