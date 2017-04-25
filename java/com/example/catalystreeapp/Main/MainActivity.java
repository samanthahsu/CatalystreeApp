package com.example.catalystreeapp.Main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.catalystreeapp.MainFragments.FHome;
import com.example.catalystreeapp.MainFragments.FInput;
import com.example.catalystreeapp.MainFragments.FProfile;
import com.example.catalystreeapp.MainFragments.FOptions;
import com.example.catalystreeapp.MainFragments.FUsageEnergy;
import com.example.catalystreeapp.R;
import com.example.catalystreeapp.Users.AlertDialogManager;
import com.example.catalystreeapp.Users.SessionManagement;

public class MainActivity extends AppCompatActivity{

    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    AlertDialogManager alert = new AlertDialogManager();
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

        // Session class instance
        session = new SessionManagement(getApplicationContext());
//        displays if user is logged in
//        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_SHORT).show();
//      check if user is logged in
        session.checkLogin();

//        set default displayed fragment
        String caller = getIntent().getStringExtra("caller");
        if (caller.equals("Input")) {
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.content_frame, new FInput());
            tx.commit();
        }
        else {
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.content_frame, new FHome());
            tx.commit();
        }

        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        setupToolbar();

        DataModel[] drawerItem = new DataModel[5];

        drawerItem[0] = new DataModel(R.drawable.ic_home, "Home");
        drawerItem[1] = new DataModel(R.drawable.ic_profile, "Profile");
        drawerItem[2] = new DataModel(R.drawable.ic_input, "Input");
        drawerItem[3] = new DataModel(R.drawable.ic_usage, "Usage");
        drawerItem[4] = new DataModel(R.drawable.ic_settings, "Options");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerToggle();


    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

//links to the drawer items
    private void selectItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new FHome();
                break;
            case 1:
                fragment = new FProfile();
                break;
            case 2:
                fragment = new FInput();
                break;
            case 3:
                fragment = new FUsageEnergy();
                break;
            case 4:
                fragment = new FOptions();
                break;
            default:
                break;
        }

        if (fragment != null) {

            fragment.setArguments(getIntent().getExtras());

            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.content_frame, fragment).commit();


            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();

    }

    void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    void setupDrawerToggle(){
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();
    }

//    allows user to return to previous fragment when back is pressed
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}