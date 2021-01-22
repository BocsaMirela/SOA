package com.example.soa.managers

import com.example.soa.dependencies.NetworkModule
import com.example.soa.model.Order
import com.example.soa.repository.IPreference
import com.example.soa.util.fromJson
import com.github.nkzawa.socketio.client.IO
import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor

interface IOrdersManager {
    val orderStatus: Flowable<Order>
    fun socketConnect()
    fun socketDisconnect()

}

class OrdersManager(private val preference: IPreference) : IOrdersManager {

    private val socket = IO.socket(NetworkModule.SOCKET_URL)

    private val statusProcessor by lazy { PublishProcessor.create<Order>() }

    override val orderStatus: Flowable<Order> = statusProcessor

    override fun socketConnect() {
        socket.connect()
        socket.on(STATUS_EVENT) {
            val order = (it.first().toString()).fromJson(Order::class.java)
            statusProcessor.onNext(order)
        }
    }

    override fun socketDisconnect() {
        socket.disconnect()
    }

    companion object {
        const val STATUS_EVENT = "orderStatusUpdated"
    }
}