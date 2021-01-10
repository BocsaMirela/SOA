package com.example.soa.ui.binding

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object ImageViewBindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["android:url", "android:placeHolder", "android:errorPlaceHolder"], requireAll = false)
    fun setImageUrl(view: ImageView, url: String?, placeHolder: Int?, errorPlaceHolder: Int?) {
        setImageUrl(view, url, placeHolder?.run { ContextCompat.getDrawable(view.context, placeHolder) },
            errorPlaceHolder?.run { ContextCompat.getDrawable(view.context, errorPlaceHolder) })
    }

    @JvmStatic
    @BindingAdapter(value = ["android:url", "android:placeHolder", "android:errorPlaceHolder"], requireAll = false)
    fun setImageUrl(view: ImageView, url: String?, placeHolder: Drawable?, errorPlaceHolder: Drawable?) {
        if (!url.isNullOrBlank()) {
            val requestOptions = if (view.scaleType == ImageView.ScaleType.CENTER_CROP) {
                RequestOptions().centerCrop()
            } else {
                RequestOptions().centerInside()
            }
            placeHolder?.let {
                requestOptions.fallback(it)
            }
            errorPlaceHolder?.let {
                requestOptions.error(it)
            }
            view.visibility = View.VISIBLE
            Glide.with(view.context).load(url).apply(requestOptions.dontAnimate()).into(view)
        } else if (placeHolder != null) {
            view.visibility = View.VISIBLE
            view.setImageDrawable(placeHolder)
        } else {
            view.visibility = View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter("android:image")
    fun setImageUrl(view: ImageView, @DrawableRes image: Int?) {
        if (image != null) {
            view.setImageResource(image)
        } else {
            view.setImageDrawable(null)
        }
    }

    @JvmStatic
    @BindingAdapter("android:tint")
    fun setColor(view: ImageView, @ColorRes color: Int?) {
        if (color != null) {
            view.setColorFilter(ContextCompat.getColor(view.context, color), PorterDuff.Mode.SRC_IN)
        } else {
            view.colorFilter = null
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["android:tintResource", "android:tintCondition"], requireAll = false)
    fun setColor(view: ImageView, @ColorInt color: Int, condition: Boolean = true) {
        if (condition) {
            view.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        } else {
            view.colorFilter = null
        }
    }
}