package com.example.catalystreeapp.Transportation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.catalystreeapp.Main.MasterDataBaseHelper;

public class CarDataBaseAdapter {

    private static String TAG = "CarDataBaseAdapter";
    public static final int NAME_COLUMN = 1;

    private static final String DATABASE_TABLE = "CAR";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_TYPE = "TYPE";
    public static final String COLUMN_DISTANCE = "DISTANCE";

    // Variable to hold the database instance
    public static SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private MasterDataBaseHelper dbHelper;

    public CarDataBaseAdapter(Context _context) {
        context = _context;
        dbHelper = new MasterDataBaseHelper(context);
    }

    public CarDataBaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }


    public void insertEntry(String username, String date, String type, int distance) {
        ContentValues newValues = new ContentValues();
        newValues.put("USERNAME", username);
        newValues.put("DATE", date);
        newValues.put("TYPE", type);
        newValues.put("DISTANCE", distance);

        db.insert("CAR", null, newValues);
        Toast.makeText(context, "Data Saved", Toast.LENGTH_SHORT).show();
    }
    public Cursor getCarEntry(String username, String Day, String Type) {

        Cursor c = db.query(true, "CAR", new String[]{"DISTANCE"}, "USERNAME=? AND DATE=? AND TYPE=?", new String[]{username, Day, Type}, null, null, null, "50");
        if (c.getCount() < 1) {
            c.close();
        }
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                int distance = c.getInt(c.getColumnIndex("DISTANCE"));
                c.moveToNext();
            }
            return c;
    }
    public void close() {
        db.close();
    }
}