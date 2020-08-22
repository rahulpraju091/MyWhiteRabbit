package com.work.slot.whiterabbit

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.slot.whiterabbit.empDetails.EmployeeDetailActivity
import com.work.slot.whiterabbit.interfaces.APICallbackListener
import com.work.slot.whiterabbit.interfaces.AppInterfaceAPI
import com.work.slot.whiterabbit.model.EmployeeModel
import com.work.slot.whiterabbit.viewModel.EmployeeViewModel
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.progress_bar_layout.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val MSG_INTERNET_FAILURE: String = "No Internet Connection !"
        const val MSG_RESPONSE_FAILURE: String = "Something Went Wrong. Try Again Later !"
        const val MSG_RESPONSE_NULL: String = "Can't Connect To Server ! Try Again Later."
        const val EMPLOYEE_PARCEL: String = "employee_data"
        val TAG: String = MainActivity::class.java.simpleName.toString()
    }

    private lateinit var viewModel: EmployeeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val employeeDAO = EmployeeDAO(this@MainActivity)
        val api = AppInterfaceAPI()
        val featureListRepository = EmployeeRepository(api, employeeDAO)
        val featureListVMFactory = EmployeeVMFactory(featureListRepository, employeeDAO)
        featureListVMFactory.let {
            viewModel = ViewModelProvider(this, it).get(EmployeeViewModel::class.java)
        }

        if (employeeDAO.isTableEmpty())
            doGetAllDataFromAPI()
        else
            viewModel.getAllEmployeeDetailsFromDB()

        viewModel.employeeList?.observe(this, Observer {
            recycler_view.apply {
                setHasFixedSize(false)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = EmployeeListAdapter(it)
            }
        })
    }

    /** Call ViewModel function to get city details from API */
    private fun doGetAllDataFromAPI() {
        progress_view.visibility = View.VISIBLE
        if (isNetworkAvailable()) {
            viewModel.doGetEmployeeDetails(object : APICallbackListener {
                override fun onResponseSuccess() {
                    /** Callback function of null response success.
                     * removes progress bar visibility */
                    progress_view.visibility = View.GONE
                }

                /** Callback function of null response failure. Calls when features list is empty or null */
                override fun onResponseFailure() {
                    Toast.makeText(this@MainActivity, MSG_RESPONSE_FAILURE, Toast.LENGTH_SHORT)
                        .show()
                    progress_view.visibility = View.GONE
                }

                /** Callback function of null response case */
                override fun onResponseNull() {
                    Toast.makeText(this@MainActivity, MSG_RESPONSE_NULL, Toast.LENGTH_SHORT)
                        .show()
                    progress_view.visibility = View.GONE
                }

            })
        } else {
            /** Shows Internet failure toast */
            Toast.makeText(this, MSG_INTERNET_FAILURE, Toast.LENGTH_SHORT)
                .show()
            progress_view.visibility = View.GONE
        }
    }

    /** Method to check whether the Internet connection is available or not.
     * @return true if Internet is available.
     */
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    fun goToDetailView(item: EmployeeModel?) {
        val intent = Intent(this, EmployeeDetailActivity::class.java)
        intent.putExtra(EMPLOYEE_PARCEL, item)
        startActivity(intent)
    }


}