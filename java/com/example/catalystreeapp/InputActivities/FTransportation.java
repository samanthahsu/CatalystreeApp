package com.example.catalystreeapp.InputActivities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.catalystreeapp.Transportation.FCar;
import com.example.catalystreeapp.Transportation.FTransit;
import com.example.catalystreeapp.Transportation.FWalk;
import com.example.catalystreeapp.R;

public class FTransportation extends AppCompatActivity {

    Button Bcar, Btransit, Bwalk;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation);
        Bcar = (Button) findViewById(R.id.b_car);
        Bcar.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Fragment frag = new FCar();
                                        frag.setArguments(getIntent().getExtras());

                                        FragmentManager fragmentManager = getSupportFragmentManager();
                                        fragmentManager.beginTransaction().replace(R.id.CVtrans, frag).commit();
                                    }});
        Btransit = (Button) findViewById(R.id.b_transit);
        Btransit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment frag = new FTransit();
                frag.setArguments(getIntent().getExtras());

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.CVtrans, frag).commit();
            }});

        Bwalk = (Button) findViewById(R.id.b_walk);
        Bwalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment frag = new FWalk();
                frag.setArguments(getIntent().getExtras());

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.CVtrans, frag).commit();
            }});

    }
}
