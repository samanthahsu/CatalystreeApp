package com.example.catalystreeapp.Transportation;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.catalystreeapp.Main.MainActivity;
import com.example.catalystreeapp.R;
import com.example.catalystreeapp.Users.SessionManagement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class FCar extends Fragment implements AdapterView.OnItemSelectedListener {

    public FCar() {
    }

    CarDataBaseAdapter carDataBaseAdapter;
    SessionManagement session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        inflating the view
        final View rootView = inflater.inflate(R.layout.fragment_car, container, false);
//        declaring stuff, not sure why it has to be final
        final Spinner carSpinner, wherSpinner;
        final EditText editTextDistance;
        Button btnSubmitCar;

//      create new car database
        carDataBaseAdapter = new CarDataBaseAdapter(getActivity());
        carDataBaseAdapter = carDataBaseAdapter.open();
        SQLiteDatabase x = carDataBaseAdapter.getDatabaseInstance();

        session = new SessionManagement(getActivity().getApplicationContext());

//        display existing tables
/*
        Cursor hi = x.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        hi.moveToFirst();
        int f = hi.getCount();
        String g = Integer.toString(f);
        Toast.makeText(getActivity().getApplicationContext(), g, Toast.LENGTH_LONG).show();
        while ( !hi.isAfterLast() ) {
            String h = (hi.getString( hi.getColumnIndex("name")) );
            hi.moveToNext();
            Toast.makeText(getActivity().getApplicationContext(), h, Toast.LENGTH_LONG).show();
        }
*/

        // Get References of Views
        carSpinner = (Spinner) rootView.findViewById(R.id.spinner_car_type);
        editTextDistance = (EditText) rootView.findViewById(R.id.editTextcarDistance);

        // Spinner click listener
        carSpinner.setOnItemSelectedListener(this);

//        set default spinner selection
        carSpinner.setSelection(0);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add("Standard");
        categories.add("Truck");
        categories.add("Electric/Hybrid");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        carSpinner.setAdapter(dataAdapter);

//      identifying the button
        btnSubmitCar = (Button) rootView.findViewById(R.id.button_car_submit);
        btnSubmitCar.setOnClickListener(new View.OnClickListener() {

                                            public void onClick(View v) {

//              take username entry from login / sign up form via method in FProfile
//                String currentUser = getSingleEntry();
                                                String username = session.getUsername();

                                                SimpleDateFormat formata = new SimpleDateFormat("MM/dd/yyyy", Locale.CANADA);
                                                String date = formata.format(new Date());

                                                String type = carSpinner.getSelectedItem().toString();

//                convert distance to integer
                                                int distance = 0;
                                                try {
                                                    distance = Integer.parseInt(editTextDistance.getText().toString());
                                                } catch (NumberFormatException nfe) {
                                                    System.out.println("Could not parse distance " + nfe);
                                                }
                                                // Save the Data in Database
                                                carDataBaseAdapter.insertEntry(username, date, type, distance);
//                                              link back to input menu
                                                Intent intent = new Intent(getActivity(), MainActivity.class);

                                                intent.putExtra("caller", "Input");
                                                startActivity(intent);

                                            }
                                        }
        );
        return rootView;
    }
    //          spinner onclick
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // need to add stuff here

    }
}