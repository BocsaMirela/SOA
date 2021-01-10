package com.example.soa.ui.adapter.holder

import com.example.soa.databinding.ProductItemBinding
import com.example.soa.ui.adapter.base.BaseViewHolder
import com.example.soa.ui.model.IProgramItemViewModel

class ProductViewHolder(private val binding: ProductItemBinding) : BaseViewHolder<IProgramItemViewModel>(binding.root) {

    override fun bindData(item: IProgramItemViewModel) {
        binding.viewModel = item
        binding.executePendingBindings()
    }
}