package com.work.slot.whiterabbit.empDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.work.slot.whiterabbit.EmployeeDAO
import com.work.slot.whiterabbit.empDetails.viewModel.DetailsViewModel
import com.work.slot.whiterabbit.viewModel.EmployeeViewModel

/**
 * ViewModel Factory class.
 *
 * Using this factory class we achieve dependency injection
 * @param employeeRepository is the Repository class object.
 */
@Suppress("UNCHECKED_CAST")
class DetailsVMFactory(
    private val detailsRepository: DetailsRepository,
    private val employeeDAO: EmployeeDAO
) :
    ViewModelProvider.NewInstanceFactory() {

    /** Override function.
     * @return FeatureListViewModel instance.
     */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(detailsRepository, employeeDAO) as T
    }
}