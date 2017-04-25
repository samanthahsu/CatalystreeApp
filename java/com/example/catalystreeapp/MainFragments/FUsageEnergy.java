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
import android.widget.Toast;

import com.example.catalystreeapp.ClimateControl.ConditioningDataBaseAdapter;
import com.example.catalystreeapp.ClimateControl.HeatingDataBaseAdapter;
import com.example.catalystreeapp.R;
import com.example.catalystreeapp.Transportation.CarDataBaseAdapter;
import com.example.catalystreeapp.Transportation.TransitDataBaseAdapter;
import com.example.catalystreeapp.Transportation.WalkDataBaseAdapter;
import com.example.catalystreeapp.Users.SessionManagement;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FUsageEnergy extends Fragment {

    public static int energySum, TODAYENERGY;
    LineChart chart;
    CarDataBaseAdapter carDataBaseAdapter;
    TransitDataBaseAdapter transitDataBaseAdapter;
    WalkDataBaseAdapter walkDataBaseAdapter;
    HeatingDataBaseAdapter heatingDataBaseAdapter;
    ConditioningDataBaseAdapter conditioningDataBaseAdapter;

    String Standard = "Standard", Truck = "Truck", Electric = "Electric/Hybrid";
    int size = 50;
    int m;
    int carStandardTotal, carTruckTotal, carElectricTotal, transitTotal, ACTotal, heatingTotal;
    int[] carStandard, carTruck, carElectric, transit, Heating, AC;
    Context context;
    SessionManagement session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View myView = inflater.inflate(R.layout.fragment_usage_energy, container, false);
//        set view for chart
        chart = (LineChart) myView.findViewById(R.id.linechart_energy);
        session = new SessionManagement(getActivity().getApplicationContext());
        String currentUsername = session.getUsername();

        /*open the databases**/
        carDataBaseAdapter = new CarDataBaseAdapter(context);
        carDataBaseAdapter = carDataBaseAdapter.open();
        transitDataBaseAdapter = new TransitDataBaseAdapter(context);
        transitDataBaseAdapter = transitDataBaseAdapter.open();
        walkDataBaseAdapter = new WalkDataBaseAdapter(context);
        walkDataBaseAdapter = walkDataBaseAdapter.open();
        heatingDataBaseAdapter = new HeatingDataBaseAdapter(context);
        heatingDataBaseAdapter = heatingDataBaseAdapter.open();
        conditioningDataBaseAdapter = new ConditioningDataBaseAdapter(context);
        conditioningDataBaseAdapter = conditioningDataBaseAdapter.open();


        /*list of cursor arrays and integer sum arrays**/
        Cursor[] carStandardCursors = new Cursor[7];
        carStandard = new int[size];
        Cursor[] carTruckCursors = new Cursor[7];
        carTruck = new int[size];
        Cursor[] carElectricCursors = new Cursor[7];
        carElectric = new int[size];
        Cursor[] transitCursors = new Cursor[7];
        transit = new int[size];
        Cursor[] ACCursors = new Cursor[7];
        AC = new int[size];
        Cursor[] HeatingCursors = new Cursor[7];
        Heating = new int[size];


        /*getting get data from data bases and populate cursor arrays**/
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < 7; i++) {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.CANADA);
            String searchDate = format.format(date);
            cal.add(Calendar.DATE, -1);
            date = cal.getTime();
            carStandardCursors[i] = carDataBaseAdapter.getCarEntry(currentUsername, searchDate, Standard);
            carTruckCursors[i] = carDataBaseAdapter.getCarEntry(currentUsername, searchDate, Truck);
            carElectricCursors[i] = carDataBaseAdapter.getCarEntry(currentUsername, searchDate, Electric);
            transitCursors[i] = transitDataBaseAdapter.getTransitEntry(currentUsername, searchDate);
            ACCursors[i] = conditioningDataBaseAdapter.getConditioningEntry(currentUsername, searchDate);
            HeatingCursors[i] = heatingDataBaseAdapter.getHeatingEntry(currentUsername, searchDate);
        }

//passing each cursor from each array through math method
        for (int i = 0; i < 7; i++) {
            Cursor ccursor = carStandardCursors[i];
            carStandardTotal = CalculateCarStandard(ccursor);
            carStandard[i] = carStandardTotal;
        }
        for (int i = 0; i < 7; i++) {
            Cursor ccursor = carTruckCursors[i];
            carTruckTotal = CalculateCarTruck(ccursor);
            carTruck[i] = carTruckTotal;
        }
        for (int i = 0; i < 7; i++) {
            Cursor ccursor = carElectricCursors[i];
            carElectricTotal = CalculateCarElectric(ccursor);
            carElectric[i] = carElectricTotal;
        }
        for (int i = 0; i < 7; i++) {
            Cursor ccursor = transitCursors[i];
            transitTotal = CalculateTransit(ccursor);
            transit[i] = transitTotal;
        }
        for (int i = 0; i < 7; i++) {
            Cursor ccursor = ACCursors[i];
            ACTotal = CalculateAC(ccursor);
            AC[i] = ACTotal;
        }
        for (int i = 0; i < 7; i++) {
            Cursor ccursor = HeatingCursors[i];
            heatingTotal = CalculateHeating(ccursor);
            Heating[i] = heatingTotal;
        }

//        todo close cursors
        for (int i = 0; i < 7; i++) {
            Cursor ccursor = carStandardCursors[i];
            ccursor.close();
        }
        for (int i = 0; i < 7; i++) {
            Cursor ccursor = carTruckCursors[i];
            ccursor.close();
        }
        for (int i = 0; i < 7; i++) {
            Cursor ccursor = carElectricCursors[i];
            ccursor.close();
        }
        for (int i = 0; i < 7; i++) {
            Cursor ccursor = transitCursors[i];
            ccursor.close();
        }
        for (int i = 0; i < 7; i++) {
            Cursor ccursor = ACCursors[i];
            ccursor.close();
        }
        for (int i = 0; i < 7; i++) {
            Cursor ccursor = HeatingCursors[i];
            ccursor.close();
        }

//LINECHART STUFF
        chart.setNoDataText("No Data yet");

// SETTING DATA
        int[] Energy;
        Energy = CalculateFinalEnergies();

//        create lists for entries
        List<Entry> FinalVals = new ArrayList<>();

        for (int i = 0; i < Energy.length; i++) {
            Entry e = new Entry(i, (float) Energy[i]);
            FinalVals.add(i, e);
//            String k = e.toString();
//            Toast.makeText(context.getApplicationContext(), k, Toast.LENGTH_SHORT).show();
        }
        LineDataSet distanceSet = new LineDataSet(FinalVals, "Energy Used (kWh)");
//        set the list to a line data set
        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(distanceSet);
//        set the line data set to line data
        LineData data = new LineData(dataSets);
        chart.setData(data);
        chart.invalidate();

        final String[] Dates = new String[7];
        for(int i = 0; i < 7; i++){
            SimpleDateFormat formatter = new SimpleDateFormat("E", Locale.CANADA);
            cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -i);
            Date todate1 = cal.getTime();
            String FinalDate = formatter.format(todate1);
            Dates[i] = FinalDate;
        }

// X AXIS FORMATTING
        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return Dates[(int) value];
            }

            // we don't draw numbers, so no decimal digits needed
            public int getDecimalDigits() {
                return 0;
            }
        };
        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
        chart.setData(data);
        chart.invalidate();

//        Calculate value for panda
        energySum = 0;
        for (int add : Energy) {
            energySum += add;
        }

//buttons
        Button b_pie = (Button) myView.findViewById(R.id.b_money);
        b_pie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment frag = new FUsageCost();
                frag.setArguments(getActivity().getIntent().getExtras());
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
            }
        });
        Button b_home = (Button) myView.findViewById(R.id.b_home);
        b_home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment frag = new FHome();
                frag.setArguments(getActivity().getIntent().getExtras());
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();
            }
        });
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

    public int CalculateCarStandard(Cursor cursor) {
        int[] distance = new int[size];
        cursor.moveToFirst();
        for (m = 0; m < cursor.getCount(); m++) {
            distance[m] = cursor.getInt(cursor.getColumnIndex("DISTANCE"));
            cursor.moveToNext();
        }
        int distancesum = 0;
        for (int t : distance) {
            distancesum += t;
        }
        return (distancesum / 22) * 37;
    }

    public int CalculateCarTruck(Cursor cursor) {
        int[] distanceS2 = new int[size];
        cursor.moveToFirst();
        for (m = 0; m < cursor.getCount(); m++) {
            distanceS2[m] = cursor.getInt(0);
            cursor.moveToNext();
        }
        cursor.close();
        int distancesum = 0;
        for (int add : distanceS2) {
            distancesum += add;
        }
        return distancesum * 41 / 28;
    }
    public int CalculateCarElectric(Cursor cursor) {
        int[] distanceS2 = new int[size];
        cursor.moveToFirst();
        for (m = 0; m < cursor.getCount(); m++) {
            distanceS2[m] = cursor.getInt(0);
            cursor.moveToNext();
        }
        cursor.close();
        int distancesum = 0;
        for (int add : distanceS2) {
            distancesum += add;
        }
        return distancesum * 37 / 35;
    }
    public int CalculateTransit(Cursor cursor) {
        int[] distance = new int[size];
        int[] time = new int[size];
        cursor.moveToFirst();
        for (m = 0; m < cursor.getCount(); m++) {
            distance[m] = cursor.getInt(cursor.getColumnIndex("DISTANCE"));
            time[m] = cursor.getInt(cursor.getColumnIndex("TIME"));
            cursor.moveToNext();
        }
        cursor.close();
        int distancesum = 0;
        for (int add : distance) {
            distancesum += add;
        }
        int timesum = 0;
        for (int add : time) {
            timesum += add;
        }
        return distancesum + timesum;
    }
    public int CalculateAC(Cursor cursor) {
        int[] time = new int[size];
        cursor.moveToFirst();
        for (m = 0; m < cursor.getCount(); m++) {
            time[m] = cursor.getInt(0);
            cursor.moveToNext();
        }
        cursor.close();
        int timesum = 0;
        for (int add : time) {
            timesum += add;
        }
//        outputs units in kWh of electricity
        return timesum / 90;
    }
    public int CalculateHeating(Cursor cursor) {
        int[] time = new int[size];
        cursor.moveToFirst();
        for (m = 0; m < cursor.getCount(); m++) {
            time[m] = cursor.getInt(0);
            cursor.moveToNext();
        }
        cursor.close();
        int timeSum = 0;
        for (int Atime : time) {
            timeSum += Atime;
        }
        return 1012 * (timeSum/60);
    }

    public int[] CalculateFinalEnergies() {
        int[] finalValues = new int[7];
        for (m = 0; m < 7; m++) {
            finalValues[m] = carStandard[m] + carTruck[m] + carElectric[m] + Heating[m] + transit[m] + AC[m];
        }
        TODAYENERGY = finalValues[0];
        return finalValues;
    }
}

