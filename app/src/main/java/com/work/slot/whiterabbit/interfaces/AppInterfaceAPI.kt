package com.work.slot.whiterabbit.interfaces

import com.work.slot.whiterabbit.model.EmployeeModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Interface
 *
 * This Interface handles all the API call for getting employee details.
 *
 */
interface AppInterfaceAPI {

    companion object {
        operator fun invoke(): AppInterfaceAPI {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
            return Retrofit.Builder()
                .baseUrl("http://www.mocky.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(AppInterfaceAPI::class.java)
        }
    }

    @GET("v2/5d565297300000680030a986")
    suspend fun doGetEmployeeDetails(): Response<ArrayList<EmployeeModel?>>


}