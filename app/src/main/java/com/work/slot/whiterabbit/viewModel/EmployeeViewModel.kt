package com.work.slot.whiterabbit.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.work.slot.whiterabbit.EmployeeDAO
import com.work.slot.whiterabbit.EmployeeRepository
import com.work.slot.whiterabbit.common.Coroutines
import com.work.slot.whiterabbit.interfaces.APICallbackListener
import com.work.slot.whiterabbit.model.EmployeeModel

class EmployeeViewModel(
    private var employeeRepository: EmployeeRepository,
    private var employeeDAO: EmployeeDAO
) : ViewModel() {

    companion object {
        val TAG = EmployeeViewModel::class.java.simpleName.toString()
    }

    private var _mutableEmployeeList = MutableLiveData<ArrayList<EmployeeModel?>>()
    val employeeList: LiveData<ArrayList<EmployeeModel?>>?
        get() = _mutableEmployeeList

    fun doGetEmployeeDetails(listener: APICallbackListener) {
        Coroutines.main {
            val response = employeeRepository.doGetEmployeeDetails()
            Log.e(TAG, response.body().toString())
            if (response.body() == null) {
                listener.onResponseFailure()
            } else if (response.isSuccessful && response.body() != null) {
                _mutableEmployeeList.value = response.body()
                employeeDAO.addEmployeeDetailsToDB(response.body()!!)
                listener.onResponseSuccess()
            } else {
                listener.onResponseNull()
            }
        }

    }

    fun getAllEmployeeDetailsFromDB() {
        val employeeList = employeeRepository.getAllEmployeeDetailsFromDB()
        employeeList?.let {
            _mutableEmployeeList.value = employeeList
        }
    }
}