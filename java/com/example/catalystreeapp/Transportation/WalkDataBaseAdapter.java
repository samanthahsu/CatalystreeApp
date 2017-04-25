package com.example.catalystreeapp.Transportation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.catalystreeapp.Main.MasterDataBaseHelper;

public class WalkDataBaseAdapter {

    private static final String DATABASE_NAME = "catalystree.db";
    private static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    private static final String DATABASE_TABLE ="WALK";

    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_DISTANCE = "DISTANCE";
    public static final String COLUMN_TIME = "TIME";

    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private MasterDataBaseHelper dbHelper;

    public WalkDataBaseAdapter(Context _context) {
        context = _context;
        dbHelper = new MasterDataBaseHelper(context);
    }

    public WalkDataBaseAdapter open() throws SQLException {
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

        db.insert("CAR", null, newValues);
        Toast.makeText(context, "Data Saved", Toast.LENGTH_SHORT).show();
    }
    public int deleteEntry(String UserName) {
        //String id=String.valueOf(ID);
        String where = "ID=?";
        int numberOFEntriesDeleted = db.delete("LOGIN", where, new String[]{UserName});
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }

    public Cursor getWalkEntry(String username, String Day) {

        Cursor walkCursor = db.query(true, "WALK", new String[]{"date, distance, time"}, "USERNAME=?", new String[]{username}, null, null, null, "50");
        if (walkCursor.getCount() < 1) {
            walkCursor.close();
        }
        for (int i = 0; i < walkCursor.getCount(); i++) {
                int distance = walkCursor.getInt(walkCursor.getColumnIndex("DISTANCE"));
                int time = walkCursor.getInt(walkCursor.getColumnIndex("TIME"));
            }
        return walkCursor;
    }


    public void updateEntry(String userName, String password, String email) {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("USERNAME", userName);
        updatedValues.put("PASSWORD", password);
        updatedValues.put("Email", email);

        String where = "USERNAME = ?";
        db.update("LOGIN", updatedValues, where, new String[]{userName});
    }
}