package com.example.catalystreeapp.MainFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.catalystreeapp.InputActivities.FClimate;
import com.example.catalystreeapp.InputActivities.FHousehold;
import com.example.catalystreeapp.InputActivities.FTransportation;
import com.example.catalystreeapp.R;

public class FInput extends Fragment {

    Button transportationButton, climateButton, householdButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_input, container, false);

        transportationButton = (Button) myView.findViewById(R.id.transportation_button);
        climateButton = (Button) myView.findViewById(R.id.climateControl_button);
        householdButton = (Button) myView.findViewById(R.id.household_button);


        transportationButton.setOnClickListener(new View.OnClickListener() {
            @Override
//            todo this is an inner class
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FTransportation.class);
                startActivity(intent);
            }
        });

        climateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FClimate.class);
                startActivity(intent);
            }
        });

        householdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FHousehold.class);
                startActivity(intent);
            }
        });
        return myView;
    }
}