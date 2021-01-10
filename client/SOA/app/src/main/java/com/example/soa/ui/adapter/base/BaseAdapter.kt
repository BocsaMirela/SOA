package com.example.soa.ui.adapter.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VH : BaseViewHolder<T>> : RecyclerView.Adapter<VH>() {

    protected var items: MutableList<T>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindData(items!![position])
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    protected fun getItem(position: Int): T {
        return items!![position]
    }
}
