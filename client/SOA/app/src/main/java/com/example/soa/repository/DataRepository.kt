package com.example.soa.repository

import com.example.soa.model.Order
import com.example.soa.model.OrderPayload
import com.example.soa.model.Product
import com.example.soa.model.User
import com.example.soa.network.client.DataClient
import com.example.soa.network.client.OrdersClient
import io.reactivex.Completable
import io.reactivex.Single

interface IDataRepository {
    fun getProducts(): Single<List<Product>>
    fun getOrders(user: User): Single<List<Order>>
    fun createOrder(product: Product, user: User): Completable
    fun buyOrder(order: Order): Completable
    fun setFCMToken(token: String): Completable
}

class DataRepository(private val dataClient: DataClient, private val ordersClient: OrdersClient) : IDataRepository {

    override fun getProducts(): Single<List<Product>> = dataClient.getItems()

    override fun getOrders(user: User): Single<List<Order>> = ordersClient.getOrders(user.id)

    override fun createOrder(product: Product, user: User): Completable {
        return ordersClient.createOrder(OrderPayload(userId = user.id, product = product, amount = 1))
    }

    override fun buyOrder(order: Order): Completable {
        return ordersClient.buyOrder(order.id)
    }

    override fun setFCMToken(token: String): Completable {
        return dataClient.setFCMToken(token)
    }
}
