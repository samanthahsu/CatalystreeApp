package com.example.catalystreeapp.Main;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MasterDataBaseHelper extends SQLiteOpenHelper {
    public MasterDataBaseHelper(Context context)
    {
        super(context, "catalystree.db", null, 1);

    }
    // Called when no database exists in disk and the helper class needs
    // to create a new one.
    @Override
    public void onCreate(SQLiteDatabase _db)
    {
        final String USER_CREATE_TABLE =
                "create table " + "User" +"( "
                        + "ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, EMAIL TEXT, PASSWORD TEXT);" ;
        _db.execSQL(USER_CREATE_TABLE);

        final String CAR_CREATE_TABLE = "create table " + "CAR" +
                "( " + "USERNAME text, DATE text, TYPE text, DISTANCE int, TIME int);";
        _db.execSQL(CAR_CREATE_TABLE);

        final String TRANSIT_CREATE_TABLE = "create table"+" TRANSIT" +
                "( " + "USERNAME text, DATE text, DISTANCE int, TIME int);";
        _db.execSQL(TRANSIT_CREATE_TABLE);

        final String WALK_CREATE_TABLE = "create table"+" WALK" +
                "( " + "USERNAME text, DATE text, DISTANCE int, TIME int);";
        _db.execSQL(WALK_CREATE_TABLE);

        final String HEAT_CREATE_TABLE = "create table"+" HEAT" +
                "( " + "USERNAME text, DATE text, TYPE text, TEMPERATURE int, TIME int);";
        _db.execSQL(HEAT_CREATE_TABLE);

        final String CONDITIONING_CREATE_TABLE = "create table"+" CONDITIONING" +
            "( " + "USERNAME text, DATE text, TYPE text, TIME int);";
        _db.execSQL(CONDITIONING_CREATE_TABLE);

        final String ELECTRICITY_CREATE_TABLE = "create table"+" ELECTRICITY" +
                "( " + "USERNAME text, DATE text, COST int);";
        _db.execSQL(ELECTRICITY_CREATE_TABLE);

        final String GAS_CREATE_TABLE = "create table"+" GAS" +
                "( " + "USERNAME text, DATE text, COST int);";
        _db.execSQL(GAS_CREATE_TABLE);

        final String WATER_CREATE_TABLE = "create table"+" WATER" +
                "( " + "USERNAME text, DATE text, COST int);";
        _db.execSQL(WATER_CREATE_TABLE);

    }
    // Called when there is a database version mismatch meaning that the version
    // of the database on disk needs to be upgraded to the current version.
    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion)
    {
        // Log the version upgrade.
        Log.w("TaskDBAdapter", "Upgrading from version " +_oldVersion + " to " +_newVersion + ", which will destroy all old data");

        // Upgrade the existing database to conform to the new version. Multiple
        // previous versions can be handled by comparing _oldVersion and _newVersion
        // values.
        // The simplest case is to drop the old table and create a new one.
        _db.execSQL("DROP TABLE IF EXISTS " + "TEMPLATE");
        // Create a new one.
        onCreate(_db);
    }
    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }

}
