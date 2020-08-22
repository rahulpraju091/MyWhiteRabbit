package com.work.experion.database.contract

import android.provider.BaseColumns

/**
 * Contract class.
 *
 * This class handles all fields of the employee table, and table name.
 *
 * @param EMPLOYEE_TABLE is the table name.
 * @param ID is the primary key of the table.
 */
class EmployeeContract {
    object Entry : BaseColumns {
        const val EMPLOYEE_TABLE = "employee_table"
        const val ID = "id"
        const val NAME = "name"
        const val USER_NAME = "user_name"
        const val EMAIL = "email"
        const val PROFILE_IMAGE = "profile_image"
        const val PHONE = "phone"
        const val WEBSITE = "website"
    }
}
