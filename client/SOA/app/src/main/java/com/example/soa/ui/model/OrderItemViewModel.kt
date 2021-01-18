package com.example.soa.ui.model

import com.example.soa.R
import com.example.soa.model.Order
import com.example.soa.model.Status
import com.example.soa.util.Constants.FORMAT
import com.example.soa.util.Constants.SHORT_FORMAT
import java.text.SimpleDateFormat
import java.util.*

interface IOrderItemViewModel {
    val title: String
    val price: String
    val status: String
    val created: String
    val color: Int
    val visible: Boolean
    fun onClick()
}

class OrderItemViewModel(private val order: Order, private val itemClick: (Order) -> Unit) :
    IOrderItemViewModel {

    override val title: String
        get() = order.product.title + " " + order.product.brand

    override val price: String
        get() = "${order.product.price} RON"

    override val status: String
        get() = order.status.name

    override val created: String
        get() = "Created at: ${SimpleDateFormat(SHORT_FORMAT, Locale.US).format(order.created)}"

    override val color: Int
        get() = if (order.status == Status.CANCELED) R.color.faded_red else R.color.gray

    override val visible: Boolean
        get() = order.status == Status.CREATED || order.status == Status.CANCELED

    override fun onClick() {
        itemClick.invoke(order)
    }
}