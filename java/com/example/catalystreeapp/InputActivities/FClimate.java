package com.example.catalystreeapp.InputActivities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.catalystreeapp.ClimateControl.FConditioning;
import com.example.catalystreeapp.ClimateControl.FHeating;
import com.example.catalystreeapp.R;

public class FClimate extends AppCompatActivity {

    Button Bheating, Bconditioning;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_climate);
        Bheating = (Button) findViewById(R.id.b_heating);
        Bheating.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Fragment frag = new FHeating();
                                        frag.setArguments(getIntent().getExtras());

                                        FragmentManager fragmentManager = getSupportFragmentManager();
                                        fragmentManager.beginTransaction().replace(R.id.CVclimate, frag).commit();
                                    }});
        Bconditioning = (Button) findViewById(R.id.b_conditioning);
        Bconditioning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment frag = new FConditioning();
                frag.setArguments(getIntent().getExtras());

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.CVclimate, frag).commit();
            }});
    }
}
