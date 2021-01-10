package com.example.soa.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Auth(@SerializedName("access_token") val accessToken: String) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readString()!!)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(accessToken)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Auth> {
        override fun createFromParcel(parcel: Parcel): Auth {
            return Auth(parcel)
        }

        override fun newArray(size: Int): Array<Auth?> {
            return arrayOfNulls(size)
        }
    }
}