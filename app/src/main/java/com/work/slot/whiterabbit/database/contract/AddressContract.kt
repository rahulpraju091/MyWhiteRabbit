package com.work.experion.database.contract

import android.provider.BaseColumns

/**
 * Contract class.
 *
 * This class handles all fields of the feature table, and table name.
 *
 * @param ADDRESS_TABLE is the table name.
 * @param EMPLOYEE_ID is the foreign key of the table.
 */
class AddressContract {
    object Entry : BaseColumns {
        const val ADDRESS_TABLE = "address_table"
        const val STREET = "street"
        const val SUITE = "suite"
        const val CITY = "city"
        const val ZIP_CODE = "zip_code"
        const val GEO_LOCATION = "loc_coordinates"
        const val EMPLOYEE_ID = "emp_id"
    }
}
