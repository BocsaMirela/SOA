package com.example.soa.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.soa.R
import com.example.soa.ui.adapter.base.BindingBaseAdapter
import com.example.soa.ui.adapter.holder.OrderViewHolder
import com.example.soa.ui.model.IOrderItemViewModel

class OrderAdapter(private val context: Context) : BindingBaseAdapter<IOrderItemViewModel, OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.order_item, parent, false))
    }
}