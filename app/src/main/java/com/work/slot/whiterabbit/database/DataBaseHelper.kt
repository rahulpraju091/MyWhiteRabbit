package com.work.experion.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import com.work.experion.database.contract.EmployeeContract
import com.work.experion.database.contract.AddressContract
import com.work.experion.database.contract.CompanyContract

/**
 * Database class.
 *
 * This class handles all the DB actions like creating and updating tables.
 *
 * @param DATABASE_NAME is the name of the database.
 * @param DATABASE_VERSION is the version number of the database
 */
class DataBaseHelper(private var context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null,
    DATABASE_VERSION
) {

    companion object {
        private var instance: DataBaseHelper? = null
        fun getHelper(context: Context): DataBaseHelper? {
            if (instance == null) {
                instance = DataBaseHelper(context.applicationContext)
            }
            return instance
        }

        const val DATABASE_NAME = "WhiteRabbitTest.db"
        const val DATABASE_VERSION = 1
        const val CREATE_EMPLOYEE_TABLE = "CREATE TABLE " +
                EmployeeContract.Entry.EMPLOYEE_TABLE + "(" +
                EmployeeContract.Entry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EmployeeContract.Entry.NAME + " TEXT, " +
                EmployeeContract.Entry.USER_NAME + " TEXT, " +
                EmployeeContract.Entry.EMAIL + " TEXT, " +
                EmployeeContract.Entry.PROFILE_IMAGE + " TEXT, " +
                EmployeeContract.Entry.PHONE + " TEXT, " +
                EmployeeContract.Entry.WEBSITE + " TEXT )"

        const val CREATE_ADDRESS_TABLE = "CREATE TABLE " +
                AddressContract.Entry.ADDRESS_TABLE + "(" +
                AddressContract.Entry.STREET + " TEXT, " +
                AddressContract.Entry.SUITE + " TEXT, " +
                AddressContract.Entry.CITY + " TEXT, " +
                AddressContract.Entry.ZIP_CODE + " TEXT, " +
                AddressContract.Entry.GEO_LOCATION + " TEXT, " +
                AddressContract.Entry.EMPLOYEE_ID + " INTEGER, " + "FOREIGN KEY(" + AddressContract.Entry.EMPLOYEE_ID + ") REFERENCES " +
                EmployeeContract.Entry.EMPLOYEE_TABLE + "(" + EmployeeContract.Entry.ID + ")  ON DELETE CASCADE )"

        const val CREATE_COMPANY_TABLE = "CREATE TABLE " +
                CompanyContract.Entry.COMPANY_TABLE + "(" +
                CompanyContract.Entry.NAME + " TEXT, " +
                CompanyContract.Entry.CATCH_PHRASE + " TEXT, " +
                CompanyContract.Entry.BS + " TEXT, " +
                CompanyContract.Entry.EMPLOYEE_ID + " INTEGER, " + "FOREIGN KEY(" + CompanyContract.Entry.EMPLOYEE_ID + ") REFERENCES " +
                EmployeeContract.Entry.EMPLOYEE_TABLE + "(" + EmployeeContract.Entry.ID + ")  ON DELETE CASCADE )"
    }

    override fun onConfigure(db: SQLiteDatabase?) {
        db?.setForeignKeyConstraintsEnabled(true)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_EMPLOYEE_TABLE)
        db?.execSQL(CREATE_ADDRESS_TABLE)
        db?.execSQL(CREATE_COMPANY_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }


}