package com.example.soa.ui.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.soa.model.Product
import com.example.soa.model.User
import com.example.soa.network.RetrofitException
import com.example.soa.network.toRetrofitException
import com.example.soa.repository.IDataRepository
import com.example.soa.repository.IPreference
import com.example.soa.ui.model.base.BaseViewModel
import com.example.soa.ui.model.base.INetworkViewModel
import com.example.soa.util.Constants.KEY_TOKEN
import com.example.soa.util.Constants.KEY_USER
import com.example.soa.util.SingleLiveEvent
import com.example.soa.util.fromJson
import com.example.soa.util.removeCredentials
import com.example.soa.util.retrieveToken
import com.google.firebase.messaging.FirebaseMessaging
import io.reactivex.rxkotlin.subscribeBy


interface IProductsViewModel : INetworkViewModel<ProductsViewModel.Action> {
    val items: LiveData<List<IProgramItemViewModel>>
    val size: LiveData<String>
    fun onOrders()
    fun onLogout()
}

class ProductsViewModel(
    repository: IDataRepository,
    private val preference: IPreference
) : BaseViewModel(), IProductsViewModel {

    override val items: MutableLiveData<List<IProgramItemViewModel>> by lazy { MutableLiveData<List<IProgramItemViewModel>>() }

    override val size: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    override val update: SingleLiveEvent<Action> by lazy { SingleLiveEvent<Action>() }

    override val progress: SingleLiveEvent<Boolean> by lazy { SingleLiveEvent<Boolean>() }

    override val error: SingleLiveEvent<RetrofitException> by lazy { SingleLiveEvent<RetrofitException>() }

    private val user = preference[KEY_USER]!!.fromJson(User::class.java)

    private val itemClick: (Product) -> Unit = { product ->
        progress.postValue(true)
        repository.createOrder(product, user).subscribeBy(onComplete = {
            progress.postValue(false)
            size.postValue((size.value?.toInt() ?: ZERO).inc().toString())
        }, onError = {
            progress.postValue(false)
            error.postValue(it.toRetrofitException())
        })
    }

    init {
        progress.postValue(true)

        if (preference[KEY_TOKEN, false]) {
            FirebaseMessaging.getInstance().retrieveToken().flatMapCompletable { token -> repository.setFCMToken(token) }.subscribeBy(onComplete = {
                preference[KEY_TOKEN] = true
            }, onError = {
                error.postValue(it.toRetrofitException())
            })
        }

        repository.getOrders(user).subscribeBy(onSuccess = {
            size.postValue(it.size.toString())
        }, onError = {
            size.postValue(ZERO.toString())
        })

        repository.getProducts().subscribeBy(onSuccess = {
            progress.postValue(false)
            items.postValue(it.map { item -> ProductItemViewModel(item, itemClick) })
        }, onError = {
            progress.postValue(false)
            error.postValue(it.toRetrofitException())
        })
    }

    override fun onOrders() {
        update.postValue(Action.ORDERS)
    }

    override fun onLogout() {
        preference.removeCredentials()
        update.postValue(null)
    }

    companion object {
        private const val ZERO = 0
    }

    enum class Action {
        ORDERS
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