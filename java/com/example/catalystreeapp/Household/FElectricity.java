package com.example.catalystreeapp.Household;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.catalystreeapp.Main.MainActivity;
import com.example.catalystreeapp.MainFragments.FInput;
import com.example.catalystreeapp.R;
import com.example.catalystreeapp.Transportation.TransitDataBaseAdapter;
import com.example.catalystreeapp.Users.SessionManagement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class FElectricity extends Fragment{

    public FElectricity() {
    }

    ElectricityDataBaseAdapter electricityDataBaseAdapter;
    SessionManagement session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        inflating the view
        final View rootView = inflater.inflate(R.layout.fragment_electricity, container, false);
//        declaring stuff, not sure why it has to be final
        final EditText ETcost;
        Button btnSubmitElec;

        ETcost = (EditText) rootView.findViewById(R.id.ET_electricity_cost);

//      create new car database
        electricityDataBaseAdapter = new ElectricityDataBaseAdapter(getActivity());
        electricityDataBaseAdapter = electricityDataBaseAdapter.open();

        session = new SessionManagement(getActivity().getApplicationContext());

//      identifying the button
        btnSubmitElec = (Button) rootView.findViewById(R.id.button_electricity_submit);
        btnSubmitElec.setOnClickListener(new View.OnClickListener() {

                                                public void onClick(View v) {

//              take username entry from login / sign up form via method in FProfile
//                String currentUser = getSingleEntry();
                                                    String username = session.getUsername();
//                convert distance to integer
                                                    SimpleDateFormat formata = new SimpleDateFormat("MM/dd/yyyy", Locale.CANADA);
                                                    String date = formata.format(new Date());

                                                    int cost = 0;
                                                    try {
                                                        cost = Integer.parseInt(ETcost.getText().toString());
                                                    } catch (NumberFormatException nfe) {
                                                        System.out.println("Could not parse distance " + nfe);
                                                    }
                                                    // Save the Data in Database
                                                    electricityDataBaseAdapter.insertEntry(username, date, cost);
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