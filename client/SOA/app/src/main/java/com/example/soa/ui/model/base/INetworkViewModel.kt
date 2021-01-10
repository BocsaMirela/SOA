package com.example.soa.ui.model.base

import androidx.lifecycle.LiveData
import com.example.soa.network.RetrofitException
import com.example.soa.util.SingleLiveEvent

interface INetworkViewModel<T> {
    val progress: LiveData<Boolean>
    val error: SingleLiveEvent<RetrofitException>
    val update: SingleLiveEvent<T>
}
