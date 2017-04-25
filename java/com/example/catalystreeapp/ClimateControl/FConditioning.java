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
import com.example.catalystreeapp.Users.SessionManagement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class FConditioning extends Fragment {

    public FConditioning() {
    }

    ConditioningDataBaseAdapter conditioningDataBaseAdapter;
    SessionManagement session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        inflating the view
        final View rootView = inflater.inflate(R.layout.fragment_conditioning, container, false);
//        declaring stuff, not sure why it has to be final
        final EditText editTexttemp, editTextMin;
        Button btnSubmitConditioning;

        conditioningDataBaseAdapter = new ConditioningDataBaseAdapter(getActivity());
        conditioningDataBaseAdapter = conditioningDataBaseAdapter.open();

        session = new SessionManagement(getActivity().getApplicationContext());

        // Get References of Views
        editTextMin = (EditText) rootView.findViewById(R.id.ET_conditioning_min);

//      identifying the button
        btnSubmitConditioning = (Button) rootView.findViewById(R.id.button_conditioning_submit);
        btnSubmitConditioning.setOnClickListener(new View.OnClickListener() {

                                                     public void onClick(View v) {

//              take username entry from login / sign up form via method in FProfile
//                String currentUser = getSingleEntry();
                                                         String username = session.getUsername();

                                                         SimpleDateFormat formata = new SimpleDateFormat("MM/dd/yyyy", Locale.CANADA);
                                                         String date = formata.format(new Date());

//                convert minutes to integer
                                                         int time = 0;
                                                         try {
                                                             time = Integer.parseInt(editTextMin.getText().toString());
                                                         } catch (NumberFormatException nfe) {
                                                             System.out.println("Could not parse minutes" + nfe);
                                                         }

                                                         // Save the Data in Database
                                                         conditioningDataBaseAdapter.insertEntry(username, date, time);
                                                         Toast.makeText(getActivity().getApplicationContext(), "Data Saved", Toast.LENGTH_LONG).show();
//                                              link back to input menu
                                                         Intent intent = new Intent(getActivity(), MainActivity.class);
                                                         intent.putExtra("caller", "Input");
                                                         startActivity(intent);
                                                     }
                                                 }
        );
        return rootView;
    }
}