package com.example.catalystreeapp.Household;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.catalystreeapp.Main.MasterDataBaseHelper;

public class WaterDataBaseAdapter {

    private static final String DATABASE_NAME = "catalystree.db";
    private static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    private static final String DATABASE_TABLE ="WATER";

    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_TIME = "COST";

    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private MasterDataBaseHelper dbHelper;

    public WaterDataBaseAdapter(Context _context) {
        context = _context;
        dbHelper = new MasterDataBaseHelper(context);
    }

    public WaterDataBaseAdapter open() throws SQLException {
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
        // Assign values for each row.
        newValues.put("USERNAME", username);
        newValues.put("DATE", date);
        newValues.put("COST", cost);

        // Insert the row into your table
        db.insert("WATER", null, newValues);
        Toast.makeText(context, "Data Saved", Toast.LENGTH_SHORT).show();
    }
    public int deleteEntry(String username) {
        //String id=String.valueOf(ID);
        String where = "ID=?";
        int numberOFEntriesDeleted = db.delete("WATER", where, new String[]{username});
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }

    public Cursor getWaterEntry(String username) {

        Cursor c = db.query(true, "WATER", new String[]{"date, cost"} ,"USERNAME=?", new String[]{username}, null, null, null, "10");
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

    public void updateEntry(String username, String password, String email) {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("USERNAME", username);
        updatedValues.put("PASSWORD", password);
        updatedValues.put("Email", email);

        String where = "USERNAME = ?";
        db.update("LOGIN", updatedValues, where, new String[]{username});
    }
}