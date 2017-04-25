package com.example.catalystreeapp.Users;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.catalystreeapp.Main.MasterDataBaseHelper;

public class UserDbAdapter {
    private static String TAG = "UserDataBaseAdapter";
    final String DATABASE_NAME = "catalystree.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;

    private static final String DATABASE_TABLE ="User";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_PASSWORD = "PASSWORD";


    // Variable to hold the database instance
    public static SQLiteDatabase db;
    // Context of the application using the database.
    private  Context context;
    // Database open/upgrade helper
    private MasterDataBaseHelper dbHelper;

    public UserDbAdapter(Context _context) {
        context = _context;
        dbHelper = new MasterDataBaseHelper(context);
    }

    public UserDbAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    public static void insertEntry(String userName, String password, String email) {
            ContentValues newValues = new ContentValues();
            // Assign values for each row.
            newValues.put("USERNAME", userName);
            newValues.put("Email", email);
            newValues.put("PASSWORD", password);

        // Insert the row into your table
        db.insert("User", null, newValues);
    }

//    public int deleteEntry(String UserName) {
//        //String id=String.valueOf(ID);
//        String where = "USERNAME=?";
//        int numberOFEntriesDeleted = db.delete("User", where, new String[]{UserName});
//        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
//        return numberOFEntriesDeleted;
//    }

//      get entry for the log in
        String getSingleEntry(String userName) {
        Cursor userCursor = db.query("User", null, " USERNAME=?", new String[]{userName}, null, null, null);
        if (userCursor.getCount() < 1) // UserName Not Exist
        {
//            userCursor.close();
            return "NOOOOOOOOOOOOOOOOOOOOO";
        }
        userCursor.moveToFirst();
        String password = userCursor.getString(userCursor.getColumnIndex("PASSWORD"));
        userCursor.close();
        return password;
    }

    public void updateEntry(String userName, String password, String email) {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("USERNAME", userName);
        updatedValues.put("PASSWORD", password);
        updatedValues.put("Email", email);

        String where = "USERNAME = ?";
        db.update("User", updatedValues, where, new String[]{userName});
    }

    public void close() {
        db.close();
    }

}