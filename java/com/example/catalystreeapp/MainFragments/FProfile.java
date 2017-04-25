package com.example.catalystreeapp.MainFragments;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.catalystreeapp.R;
import com.example.catalystreeapp.Users.SessionManagement;
import com.example.catalystreeapp.Users.UserDbAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FProfile extends Fragment {

    public FProfile() {
    }
    SessionManagement session;
    AnimationDrawable pandarun;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

//        SET USER INFO TO TEXT VIEWS
        session = new SessionManagement(getActivity().getApplicationContext());
        TextView txtname = (TextView) rootView.findViewById(R.id.tvUsername);
        String currentUsername = session.getUsername();
        txtname.setText("Username: " + currentUsername);
        TextView txtemail = (TextView) rootView.findViewById(R.id.tvEmail);
        String currentEmail = session.getEmail();
        txtemail.setText("Email: " + currentEmail);

//        SET PANDA RUN ANIMATION
        ImageView pandaImage = (ImageView) rootView.findViewById(R.id.panda_run);
        pandaImage.setBackgroundResource(R.drawable.pandarun);
        pandarun = (AnimationDrawable) pandaImage.getBackground();
        pandarun.start();

        return rootView;
    }
}
//boolean check for empty name
//                if (txtname == null)
//        {
//            Toast.makeText(getActivity().getApplicationContext(), "NO TEXT VIEW DEFINED!!!", Toast.LENGTH_LONG).show();
//        }
//        if (currentUsername == null)
//        {
//            currentUsername = "NULL NAME";
//            Toast.makeText(getActivity().getApplicationContext(), "NULL NAME", Toast.LENGTH_LONG).show();
//        }
//        else if (currentUsername.isEmpty()) {
//            currentUsername = "EMPTY NAME";
//            Toast.makeText(getActivity().getApplicationContext(), "EMPTY NAME", Toast.LENGTH_LONG).show();
