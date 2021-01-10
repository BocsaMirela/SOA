package com.example.soa.ui.model

import com.example.soa.model.Product


interface IProgramItemViewModel {
    val title: String
    val description: String
    val brand: String
    val price: String
    fun onClick()
}

class ProductItemViewModel(private val product: Product, private val itemClick: (Product) -> Unit) :
        IProgramItemViewModel {

    override val title: String
        get() = product.title

    override val description: String
        get() = if (product.description.length > 100) product.description.substring(0..100) + " ..." else product.description

    override val brand: String
        get() = product.brand

    override val price: String
        get() = "${product.price} RON"

    override fun onClick() {
        itemClick.invoke(product)
    }
}