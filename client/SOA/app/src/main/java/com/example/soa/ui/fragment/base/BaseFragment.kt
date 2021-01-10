package com.example.soa.ui.fragment.base

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.soa.SoaApplication
import com.example.soa.network.handleError
import com.example.soa.repository.IPreference
import com.example.soa.ui.model.base.INetworkViewModel
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var preference: IPreference

    open val networkViewModel: INetworkViewModel<*>? = null

    override fun onAttach(context: Context) {
        (context.applicationContext as SoaApplication).applicationComponent.inject(this)

        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        networkViewModel?.let {
            it.error.observe(viewLifecycleOwner, Observer { throwable ->
                throwable!!.handleError(this)
            })
        }
    }
}
