package com.example.soa.ui.adapter.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T> protected constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bindData(item: T)
}