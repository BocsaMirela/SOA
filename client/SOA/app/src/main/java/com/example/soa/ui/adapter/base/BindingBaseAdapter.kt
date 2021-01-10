package com.example.soa.ui.adapter.base

abstract class BindingBaseAdapter<T, VH : BaseViewHolder<T>> : BaseAdapter<T, VH>(),
    IBindingAdapter {

    override fun bindItems(items: List<Any>) {
        this.items = items.toMutableList() as MutableList<T>
    }
}
