package com.example.soa.util

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.Html
import android.text.Spanned
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.soa.R
import com.example.soa.ui.screen.SplashScreen
import com.example.soa.util.Constants.NOTIFICATION_CODE
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.subjects.SingleSubject

fun Context.toast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Context.toast(text: Int) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Any.toJson(): String {
    return Gson().toJson(this)
}

fun <T> String.fromJson(clazz: Class<T>): T {
    return Gson().fromJson(this, clazz)
}

fun String.fromHtml(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(this)
    }
}

fun <V : Parcelable> Parcel.writeParcelableMap(map: Map<String, V>, flags: Int) {
    this.writeInt(map.size)
    for ((key, value) in map) {
        this.writeString(key)
        this.writeParcelable(value, flags)
    }
}

fun <V : Parcelable> Parcel.readParcelableMap(vClass: Class<V>): Map<String, V> {
    val size: Int = this.readInt()
    val map: MutableMap<String, V> = mutableMapOf()
    for (i in 0 until size) {
        map[this.readString()!!] = vClass.cast(this.readParcelable(vClass.classLoader))!!
    }
    return map
}

fun <T> ObservableField<T>.toFlowable(): Flowable<T> {
    val subject = BehaviorProcessor.create<T>()
    val callback = object : Observable.OnPropertyChangedCallback() {

        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            get()?.let {
                subject.onNext(it)
            }
        }

    }
    get()?.let {
        subject.onNext(it)
    }
    return subject.doOnSubscribe { addOnPropertyChangedCallback(callback) }
        .doAfterTerminate { removeOnPropertyChangedCallback(callback) }
}

fun <T> LiveData<T>.toFlowable(default: T? = null): Flowable<T> {
    val subject = BehaviorProcessor.create<T>()
    val callback = Observer<T> {
        value?.let {
            subject.onNext(it)
        }
    }
    if (value != null) {
        subject.onNext(value!!)
    } else if (default != null) {
        subject.onNext(default)
    }
    return subject.doOnSubscribe { observeForever(callback) }
        .doAfterTerminate { removeObserver(callback) }
}

fun FirebaseMessaging.retrieveToken(): Single<String> {
    val subject = SingleSubject.create<String>()
    return subject.doOnSubscribe {
        token.addOnCompleteListener { subject.onSuccess(it.result) }
            .addOnCanceledListener { subject.onError(Throwable()) }
    }
}

fun <E> Iterable<E>.replace(old: E, new: E) = map { if (it == old) new else it }

fun Context.showNotification(title: String?, message: String?, priority: Int, data: Bundle? = null) {
    val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val builder = NotificationCompat.Builder(this).apply {
        setSmallIcon(R.drawable.ic__logo)
        setContentTitle(title)
        setContentText(message)
        setSound(defaultSoundUri)
        setAutoCancel(true)
        setPriority(priority)
    }

    val notificationIntent = Intent(this, SplashScreen::class.java)
    notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
    if (data != null) {
        notificationIntent.putExtras(data)
    }
    builder.setContentIntent(PendingIntent.getActivity(this, NOTIFICATION_CODE, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT))

    // Add as notification
    val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager? ?: return
    manager.notify(NOTIFICATION_CODE, builder.build())
}