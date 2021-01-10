package com.example.soa.ui.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.soa.network.RetrofitException
import com.example.soa.network.toRetrofitException
import com.example.soa.repository.IDataRepository
import com.example.soa.repository.IPreference
import com.example.soa.ui.model.base.BaseViewModel
import com.example.soa.ui.model.base.INetworkViewModel
import com.example.soa.util.SingleLiveEvent
import io.reactivex.rxkotlin.subscribeBy


interface IProgramViewModel : INetworkViewModel<Void> {
    val items: LiveData<List<IProgramItemViewModel>>
    fun onLogOut()
}

class ProductsViewModel(
    repository: IDataRepository,
    private val preference: IPreference
) : BaseViewModel(), IProgramViewModel {

    override val items: MutableLiveData<List<IProgramItemViewModel>> by lazy { MutableLiveData<List<IProgramItemViewModel>>() }

    override val update: SingleLiveEvent<Void> by lazy { SingleLiveEvent<Void>() }

    override val progress: SingleLiveEvent<Boolean> by lazy { SingleLiveEvent<Boolean>() }

    override val error: SingleLiveEvent<RetrofitException> by lazy { SingleLiveEvent<RetrofitException>() }

    init {
        progress.postValue(true)
        repository.getProducts().subscribeBy(onSuccess = {
            progress.postValue(false)
            items.postValue(it.map { item -> ProductItemViewModel(item, {}) })
        }, onError = {
            progress.postValue(false)
            error.postValue(it.toRetrofitException())
        })
    }

    override fun onLogOut() {
        update.postValue(null)
    }
}

class ProductsViewModelFactory(
        private val repository: IDataRepository,
        private val preference: IPreference
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: java.lang.Class<T>): T {
        if (modelClass.isAssignableFrom(ProductsViewModel::class.java)) {
            return ProductsViewModel(repository, preference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}