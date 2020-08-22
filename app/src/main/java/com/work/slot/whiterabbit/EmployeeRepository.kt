package com.work.slot.whiterabbit

import com.work.slot.whiterabbit.interfaces.AppInterfaceAPI
import com.work.slot.whiterabbit.model.EmployeeModel
import retrofit2.Response

/**
 * Repository class.
 *
 * This class handles all the API functions and DB functions and returns their output.
 *
 * @param api is the object of AppInterfaceAPI Interface class.
 * @param employeeDAO is the object of EmployeeDAO class. It handles all the DB functions
 */
class EmployeeRepository(
    private val api: AppInterfaceAPI,
    private val employeeDAO: EmployeeDAO
) {
    /** API function. Its used for fetch data from a URL.
     * @return EmployeeModel response data.
     */
    suspend fun doGetEmployeeDetails(): Response<ArrayList<EmployeeModel?>> {
        return api.doGetEmployeeDetails()
    }

    /** DB function. Its used for fetch data from Employee Table.
     * @return EmployeeModel list data.
     */
    fun getAllEmployeeDetailsFromDB(): ArrayList<EmployeeModel?>? {
        return employeeDAO.getAllEmployeeDetailsFromDB()
    }
}