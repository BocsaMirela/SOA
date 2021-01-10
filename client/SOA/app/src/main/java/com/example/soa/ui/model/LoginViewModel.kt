package com.example.soa.ui.model

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.soa.network.RetrofitException
import com.example.soa.network.client.AuthClient
import com.example.soa.network.client.SessionClient
import com.example.soa.network.toRetrofitException
import com.example.soa.repository.IPreference
import com.example.soa.ui.model.base.BaseViewModel
import com.example.soa.ui.model.base.INetworkViewModel
import com.example.soa.util.Constants.KEY_ACCESS_TOKEN
import com.example.soa.util.Constants.KEY_USER
import com.example.soa.util.SingleLiveEvent
import com.example.soa.util.toFlowable
import com.example.soa.util.toJson
import io.reactivex.rxkotlin.Flowables
import io.reactivex.rxkotlin.subscribeBy


interface ILoginViewModel : INetworkViewModel<Void> {
    val username: ObservableField<String>
    val password: ObservableField<String>
    val enabled: LiveData<Boolean>
    fun onSignIn()
}

class LoginViewModel(
    private val authClient: AuthClient,
    private val sessionClient: SessionClient,
    private val preference: IPreference
) : BaseViewModel(), ILoginViewModel {

    override val username: ObservableField<String> by lazy { ObservableField<String>() }

    override val password: ObservableField<String> by lazy { ObservableField<String>() }

    override val enabled: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    override val update: SingleLiveEvent<Void> by lazy { SingleLiveEvent<Void>() }

    override val progress: SingleLiveEvent<Boolean> by lazy { SingleLiveEvent<Boolean>() }

    override val error: SingleLiveEvent<RetrofitException> by lazy { SingleLiveEvent<RetrofitException>() }

    init {
        progress.postValue(false)
        enabled.postValue(false)

        Flowables.combineLatest(username.toFlowable(), password.toFlowable()).takeUntil(cleared)
            .subscribeBy(onNext = { (username, password) ->
                enabled.postValue(username.isNotEmpty() && password.isNotEmpty())
            })
    }

    override fun onSignIn() {
        progress.postValue(true)
        enabled.postValue(false)
        authClient.login(username.get()!!, password.get()!!).doOnSuccess { auth ->
            preference[KEY_ACCESS_TOKEN] = auth.accessToken
        }.flatMap { sessionClient.getUser() }
            .subscribeBy(onSuccess = {
                preference[KEY_USER] = it.toJson()
                progress.postValue(false)
                update.postValue(null)
            }, onError = {
                progress.postValue(false)
                enabled.postValue(true)
                error.postValue(it.toRetrofitException())
            })
    }
}

class LoginViewModelFactory(
    private val authClient: AuthClient,
    private val sessionClient: SessionClient,
    private val preference: IPreference
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(authClient, sessionClient, preference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}