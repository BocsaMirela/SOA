package com.example.soa.repository

import com.example.soa.model.Order
import com.example.soa.model.OrderPayload
import com.example.soa.model.Product
import com.example.soa.model.User
import com.example.soa.network.client.DataClient
import io.reactivex.Completable
import io.reactivex.Single

interface IDataRepository {
    fun getProducts(): Single<List<Product>>
    fun getOrders(): Single<List<Order>>
    fun createOrder(product: Product, user: User): Completable
    fun buyOrder(order: Order): Completable
}

class DataRepository(private val dataClient: DataClient) : IDataRepository {

    override fun getProducts(): Single<List<Product>> = dataClient.getItems()

    override fun getOrders(): Single<List<Order>> = dataClient.getOrders()

    override fun createOrder(product: Product, user: User): Completable {
        return dataClient.createOrder(OrderPayload(userId = user.id, product = product, amount = 1))
    }

    override fun buyOrder(order: Order): Completable {
        return dataClient.buyOrder(order.id)
    }
}
