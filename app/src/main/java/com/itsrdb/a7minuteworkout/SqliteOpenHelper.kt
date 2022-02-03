package com.itsrdb.a7minuteworkout

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteOpenHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object {  //static variables
        private const val DATABASE_VERSION = 1 // This DATABASE Version
        private const val DATABASE_NAME = "SevenMinutesWorkout.db" // Name of the DATABASE
        private const val TABLE_HISTORY = "history" // Table Name
        private const val COLUMN_ID = "_id" // Column Id
        private const val COLUMN_COMPLETED_DATE = "completed_date" // Column for Completed Date
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //Create table
        val CREATE_EXERCISE_TABLE = ("CREATE TABLE " + TABLE_HISTORY + "(" + COLUMN_ID
        + " INTEGER PRIMARY KEY," + COLUMN_COMPLETED_DATE + " TEXT)")
        db?.execSQL(CREATE_EXERCISE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IS EXISTS " + TABLE_HISTORY)
        onCreate(db)
    }

    fun addDate(date : String){
        val values = ContentValues()
        values.put(COLUMN_COMPLETED_DATE, date)
        val db = this.writableDatabase
        db.insert(TABLE_HISTORY, null, values)
        db.close()
    }

}