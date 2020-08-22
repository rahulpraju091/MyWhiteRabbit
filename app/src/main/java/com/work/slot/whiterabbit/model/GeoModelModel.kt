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
data class GeoModelModel(
    @SerializedName("lat")
    var lat: String?,
    @SerializedName("lng")
    var lng: String?
) : Parcelable {
    /** @constructor secondary constructor for initialise.
     */
    constructor() : this(null, null)
}