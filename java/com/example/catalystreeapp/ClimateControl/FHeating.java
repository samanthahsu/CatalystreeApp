package com.example.catalystreeapp.ClimateControl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.catalystreeapp.Main.MainActivity;
import com.example.catalystreeapp.R;
import com.example.catalystreeapp.Transportation.CarDataBaseAdapter;
import com.example.catalystreeapp.Users.SessionManagement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class FHeating extends Fragment implements AdapterView.OnItemSelectedListener {

    public FHeating() {
    }

    HeatingDataBaseAdapter heatingDataBaseAdapter;
    SessionManagement session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_heating, container, false);
        final Spinner heatingSpinner;
        final EditText editTextMin;
        Button btnSubmitCar;

//      create new car database
        heatingDataBaseAdapter = new HeatingDataBaseAdapter(getActivity());
        heatingDataBaseAdapter = heatingDataBaseAdapter.open();

        session = new SessionManagement(getActivity().getApplicationContext());

        // Get References of Views
        heatingSpinner = (Spinner) rootView.findViewById(R.id.spinner_heating_type);
        editTextMin = (EditText) rootView.findViewById(R.id.ET_heating_min);

        // Spinner
        heatingSpinner.setOnItemSelectedListener(this);

        heatingSpinner.setSelection(0);

        List<String> categories = new ArrayList<>();
        categories.add("Central");
        categories.add("Baseboard");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        heatingSpinner.setAdapter(dataAdapter);

        btnSubmitCar = (Button) rootView.findViewById(R.id.button_heating_submit);
        btnSubmitCar.setOnClickListener(new View.OnClickListener() {

                                            public void onClick(View v) {

                                                String username = session.getUsername();

                                                SimpleDateFormat formata = new SimpleDateFormat("MM/dd/yyyy", Locale.CANADA);
                                                String date = formata.format(new Date());

                                                String type = heatingSpinner.getSelectedItem().toString();

                                                int time = 0;
                                                try {
                                                    time = Integer.parseInt(editTextMin.getText().toString());
                                                } catch (NumberFormatException nfe) {
                                                    System.out.println("Could not parse minutes" + nfe);
                                                }

                                                // Save the Data in Database
                                                heatingDataBaseAdapter.insertEntry(username, date, type, time);
                                                Toast.makeText(getActivity().getApplicationContext(), "Data Saved", Toast.LENGTH_LONG).show();
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