package com.work.slot.whiterabbit.empDetails

import com.work.slot.whiterabbit.EmployeeDAO
import com.work.slot.whiterabbit.interfaces.AppInterfaceAPI
import com.work.slot.whiterabbit.model.AddressModel
import com.work.slot.whiterabbit.model.CompanyModel
import com.work.slot.whiterabbit.model.EmployeeModel
import retrofit2.Response

/**
 * Repository class.
 *
 * This class handles all the DB functions and returns their output.
 *
 * @param employeeDAO is the object of EmployeeDAO class. It handles all the DB functions
 */
class DetailsRepository(
    private val employeeDAO: EmployeeDAO
) {
    fun getAddressDetails(id: Int?): AddressModel {
        return employeeDAO.getAddressDetails(id)
    }

    fun getCompanyDetails(id: Int?): CompanyModel? {
        return employeeDAO.getCompanyDetails(id)
    }
}