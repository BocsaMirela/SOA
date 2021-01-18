package com.example.soa.ui.adapter.holder

import com.example.soa.databinding.OrderItemBinding
import com.example.soa.ui.adapter.base.BaseViewHolder
import com.example.soa.ui.model.IOrderItemViewModel

class OrderViewHolder(private val binding: OrderItemBinding) : BaseViewHolder<IOrderItemViewModel>(binding.root) {

    override fun bindData(item: IOrderItemViewModel) {
        binding.viewModel = item
        binding.executePendingBindings()
    }
}