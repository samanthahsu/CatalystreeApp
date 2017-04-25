package com.example.catalystreeapp.Transportation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.catalystreeapp.Main.MasterDataBaseHelper;

public class TransitDataBaseAdapter {

    private static final String DATABASE_NAME = "catalystree.db";
    private static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    private static final String DATABASE_TABLE = "TRANSIT";

    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_DISTANCE = "DISTANCE";
    public static final String COLUMN_TIME = "TIME";

    // Variable to hold the database instance
    public static SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private MasterDataBaseHelper dbHelper;

    public TransitDataBaseAdapter(Context _context) {
        context = _context;
        dbHelper = new MasterDataBaseHelper(context);
    }

    public TransitDataBaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    public void insertEntry(String USERNAME, String date, int distance, int time) {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("USERNAME", USERNAME);
        newValues.put("DATE", date);
        newValues.put("DISTANCE", distance);
        newValues.put("TIME", time);

        // Insert the row into your table
        db.insert("TRANSIT", null, newValues);
        Toast.makeText(context, "Data Saved", Toast.LENGTH_SHORT).show();
    }

    public Cursor getTransitEntry(String username, String Day) {

        Cursor transitCursor = db.query(true, "TRANSIT", new String[]{"DISTANCE, TIME"}, "USERNAME=? AND DATE=?", new String[]{username, Day}, null, null, null, "50");
        if (transitCursor.getCount() < 1) {
            transitCursor.close();
        }
        transitCursor.moveToFirst();
        for (int i = 0; i < transitCursor.getCount(); i++) {
            int distance = transitCursor.getInt(transitCursor.getColumnIndex("DISTANCE"));
            int time = transitCursor.getInt(transitCursor.getColumnIndex("TIME"));
            transitCursor.moveToNext();
        }
        return transitCursor;
    }
}