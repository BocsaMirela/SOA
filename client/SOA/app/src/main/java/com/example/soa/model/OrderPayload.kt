package com.example.soa.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class OrderPayload(
    @SerializedName("userId") val userId: String,
    @SerializedName("product") val product: Product,
    @SerializedName("amount") val amount: Int,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readParcelable<Product>(Product::class.java.classLoader)!!,
        parcel.readInt(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeParcelable(product, flags)
        parcel.writeInt(amount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderPayload> {
        override fun createFromParcel(parcel: Parcel): OrderPayload {
            return OrderPayload(parcel)
        }

        override fun newArray(size: Int): Array<OrderPayload?> {
            return arrayOfNulls(size)
        }
    }

}