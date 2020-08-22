package com.work.slot.whiterabbit.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Data class.
 *
 * This class handles getter and setter properties.
 */
@Parcelize
data class AddressModel(
    @SerializedName("street")
    var street: String?,
    @SerializedName("suite")
    var suite: String?,
    @SerializedName("city")
    var city: String?,
    @SerializedName("zipcode")
    var zipcode: String?,
    @SerializedName("geo")
    var geo: GeoModelModel?
) : Parcelable {
    /** @constructor secondary constructor for initialise.
     */
    constructor() : this(null, null, null, null, null)
}