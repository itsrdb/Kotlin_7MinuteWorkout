package com.itsrdb.a7minuteworkout

import android.database.sqlite.SQLiteOpenHelper

class SqliteOpenHelper : SQLiteOpenHelper {

    companion object {  //static variables
        private const val DATABASE_VERSION = 1 // This DATABASE Version
        private const val DATABASE_NAME = "SevenMinutesWorkout.db" // Name of the DATABASE
        private const val TABLE_HISTORY = "history" // Table Name
        private const val COLUMN_ID = "_id" // Column Id
        private const val COLUMN_COMPLETED_DATE = "completed_date" // Column for Completed Date
    }

}