package com.work.slot.whiterabbit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.work.slot.whiterabbit.viewModel.EmployeeViewModel

/**
 * ViewModel Factory class.
 *
 * Using this factory class we achieve dependency injection
 * @param employeeRepository is the Repository class object.
 */
@Suppress("UNCHECKED_CAST")
class EmployeeVMFactory(
    private val employeeRepository: EmployeeRepository,
    private val employeeDAO: EmployeeDAO
) :
    ViewModelProvider.NewInstanceFactory() {

    /** Override function.
     * @return FeatureListViewModel instance.
     */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EmployeeViewModel(employeeRepository,employeeDAO) as T
    }
}