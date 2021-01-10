package com.example.soa.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.soa.ui.adapter.base.BindingBaseAdapter
import com.example.soa.ui.adapter.holder.ProductViewHolder
import com.example.soa.ui.model.IProgramItemViewModel
import com.example.soa.R

class ProductAdapter(private val context: Context) : BindingBaseAdapter<IProgramItemViewModel, ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.product_item, parent, false))
    }
}