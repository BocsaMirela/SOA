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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.soa.R
import com.example.soa.SoaApplication
import com.example.soa.databinding.ProductFragmentBinding
import com.example.soa.network.client.DataClient
import com.example.soa.repository.IDataRepository
import com.example.soa.ui.adapter.ProductAdapter
import com.example.soa.ui.fragment.base.BaseFragment
import com.example.soa.ui.model.IProductsViewModel
import com.example.soa.ui.model.ProductsViewModel
import com.example.soa.ui.model.ProductsViewModelFactory
import com.example.soa.ui.screen.LoginScreen
import com.example.soa.ui.screen.OrdersScreen
import com.google.android.gms.tasks.Task
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.google.firebase.messaging.FirebaseMessaging
import javax.inject.Inject


class ProductsFragment : BaseFragment() {

    @Inject
    internal lateinit var client: DataClient

    @Inject
    internal lateinit var repository: IDataRepository

    private val viewModel: IProductsViewModel by lazy {
        val factory = ProductsViewModelFactory(repository, preference)
        ViewModelProvider(this, factory).get(ProductsViewModel::class.java)
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
        val binding = DataBindingUtil.inflate<ProductFragmentBinding>(
                inflater,
                R.layout.fragment_product,
                container,
                false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.items.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.items.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        binding.items.adapter = ProductAdapter(requireContext())
        binding.executePendingBindings()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.update.observe(viewLifecycleOwner, Observer {
            when (it) {
                ProductsViewModel.Action.ORDERS -> {
                    startActivity(Intent(activity, OrdersScreen::class.java))
                }
                else -> {
                    startActivity(Intent(activity, LoginScreen::class.java))
                    requireActivity().finish()
                }
            }
        })
    }
}