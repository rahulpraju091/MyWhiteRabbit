package com.work.slot.whiterabbit.empDetails.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.work.slot.whiterabbit.EmployeeDAO
import com.work.slot.whiterabbit.empDetails.DetailsRepository
import com.work.slot.whiterabbit.model.AddressModel
import com.work.slot.whiterabbit.model.CompanyModel
import com.work.slot.whiterabbit.model.EmployeeModel

class DetailsViewModel(
    private var detailsRepository: DetailsRepository,
) : ViewModel() {

    companion object {
        val TAG = DetailsViewModel::class.java.simpleName.toString()
    }

    private var _mutableAddressDetails = MutableLiveData<AddressModel?>()
    val addressData: LiveData<AddressModel?>?
        get() = _mutableAddressDetails
    private var _mutableCompanyDetails = MutableLiveData<CompanyModel?>()
    val companyData: LiveData<CompanyModel?>?
        get() = _mutableCompanyDetails

    fun getEmployeeAddressDetails(id: Int?) {
        val addressDetails = detailsRepository.getAddressDetails(id)
        addressDetails.let {
            _mutableAddressDetails.value = addressDetails
        }
    }

    fun getEmployeeCompanyDetails(id: Int?) {
        val companyDetails = detailsRepository.getCompanyDetails(id)
        companyDetails.let {
            _mutableCompanyDetails.value = companyDetails
        }
    }
}