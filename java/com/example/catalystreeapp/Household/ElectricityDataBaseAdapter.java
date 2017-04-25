package com.example.catalystreeapp.Household;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.catalystreeapp.Main.MasterDataBaseHelper;

public class ElectricityDataBaseAdapter {

    private static final String DATABASE_NAME = "catalystree.db";
    private static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    private static final String DATABASE_TABLE = "ELECTRICITY";

    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_TIME = "COST";

    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private MasterDataBaseHelper dbHelper;

    public ElectricityDataBaseAdapter(Context _context) {
        context = _context;
        dbHelper = new MasterDataBaseHelper(context);
    }

    public ElectricityDataBaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }


    public void insertEntry(String username, String date, int cost) {
        ContentValues newValues = new ContentValues();
        newValues.put("USERNAME", username);
        newValues.put("DATE", date);
        newValues.put("COST", cost);

        db.insert("ELECTRICITY", null, newValues);
        Toast.makeText(context, "Data Saved", Toast.LENGTH_SHORT).show();
    }

    public Cursor getElectricityEntry(String username) {

        Cursor c = db.query(true, "ELECTRICITY", new String[]{"DATE, COST"}, "USERNAME=?", new String[]{username}, null, null, null, "12");
        if (c.getCount() < 1) {
            c.close();
        }
        for (int i = 0; i < c.getCount(); i++) {
            c.moveToFirst();
            String date = c.getString(c.getColumnIndex("DATE"));
            int cost = c.getInt(c.getColumnIndex("COST"));
            c.moveToNext();
        }
        return c;
    }
}