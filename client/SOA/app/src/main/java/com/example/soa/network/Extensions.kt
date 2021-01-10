package com.example.soa.network


import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.example.soa.R
import com.example.soa.util.toast
import io.reactivex.exceptions.CompositeException
import retrofit2.HttpException
import java.io.IOException

fun <T : Throwable> T.toRetrofitException(): RetrofitException {
    return when (this) {
        is RetrofitException -> this
        is HttpException -> RetrofitException.httpError(this) // We had non-200 http error
        is IOException -> RetrofitException.networkError(this) // A network error happened
        is CompositeException -> this.exceptions.first().toRetrofitException()
        else -> RetrofitException.unexpectedError(this) // We don't know what happened.
    }
}

fun RetrofitException.toString(context: Context): String? {
    return when (kind) {
        RetrofitException.Kind.NETWORK -> context.getString(R.string.no_internet)
        RetrofitException.Kind.UNEXPECTED -> context.getString(R.string.general_network_error)
        else -> message
    }
}

fun Throwable.handleError(activity: Activity?) {
    printStackTrace()
    activity?.toast(toString())
}

fun Throwable.handleError(fragment: Fragment) {
    handleError(fragment.activity)
}
