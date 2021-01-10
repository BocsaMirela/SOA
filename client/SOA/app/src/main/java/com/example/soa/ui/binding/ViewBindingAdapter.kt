package com.example.soa.ui.binding

import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.databinding.BindingAdapter


object ViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("android:insetPadding")
    fun setInsetPadding(view: View, fullScreen: Boolean) {
        if (fullScreen && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ViewCompat.setOnApplyWindowInsetsListener(view) { container, insets ->
                container.setPadding(
                    container.paddingLeft, container.paddingTop + insets.systemWindowInsetTop, container.paddingRight,
                    container.paddingBottom + insets.systemWindowInsetBottom
                )
                container.setOnApplyWindowInsetsListener(null)
                insets
            }
        }
    }

    @JvmStatic
    @BindingAdapter("android:visible")
    fun setVisible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("android:invisible")
    fun setInvisible(view: View, invisible: Boolean) {
        view.visibility = if (invisible) View.INVISIBLE else View.VISIBLE
    }

    @JvmStatic
    @BindingAdapter("android:enabled")
    fun setEnabled(view: View, enabled: Boolean) {
        view.isEnabled = enabled
        view.isClickable = enabled
    }

    @JvmStatic
    @BindingAdapter("android:background")
    fun setBackground(view: View, @ColorRes color: Int) {
        view.setBackgroundResource(color)
    }

    @JvmStatic
    @BindingAdapter(value = ["android:backgroundStrokeColor", "android:backgroundStrokeWidth"], requireAll = true)
    fun setBackgroundStroke(view: View, @ColorRes color: Int, width: Float = view.resources.displayMetrics.density) {
        (view.background as? GradientDrawable)?.apply {
            setStroke(width.toInt(), ContextCompat.getColor(view.context, color))
        }
    }

    @JvmStatic
    @BindingAdapter("android:backgroundTint")
    fun setBackgroundTint(view: View, color: Int) {
        view.backgroundTintList = ContextCompat.getColorStateList(view.context, color)
    }

    @JvmStatic
    @BindingAdapter("android:layout_width")
    fun setLayoutWidth(view: View, width: Float) {
        view.layoutParams = view.layoutParams.apply { this.width = width.toInt() }
    }
}