package com.example.catalystreeapp.MainFragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.catalystreeapp.Household.ElectricityDataBaseAdapter;
import com.example.catalystreeapp.Household.GasDataBaseAdapter;
import com.example.catalystreeapp.Household.WaterDataBaseAdapter;
import com.example.catalystreeapp.R;
import com.example.catalystreeapp.Users.SessionManagement;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

public class FUsageCost extends Fragment {

    BarChart chart;
    ElectricityDataBaseAdapter electricityDataBaseAdapter;
    GasDataBaseAdapter gasDataBaseAdapter;
    WaterDataBaseAdapter waterDataBaseAdapter;
    Context context;
    SessionManagement session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View myView = inflater.inflate(R.layout.fragment_usage_money, container, false);
        chart = (BarChart) myView.findViewById(R.id.barchart_money);

//      open databases
        session = new SessionManagement(context);
        String currentUsername = session.getUsername();

        electricityDataBaseAdapter = new ElectricityDataBaseAdapter(context);
        electricityDataBaseAdapter = electricityDataBaseAdapter.open();

        gasDataBaseAdapter = new GasDataBaseAdapter(context);
        gasDataBaseAdapter = gasDataBaseAdapter.open();

        waterDataBaseAdapter = new WaterDataBaseAdapter(context);
        waterDataBaseAdapter = waterDataBaseAdapter.open();

//      LEGEND CONTENT
        Legend l = chart.getLegend();
        l.setTextColor(Color.WHITE);
        int x = AddValuesE(currentUsername);
        int y = AddValuesG(currentUsername);
        int z = AddValuesW(currentUsername);

        final String[] types = {"Electricity", "Gas", "Water"};
        int[] cost = {x, y, z};

//      ENTRIES
        List<BarEntry> CostVals = new ArrayList<>();

        for (int i = 0; i < cost.length; i++) {
            BarEntry e = new BarEntry(i, (float) cost[i]);
            CostVals.add(i, e);
        }
        BarDataSet distanceSet = new BarDataSet(CostVals, "Cost");
//        set the list to a line data set
        List<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(distanceSet);
//        set the line data set to line data
        BarData data = new BarData(dataSets);
        chart.setData(data);
        chart.invalidate();

//       FORMATTING X AXIS
        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return types[(int) value];
            }
            public int getDecimalDigits() {
                return 0;
            }
        };
        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
        chart.setData(data);
        chart.invalidate();

//      button to money chart
        Button b_pie = (Button) myView.findViewById(R.id.b_energy);
        b_pie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment frag = new FUsageEnergy();
                frag.setArguments(getActivity().getIntent().getExtras());
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
            }
        });
//      button to go home
        Button b_home = (Button) myView.findViewById(R.id.b_home);
        b_home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment frag = new FHome();
                frag.setArguments(getActivity().getIntent().getExtras());
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
            }
        });
//        button for tips
        Button b_tips = (Button) myView.findViewById(R.id.b_tips);
        b_tips.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://catalystree.wordpress.com/"));
                startActivity(intent);
            }
        });
        return myView;
    }
    public int AddValuesE(String currentUsername) {
        int count = 7;
        Cursor Ec = electricityDataBaseAdapter.getElectricityEntry(currentUsername);

        final String[] Edate = new String[count];
        int[] Ecost = new int[count];
        int m = 0;
        Ec.moveToFirst();
        for (m = 0; m < Ec.getCount(); m++) {
            Edate[m] = Ec.getString(0);
            Ecost[m] = Ec.getInt(1);
            Ec.moveToNext();
        }
        Ec.close();
        int Esum = 0;
        for (int aEcost : Ecost) {
            Esum += aEcost;
        }
        return Esum;
    }

    public int AddValuesW(String currentUsername) {
        int count = 7;
        Cursor Wc = waterDataBaseAdapter.getWaterEntry(currentUsername);
        int m;
        final String[] Wdate = new String[count];
        int[] Wcost = new int[count];
        Wc.moveToFirst();
        for (m = 0; m < Wc.getCount(); m++) {
            Wdate[m] = Wc.getString(0);
            Wcost[m] = Wc.getInt(1);
            Wc.moveToNext();
        }
        Wc.close();
        int Wsum = 0;
        for (int aWcost : Wcost) {
            Wsum += aWcost;
        }
        return Wsum;
    }

    public int AddValuesG(String currentUsername) {
        int count = 7;
        int m;
        Cursor Gc = gasDataBaseAdapter.getGasEntry(currentUsername);
        final String[] Gdate = new String[count];
        int[] Gcost = new int[count];
        Gc.moveToFirst();
        for (m = 0; m < Gc.getCount(); m++) {
            Gdate[m] = Gc.getString(0);
            Gcost[m] = Gc.getInt(1);
            Gc.moveToNext();
        }
        Gc.close();
        int Gsum = 0;
        for (int aGcost : Gcost) {
            Gsum += aGcost;
        }
        return Gsum;
    }
}