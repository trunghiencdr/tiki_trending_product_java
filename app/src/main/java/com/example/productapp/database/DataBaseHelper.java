package com.example.productapp.database;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_PRODUCT = "PRODUCT";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_PRICE = "PRICE";
    public static final String COLUMN_SHORT_DESCRIPTION = "SHORT_DESCRIPTION";
    public static final String COLUMN_RATING_AVERAGE = "RATING_AVERAGE";
    public static final String COLUMN_QUANTITY_SOLD = "QUANTITY_SOLD";
    public static final String COLUMN_QUANTITY_SOLD_TEXT = "QUANTITY_SOLD_TEXT";
    public static final String COLUMN_THUMBNAIL = "THUMBNAIL";

    public static final String TABLE_QUANTITY_SOLD = "QUANTITY_SOLD";
    public static final String COLUMN_TEXT = "TEXT";
    public static final String COLUMN_VALUE = "VALUE";



    public static DataBaseHelper INSTANCE = null;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);



//    private static final String createQuantitySoldTableStatement = "CREATE TABLE " + TABLE_QUANTITY_SOLD + "(\n" +
//            "\t    " + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, \n" +
//            "\t    " + COLUMN_TEXT + " TEXT, \n" +
//            " \t   " + COLUMN_VALUE+ " INTEGER )";

    private static final String createProductTableStatement = "CREATE TABLE " + TABLE_PRODUCT + "(\n" +
            "\t    " + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, \n" +
            "\t    " + COLUMN_NAME + " TEXT, \n" +
            "\t    " + COLUMN_SHORT_DESCRIPTION + " INTEGER ,\n"+
            "\t    " + COLUMN_RATING_AVERAGE + " REAL ,\n"+
            "\t    " + COLUMN_QUANTITY_SOLD + " INTEGER ,\n"+
            "\t    " + COLUMN_QUANTITY_SOLD_TEXT + " TEXT ,\n"+
            " \t   " + COLUMN_PRICE + " INTEGER, \n" +
            " \t  " + COLUMN_THUMBNAIL + " TEXT)" ;




    private DataBaseHelper(Application application) {
        super(application, "manage_product.db", null, 5);

    }

    public static synchronized DataBaseHelper getInstance(Application application) {
        if (INSTANCE == null) INSTANCE = new DataBaseHelper(application);
        return INSTANCE;
    }

    // This is called if the first time database is accessed.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createProductTableStatement);
    }

    // This is called if the database version number change.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        onCreate(db);
    }


    //insert, delete, update, select


//    public boolean insert(String tableName, ContentValues values) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        try{
//            db.insert(tableName, null, values);
//        }catch (Exception e){
//            return false;
//        }
//        return true;
//    }

    public boolean insert(String tableName, ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON");
        try{
            db.insertOrThrow(tableName, null, values);
        }catch (Exception e){
            return false;
        }
     return true;
    }


    public boolean update(String tableName, String whereClause,
                          ContentValues values, String[] whereArgs) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON");
        return db.update(tableName, values, whereClause, whereArgs) > 0;
    }

    public boolean delete(String tableName, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON");
        try{
            db.delete(tableName, whereClause, whereArgs);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Cursor query(String query, String[] selectionArgs) {
        return this.getReadableDatabase().rawQuery(query, selectionArgs, null);
    }

    public void execSql(String query){
        this.getWritableDatabase().execSQL(query);
    }
}

