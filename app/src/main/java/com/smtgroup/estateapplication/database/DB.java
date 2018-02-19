package com.smtgroup.estateapplication.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {

    private static String name = "estate.db";
    private static int version = 1;


    public DB(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE `favories` (\n" +
                "\t`product_id`\tINTEGER NOT NULL UNIQUE\n" +
                ");");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists Kullanicilar ");
        onCreate(sqLiteDatabase);
    }
}
