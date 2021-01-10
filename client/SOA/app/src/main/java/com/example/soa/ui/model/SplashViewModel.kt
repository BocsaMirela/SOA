package com.example.soa.ui.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.soa.repository.IPreference
import com.example.soa.ui.model.base.BaseViewModel
import com.example.soa.util.Constants.KEY_ACCESS_TOKEN
import com.example.soa.util.Constants.KEY_USER
import com.example.soa.util.SingleLiveEvent

interface ISplashViewModel {
    val action: LiveData<SplashViewModel.Action>
}

class SplashViewModel(preference: IPreference) : BaseViewModel(),
    ISplashViewModel {

    override val action: SingleLiveEvent<Action> by lazy { SingleLiveEvent<Action>() }

    init {
        if (preference[KEY_ACCESS_TOKEN].isNullOrBlank() || preference[KEY_USER].isNullOrBlank()) {
            action.postValue(Action.LOGIN)
        } else {
            action.postValue(Action.PROGRAMS)
        }
    }

    enum class Action {
        LOGIN, PROGRAMS
    }
}

class SplashViewModelFactory(private val preference: IPreference) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(preference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}