package com.example.quanlisinhvien.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CreateDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "QuanLySinhVien.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TEN_BANG_SINHVIEN = "SinhVien";
    public static final String COT_ID = "id";
    public static final String COT_TEN = "ten";
    public static final String COT_LOP = "lop";

    public CreateDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TAO_BANG_SINHVIEN = "create table " + TEN_BANG_SINHVIEN + " (" +
                COT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COT_TEN + " TEXT NOT NULL, " +
                COT_LOP + " TEXT NOT NULL) ";
        db.execSQL(TAO_BANG_SINHVIEN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TEN_BANG_SINHVIEN);
        onCreate(db);
    }

    public long insertSinhVien(String ten, String lop){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COT_TEN, ten);
        values.put(COT_LOP, lop);
        return db.insert(TEN_BANG_SINHVIEN, null,values);
    }

    public int updateSinhVien( int id ,String ten, String lop){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COT_TEN, ten);
        values.put(COT_LOP, lop);
        return db.update(TEN_BANG_SINHVIEN, values, COT_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int deleteSinhVien(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TEN_BANG_SINHVIEN,COT_ID + "=?", new String[]{String.valueOf(id)});
    }

    public Cursor getAllSinhVien(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TEN_BANG_SINHVIEN, null);
    }
}
