package com.example.soa.ui.adapter.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseDiffAdapter<T, VH : BaseViewHolder<T>>(itemCallback: DiffUtil.ItemCallback<T>) : ListAdapter<T, VH>(itemCallback),
    IBindingAdapter {

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindData(getItem(position))
    }

    override fun bindItems(items: List<Any>) {
        submitList(items as List<T>)
    }

    public override fun getItem(position: Int): T {
        return super.getItem(position)
    }
}