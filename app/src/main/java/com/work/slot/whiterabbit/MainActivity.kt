package com.work.slot.whiterabbit

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.work.slot.whiterabbit.empDetails.EmployeeDetailActivity
import com.work.slot.whiterabbit.interfaces.APICallbackListener
import com.work.slot.whiterabbit.interfaces.AppInterfaceAPI
import com.work.slot.whiterabbit.model.EmployeeModel
import com.work.slot.whiterabbit.viewModel.EmployeeViewModel
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.progress_bar_layout.*
import java.util.*
import java.util.Locale.filter
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    companion object {
        const val MSG_INTERNET_FAILURE: String = "No Internet Connection !"
        const val MSG_RESPONSE_FAILURE: String = "Something Went Wrong. Try Again Later !"
        const val MSG_RESPONSE_NULL: String = "Can't Connect To Server ! Try Again Later."
        const val EMPLOYEE_PARCEL: String = "employee_data"
        val TAG: String = MainActivity::class.java.simpleName.toString()
    }

    private lateinit var viewModel: EmployeeViewModel
    private lateinit var searchView: SearchView
    lateinit var adapter: EmployeeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val employeeDAO = EmployeeDAO(this@MainActivity)
        val api = AppInterfaceAPI()
        val employeeRepository = EmployeeRepository(api, employeeDAO)
        val employeeVMFactory = EmployeeVMFactory(employeeRepository, employeeDAO)
        employeeVMFactory.let {
            viewModel = ViewModelProvider(this, it).get(EmployeeViewModel::class.java)
        }

        if (employeeDAO.isTableEmpty())
            doGetAllDataFromAPI()
        else
            viewModel.getAllEmployeeDetailsFromDB()

        viewModel.employeeList?.observe(this, Observer {
            adapter = EmployeeListAdapter(it)
            recycler_view.layoutManager = LinearLayoutManager(this)
            recycler_view.setHasFixedSize(true)
            recycler_view.adapter = adapter
            adapter.notifyDataSetChanged()
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

                /** Callback function of null response failure. Calls when employee list is empty or null */
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchManager =
            getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search)
            .actionView as SearchView
        searchView.setSearchableInfo(
            searchManager.getSearchableInfo(componentName)
        )
        searchView.maxWidth = Int.MAX_VALUE
        val adapterList: ArrayList<EmployeeModel?> = adapter.getList()
        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                val filteredList: ArrayList<EmployeeModel>? = filter(adapterList, query)
                adapter.setFilter(filteredList!!)
                return true
            }
        })
        return true
    }

    private fun filter(list: ArrayList<EmployeeModel?>, query: String): ArrayList<EmployeeModel>? {
        val sQuery = query.toLowerCase(Locale.ROOT)
        val filteredList: ArrayList<EmployeeModel> =
            ArrayList()
        for (contentModel in list) {
            var strName = ""
            strName = contentModel?.name!!.toLowerCase(Locale.ROOT)
            var strEmail = ""
            strEmail = contentModel.email!!.toLowerCase(Locale.ROOT)
            if (strName.contains(sQuery) || strEmail.contains(sQuery)) {
                filteredList.add(contentModel)
            }
        }
        return filteredList
    }


}