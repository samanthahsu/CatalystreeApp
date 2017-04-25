package com.example.catalystreeapp.MainFragments;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.catalystreeapp.R;

public class FHome extends Fragment {

    AnimationDrawable neutral_idle, grump_idle, mad_idle, happy_idle;
    int[] Energy;
    public FHome() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ImageView pandaImage = (ImageView) rootView.findViewById(R.id.pandaView);

//        SOUND WHEN PANDA TAPPED
        final MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.chirp);
        pandaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
            }
        });

        int j = FUsageEnergy.energySum;
        int Today = FUsageEnergy.TODAYENERGY;
        Double Previous = (double) (j / 7);
        Double percentchange = (Today / Previous) * 100;

        if(percentchange < 75) {
            pandaImage.setBackgroundResource(R.drawable.happy_idle);
            happy_idle = (AnimationDrawable) pandaImage.getBackground();
            happy_idle.start();
        } else if(percentchange < 110 && percentchange >= 75) {
            pandaImage.setBackgroundResource(R.drawable.neutral_idle);
            neutral_idle = (AnimationDrawable) pandaImage.getBackground();
            neutral_idle.start();
        } else if (percentchange < 150 && percentchange >= 110) {
            pandaImage.setBackgroundResource(R.drawable.grump_idle);
            grump_idle = (AnimationDrawable) pandaImage.getBackground();
            grump_idle.start();
        } else {
            pandaImage.setBackgroundResource(R.drawable.mad_idle);
            mad_idle = (AnimationDrawable) pandaImage.getBackground();
            mad_idle.start();
        }
        return rootView;
    }
}