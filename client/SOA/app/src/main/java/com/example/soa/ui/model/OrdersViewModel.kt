package com.example.soa.ui.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.soa.managers.IOrdersManager
import com.example.soa.model.Order
import com.example.soa.model.User
import com.example.soa.network.RetrofitException
import com.example.soa.network.toRetrofitException
import com.example.soa.repository.IDataRepository
import com.example.soa.repository.IPreference
import com.example.soa.ui.model.base.BaseViewModel
import com.example.soa.ui.model.base.INetworkViewModel
import com.example.soa.util.Constants.KEY_USER
import com.example.soa.util.SingleLiveEvent
import com.example.soa.util.fromJson
import com.example.soa.util.replace
import io.reactivex.rxkotlin.subscribeBy

interface IOrdersViewModel : INetworkViewModel<Void> {
    val pay: LiveData<Boolean>
    val items: LiveData<List<IOrderItemViewModel>>
    fun onBack()
}

class OrdersViewModel(
    repository: IDataRepository,
    ordersManager: IOrdersManager,
    private val preference: IPreference
) : BaseViewModel(), IOrdersViewModel {

    override val pay: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    override val items: MutableLiveData<List<IOrderItemViewModel>> by lazy { MutableLiveData<List<IOrderItemViewModel>>() }

    override val update: SingleLiveEvent<Void> by lazy { SingleLiveEvent<Void>() }

    override val progress: SingleLiveEvent<Boolean> by lazy { SingleLiveEvent<Boolean>() }

    override val error: SingleLiveEvent<RetrofitException> by lazy { SingleLiveEvent<RetrofitException>() }

    private val user = preference[KEY_USER]!!.fromJson(User::class.java)

    private val data = mutableListOf<Order>()

    private val itemClick: (Order) -> Unit = { order ->
        progress.postValue(true)
        pay.postValue(true)
        repository.buyOrder(order).subscribeBy(onComplete = {
            progress.postValue(false)
        }, onError = {
            progress.postValue(false)
            error.postValue(it.toRetrofitException())
        })
    }

    init {
        progress.postValue(true)

        repository.getOrders(user).subscribeBy(onSuccess = {
            progress.postValue(false)

            data.clear()
            data.addAll(it)

            items.postValue(it.map { item -> OrderItemViewModel(item, itemClick) })
        }, onError = {
            progress.postValue(false)
            error.postValue(it.toRetrofitException())
        })

        ordersManager.orderStatus.takeUntil(cleared).subscribeBy(onNext = { order ->
            val old = data.find { it.id == order.id }
            old?.let {
                val index = data.indexOf(old)
                data.removeAt(index)
                data.add(index, order)

                items.postValue(data.map { item -> OrderItemViewModel(item, itemClick) })
            }
        })

    }

    override fun onBack() {
        update.postValue(null)
    }
}

class OrdersViewModelFactory(
    private val repository: IDataRepository,
    private val ordersManager: IOrdersManager,
    private val preference: IPreference
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: java.lang.Class<T>): T {
        if (modelClass.isAssignableFrom(OrdersViewModel::class.java)) {
            return OrdersViewModel(repository, ordersManager, preference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}