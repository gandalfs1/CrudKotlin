package com.example.crud

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_VERSION = 1;
        private const val DATABASE_NAME = "student.db";
        private const val TABLE_NAME = "table_student";
        private const val ID = "id";
        private const val NAME = "name";
        private const val EMAIL = "email";
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableStudent = ("CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY," +
                NAME + " TEXT," +
                EMAIL + " TEXT " + ")")

        db?.execSQL(createTableStudent)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}