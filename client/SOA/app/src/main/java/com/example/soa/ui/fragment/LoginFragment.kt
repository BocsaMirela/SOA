package com.example.soa.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.soa.R
import com.example.soa.ui.model.ILoginViewModel
import com.example.soa.ui.model.LoginViewModel
import com.example.soa.ui.model.LoginViewModelFactory
import com.example.soa.SoaApplication
import com.example.soa.databinding.LoginBinding
import com.example.soa.network.RetrofitException
import com.example.soa.network.client.AuthClient
import com.example.soa.network.client.SessionClient
import com.example.soa.ui.fragment.base.BaseFragment
import com.example.soa.ui.screen.ProductsScreen
import com.example.soa.util.toast
import javax.inject.Inject


class LoginFragment : BaseFragment() {

    @Inject
    internal lateinit var authClient: AuthClient

    @Inject
    internal lateinit var sessionClient: SessionClient

    private val viewModel: ILoginViewModel by lazy {
        ViewModelProvider(
            this,
            LoginViewModelFactory(authClient, sessionClient, preference)
        ).get(LoginViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        (context.applicationContext as SoaApplication).applicationComponent.inject(this)

        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            DataBindingUtil.inflate<LoginBinding>(inflater, R.layout.login, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.executePendingBindings()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.update.observe(this, Observer {
            startActivity(Intent(activity, ProductsScreen::class.java).apply {
                flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            })
        })

        viewModel.error.observe(this, Observer { _ ->
               requireActivity().toast(R.string.wrong_email_or_password)
        })
    }
}