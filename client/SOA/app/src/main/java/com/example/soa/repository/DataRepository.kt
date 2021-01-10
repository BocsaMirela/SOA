package com.example.soa.repository

import com.example.soa.model.Product
import com.example.soa.network.client.DataClient
import io.reactivex.Single

interface IDataRepository {
    fun getProducts(): Single<List<Product>>
}

class DataRepository(private val dataClient: DataClient) : IDataRepository {

    override fun getProducts(): Single<List<Product>> = dataClient.getItems()
}
