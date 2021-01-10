package com.example.soa.ui.binding

import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.soa.util.fromHtml

object TextViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("android:html")
    fun setHtml(view: TextView, text: String?) {
        text?.let {
            view.text = it.fromHtml()
        }
    }

    @JvmStatic
    @BindingAdapter("android:html")
    fun setHtml(view: TextView, text: Pair<String?, Int?>?) {
        text?.let { data ->
            data.first?.let {
                view.text = it.fromHtml()
            }
        }
    }

    @JvmStatic
    @BindingAdapter("android:color")
    fun setTextColor(view: TextView, @ColorRes color: Int?) {
        color?.let {
            view.setTextColor(ContextCompat.getColor(view.context, it))
        }
    }
}
