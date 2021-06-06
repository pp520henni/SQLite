package com.example.sqlitetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {
    /**
     * 构造方法
     * name为数据库的名称
     *
     * @param name    数据库名称
     * @param version 版本
     */
    public DBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 第一次创建就是进行onCreate方法
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * 方式1和方式2都时可以创建数据库的，用 CREATE TABLE IF NOT EXISTS创建表时
         * 当表已经存在的时候，它不会有操作，也不会去报错。没有的话，就当表存在再去创建，
         * 那么就会创建失败并报错。
         */
        //方式1
        db.execSQL("CREATE TABLE IF NOT EXISTS user" + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(255), phoneNumber INTERGER(11), password VARCHAR(255))");
        //方式2
        db.execSQL("CREATE TABLE person" + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(255), phoneNumber INTERGER(11), password VARCHAR(255))");
    }

    /**
     * 当版本号不一样的的话，就会调用此方法
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //简单的时候就暂时不用更新了嘿嘿嘿
    }
}
