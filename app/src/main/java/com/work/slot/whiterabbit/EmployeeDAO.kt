package com.work.slot.whiterabbit

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.work.experion.database.DbDao
import com.work.experion.database.contract.AddressContract
import com.work.experion.database.contract.CompanyContract
import com.work.experion.database.contract.EmployeeContract
import com.work.slot.whiterabbit.model.AddressModel
import com.work.slot.whiterabbit.model.CompanyModel
import com.work.slot.whiterabbit.model.EmployeeModel
import java.util.*

/**
 * DAO class.
 *
 * This class handles all feature related DB operations.
 * @param context is the Context value of calling class. Used for initialize DB
 */
class EmployeeDAO(context: Context) : DbDao(context) {

    /** DB insert function. Its used for insert employee data into all 3 tables.
     * @return insert long value if the insertion is success.
     */
    fun addEmployeeDetailsToDB(employeeList: ArrayList<EmployeeModel?>) {
        for (i in 0..employeeList.size.minus(1)) {
            val employeeValues = ContentValues().also {
                it.put(EmployeeContract.Entry.ID, employeeList[i]?.id)
                it.put(EmployeeContract.Entry.NAME, employeeList[i]?.name)
                it.put(EmployeeContract.Entry.USER_NAME, employeeList[i]?.username)
                it.put(EmployeeContract.Entry.EMAIL, employeeList[i]?.email)
                it.put(EmployeeContract.Entry.PROFILE_IMAGE, employeeList[i]?.profileImage)
                it.put(EmployeeContract.Entry.PHONE, employeeList[i]?.phone)
                it.put(EmployeeContract.Entry.WEBSITE, employeeList[i]?.website)
            }
            val insert =
                database!!.insert(EmployeeContract.Entry.EMPLOYEE_TABLE, null, employeeValues)
            if (insert > 0) {
                val addressValues = ContentValues().also {
                    it.put(AddressContract.Entry.STREET, employeeList[i]?.address?.street)
                    it.put(AddressContract.Entry.SUITE, employeeList[i]?.address?.suite)
                    it.put(AddressContract.Entry.CITY, employeeList[i]?.address?.city)
                    it.put(AddressContract.Entry.ZIP_CODE, employeeList[i]?.address?.zipcode)
                    it.put(
                        AddressContract.Entry.GEO_LOCATION,
                        employeeList[i]?.address?.geo?.lat + "/" + employeeList[i]?.address?.geo?.lng
                    )
                    it.put(AddressContract.Entry.EMPLOYEE_ID, employeeList[i]?.id)
                }
                val insertAddress =
                    database!!.insert(AddressContract.Entry.ADDRESS_TABLE, null, addressValues)
                if (insertAddress > 0) {
                    val companyValues = ContentValues().also {
                        it.put(CompanyContract.Entry.NAME, employeeList[i]?.company?.name)
                        it.put(
                            CompanyContract.Entry.CATCH_PHRASE,
                            employeeList[i]?.company?.catchPhrase
                        )
                        it.put(CompanyContract.Entry.BS, employeeList[i]?.company?.bs)
                        it.put(CompanyContract.Entry.EMPLOYEE_ID, employeeList[i]?.id)
                    }
                    database!!.insert(CompanyContract.Entry.COMPANY_TABLE, null, companyValues)
                }
            }
        }
    }

    @SuppressLint("Recycle")
    fun isTableEmpty(): Boolean {
        var empty = true
        val cursor: Cursor = database!!.rawQuery(
            "SELECT COUNT(*) FROM " + EmployeeContract.Entry.EMPLOYEE_TABLE,
            null
        )
        if (cursor.moveToFirst()) {
            empty = cursor.getInt(0) == 0
        }
        return empty
    }

    /** DB read function. Its used for get employee details. */
    @SuppressLint("Recycle")
    fun getAllEmployeeDetailsFromDB(): ArrayList<EmployeeModel?>? {
        val employeeList: ArrayList<EmployeeModel?>? = arrayListOf()
        val cursor: Cursor =
            database!!.query(
                EmployeeContract.Entry.EMPLOYEE_TABLE,
                null,
                null,
                null,
                null,
                null,
                null
            )
        while (cursor.moveToNext()) {
            val employeeItem = EmployeeModel()
            employeeItem.id = cursor.getInt(cursor.getColumnIndex(EmployeeContract.Entry.ID))
            employeeItem.name = cursor.getString(cursor.getColumnIndex(EmployeeContract.Entry.NAME))
            employeeItem.username =
                cursor.getString(cursor.getColumnIndex(EmployeeContract.Entry.USER_NAME))
            employeeItem.email =
                cursor.getString(cursor.getColumnIndex(EmployeeContract.Entry.EMAIL))
            employeeItem.profileImage =
                cursor.getString(cursor.getColumnIndex(EmployeeContract.Entry.PROFILE_IMAGE))
            employeeList?.add(employeeItem)
        }
        for (i in 0..employeeList?.size!!.minus(1)) {
            val company = getCompanyDetails(employeeList[i]?.id)
            employeeList[i]?.company = company
        }
        return employeeList
    }

    /** DB read function. Its used for get address details. */
    @SuppressLint("Recycle")
    fun getAddressDetails(id: Int?): AddressModel {
        val address = AddressModel()
        val cursor: Cursor =
            database!!.query(
                AddressContract.Entry.ADDRESS_TABLE,
                null,
                CompanyContract.Entry.EMPLOYEE_ID + "=?",
                arrayOf(id.toString()),
                null,
                null,
                null
            )
        if (cursor.moveToNext()) {
            address.street = cursor.getString(cursor.getColumnIndex(AddressContract.Entry.STREET))
            address.suite = cursor.getString(cursor.getColumnIndex(AddressContract.Entry.SUITE))
            address.city = cursor.getString(cursor.getColumnIndex(AddressContract.Entry.CITY))
            address.zipcode =
                cursor.getString(cursor.getColumnIndex(AddressContract.Entry.ZIP_CODE))
            val geoLoc = cursor.getString(cursor.getColumnIndex(AddressContract.Entry.GEO_LOCATION))
            val location = geoLoc.split("/")
            location.let {
                address.geo?.lat = location[0]
                address.geo?.lng = location[1]
            }

        }
        return address
    }


    /** DB read function. Its used for get company details. */
    @SuppressLint("Recycle")
    fun getCompanyDetails(id: Int?): CompanyModel? {
        val company = CompanyModel()
        val cursor: Cursor =
            database!!.query(
                CompanyContract.Entry.COMPANY_TABLE, null,
                CompanyContract.Entry.EMPLOYEE_ID + "=?",
                arrayOf<String>(id.toString()), null, null, null, null
            )
        if (cursor.moveToNext()) {
            company.name = cursor.getString(cursor.getColumnIndex(CompanyContract.Entry.NAME))
            company.catchPhrase =
                cursor.getString(cursor.getColumnIndex(CompanyContract.Entry.CATCH_PHRASE))
            company.bs = cursor.getString(cursor.getColumnIndex(CompanyContract.Entry.BS))

        }
        return company
    }

}