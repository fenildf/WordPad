package edu.uestc.yang.sqldemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lypeer on 2015/4/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String name = "myDatabase";
    private static final int version = 1;

    public DatabaseHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table word(id integer primary key autoincrement , " +
                "newword ," +
                "translation)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
