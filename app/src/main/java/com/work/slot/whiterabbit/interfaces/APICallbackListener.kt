package com.work.slot.whiterabbit.interfaces

/** Interface for handle API callback.*/
interface APICallbackListener {
    fun onResponseSuccess()
    fun onResponseFailure()
    fun onResponseNull()
}