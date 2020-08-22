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
data class EmployeeModel(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("username")
    var username: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("profile_image")
    var profileImage: String?,
    @SerializedName("address")
    var address: AddressModel?,
    @SerializedName("phone")
    var phone: String?,
    @SerializedName("website")
    var website: String?,
    @SerializedName("company")
    var company: CompanyModel?
) : Parcelable {
    /** @constructor secondary constructor for initialise.
     */
    constructor() : this(null, null, null, null, null, null, null, null, null)
}