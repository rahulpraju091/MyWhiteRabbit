package com.work.experion.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase

/**
 * DAO class.
 *
 * This class initializes database object.
 *
 * @param database is the object of SQLiteDatabase class.
 * @param dbHelper is the object of DataBaseHelper class. It handles all the DB actions
 */
open class DbDao() {

    var database: SQLiteDatabase? = null
    private var dbHelper: DataBaseHelper? = null

    /**
     * @constructor initializes db object
     */
    constructor(context: Context) : this() {
        if (dbHelper == null) {
            dbHelper = DataBaseHelper.getHelper(context)
        }
        database = dbHelper?.writableDatabase
    }

}