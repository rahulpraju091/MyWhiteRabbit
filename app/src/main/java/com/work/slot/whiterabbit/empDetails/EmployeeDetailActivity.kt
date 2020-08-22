package com.work.slot.whiterabbit.empDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.work.slot.whiterabbit.*
import com.work.slot.whiterabbit.empDetails.viewModel.DetailsViewModel
import com.work.slot.whiterabbit.model.AddressModel
import com.work.slot.whiterabbit.model.CompanyModel
import com.work.slot.whiterabbit.model.EmployeeModel
import kotlinx.android.synthetic.main.activity_employee_detail.*

class EmployeeDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var employeeDetails: EmployeeModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_detail)
        toolbar.title = "Employee Details"
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white))
        setSupportActionBar(toolbar)

        val bundle = intent.extras
        bundle?.let {
            employeeDetails = it.getParcelable(MainActivity.EMPLOYEE_PARCEL)!!
            setupValues(employeeDetails)
        }
        val employeeDAO = EmployeeDAO(this@EmployeeDetailActivity)
        val detailsRepository = DetailsRepository(employeeDAO)
        val detailsVMFactory = DetailsVMFactory(detailsRepository)
        detailsVMFactory.let {
            viewModel = ViewModelProvider(this, it).get(DetailsViewModel::class.java)
        }

        viewModel.addressData?.observe(this, Observer {
            updateAddress(it)
        })
        viewModel.companyData?.observe(this, Observer {
            updateCompanyDetails(it)
        })

        viewModel.getEmployeeAddressDetails(employeeDetails.id)
        viewModel.getEmployeeCompanyDetails(employeeDetails.id)
    }

    private fun updateAddress(it: AddressModel?) {
        textView8.text = it?.street
        textView9.text = it?.suite
        textView10.text = it?.city
        textView11.text = it?.zipcode
        textView12.text = it?.geo?.lat + "-" + it?.geo?.lng
    }

    private fun updateCompanyDetails(it: CompanyModel?) {
        textView14.text = it?.name
        textView15.text = it?.catchPhrase
        textView16.text = it?.bs
    }

    private fun setupValues(employeeDetails: EmployeeModel?) {
        employeeDetails?.let {
            textView2.text = it.name
            textView3.text = it.username
            textView4.text = it.email
            textView5.text = it.phone
            textView6.text = it.website
            Glide.with(this).load(it.profileImage)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .placeholder(R.drawable.user_icon)
                .into(imageView)
        }
    }
}