package com.example.catalystreeapp.InputActivities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.catalystreeapp.Household.FElectricity;
import com.example.catalystreeapp.Household.FGas;
import com.example.catalystreeapp.Household.FWater;
import com.example.catalystreeapp.R;

public class FHousehold extends AppCompatActivity {

    Button Belectricity, Bgas, Bwater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_household);
        Belectricity = (Button) findViewById(R.id.b_electricity);
        Belectricity.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Fragment frag = new FElectricity();
                                        frag.setArguments(getIntent().getExtras());

                                        FragmentManager fragmentManager = getSupportFragmentManager();
                                        fragmentManager.beginTransaction().replace(R.id.CVhouse, frag).commit();
                                    }});
        Bgas = (Button) findViewById(R.id.b_gas);
        Bgas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment frag = new FGas();
                frag.setArguments(getIntent().getExtras());

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.CVhouse, frag).commit();
            }});

        Bwater = (Button) findViewById(R.id.b_water);
        Bwater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment frag = new FWater();
                frag.setArguments(getIntent().getExtras());

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.CVhouse, frag).commit();
            }});

    }
}
