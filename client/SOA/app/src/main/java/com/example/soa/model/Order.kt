package com.example.soa.model

import android.os.Parcel
import android.os.Parcelable
import com.example.soa.model.adapter.DateAdapter
import com.example.soa.model.adapter.StatusAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.util.*

data class Order(
    @SerializedName("_id") val id: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("amount") val amount: Int,
    @SerializedName("status") @JsonAdapter(StatusAdapter::class) val status: Status,
    @SerializedName("transactionId") val brand: String?,
    @SerializedName("product") val product: Product,
    @SerializedName("createdAt") @JsonAdapter(DateAdapter::class) val created: Date
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        Status.values()[parcel.readInt()],
        parcel.readString(),
        parcel.readParcelable<Product>(Product::class.java.classLoader)!!,
        parcel.readSerializable() as Date
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(userId)
        parcel.writeInt(amount)
        parcel.writeInt(status.ordinal)
        parcel.writeString(brand)
        parcel.writeParcelable(product, flags)
        parcel.writeSerializable(created)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Order> {
        override fun createFromParcel(parcel: Parcel): Order {
            return Order(parcel)
        }

        override fun newArray(size: Int): Array<Order?> {
            return arrayOfNulls(size)
        }
    }

}

enum class Status(val label: String) {
    CREATED("created"), CONFIRMED("confirmed"), DELIVERED("delivered"), CANCELED("canceled")
}