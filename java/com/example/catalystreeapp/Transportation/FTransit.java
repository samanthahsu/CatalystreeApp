package com.example.catalystreeapp.Transportation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.catalystreeapp.Main.MainActivity;
import com.example.catalystreeapp.R;
import com.example.catalystreeapp.Users.SessionManagement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class FTransit extends Fragment{

    public FTransit() {
    }

    TransitDataBaseAdapter transitDataBaseAdapter;
    SessionManagement session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        inflating the view
        final View rootView = inflater.inflate(R.layout.fragment_transit, container, false);
//        declaring stuff, not sure why it has to be final
        final EditText editTextDistance, editTextMin;
        Button btnSubmitTransit;

        editTextDistance = (EditText) rootView.findViewById(R.id.editTextTransitDistance);
        editTextMin = (EditText) rootView.findViewById(R.id.editTextTransitMin);

//      create new car database
        transitDataBaseAdapter = new TransitDataBaseAdapter(getActivity());
        transitDataBaseAdapter = transitDataBaseAdapter.open();

        session = new SessionManagement(getActivity().getApplicationContext());

//      identifying the button
        btnSubmitTransit = (Button) rootView.findViewById(R.id.button_transit_submit);
        btnSubmitTransit.setOnClickListener(new View.OnClickListener() {

                                                public void onClick(View v) {

//              take username entry from login / sign up form via method in FProfile
//                String currentUser = getSingleEntry();
                                                    String username = session.getUsername();
//                convert distance to integer
                                                    SimpleDateFormat formata = new SimpleDateFormat("MM/dd/yyyy", Locale.CANADA);
                                                    String date = formata.format(new Date());

                                                    int distance = 0;
                                                    try {
                                                        distance = Integer.parseInt(editTextDistance.getText().toString());
                                                    } catch (NumberFormatException nfe) {
                                                        System.out.println("Could not parse distance " + nfe);
                                                    }
                                                    int time = 0;
                                                    try {
                                                        time = Integer.parseInt(editTextMin.getText().toString());
                                                    } catch (NumberFormatException nfe) {
                                                        System.out.println("Could not parse minutes" + nfe);
                                                    }

                                                    // Save the Data in Database
                                                    transitDataBaseAdapter.insertEntry(username, date, distance, time);
                                                    Toast.makeText(getActivity().getApplicationContext(), "Data Saved", Toast.LENGTH_LONG).show();
//                                              link back to input menu
                                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                                    intent.putExtra("caller", "Input");
                                                    startActivity(intent);
                                                }
                                            }
        );
        return rootView;
    }}