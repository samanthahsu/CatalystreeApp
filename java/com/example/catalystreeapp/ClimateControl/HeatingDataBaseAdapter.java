package com.example.catalystreeapp.ClimateControl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.catalystreeapp.Main.MasterDataBaseHelper;

public class HeatingDataBaseAdapter {

    private static final String DATABASE_NAME = "catalystree.db";
    private static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    private static final String DATABASE_TABLE = "HEATING";

    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_TYPE = "TYPE";
    public static final String COLUMN_TIME = "TIME";

    // SQL Statement to create a new database.
    // Variable to hold the database instance
    public static SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private MasterDataBaseHelper dbHelper;

    public HeatingDataBaseAdapter(Context _context) {
        context = _context;
        dbHelper = new MasterDataBaseHelper(context);
    }

    public HeatingDataBaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }


    public void insertEntry(String username, String date, String type, Integer time) {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("USERNAME", username);
        newValues.put("DATE", date);
        newValues.put("TYPE", type);
        newValues.put("TIME", time);

        // Insert the row into your table
        db.insert("HEAT", null, newValues);
        Toast.makeText(context, "Data Saved", Toast.LENGTH_SHORT).show();
    }

    public Cursor getHeatingEntry(String username, String date) {

        Cursor c = db.query(true, "HEAT", new String[]{"time"}, "USERNAME=? AND DATE =?", new String[]{username, date}, null, null, null, "50");
        if (c.getCount() < 1) {
            c.close();
        }
        for (int i = 0; i < c.getCount(); i++) {
            c.moveToFirst();
            Integer time = c.getInt(c.getColumnIndex("TIME"));
            c.moveToNext();
        }
        return c;
    }
}