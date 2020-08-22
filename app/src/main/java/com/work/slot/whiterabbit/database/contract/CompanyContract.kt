package com.work.experion.database.contract

import android.provider.BaseColumns

/**
 * Contract class.
 *
 * This class handles all fields of the feature table, and table name.
 *
 * @param COMPANY_TABLE is the table name.
 * @param EMPLOYEE_ID is the foreign key of the table.
 */
class CompanyContract {
    object Entry : BaseColumns {
        const val COMPANY_TABLE = "company_table"
        const val NAME = "name"
        const val CATCH_PHRASE = "catch_phrase"
        const val BS = "bs"
        const val EMPLOYEE_ID = "emp_id"
    }
}
