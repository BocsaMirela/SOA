package com.example.soa.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.soa.R
import com.example.soa.SoaApplication
import com.example.soa.databinding.OrderFragmentBinding
import com.example.soa.managers.IOrdersManager
import com.example.soa.repository.IDataRepository
import com.example.soa.ui.adapter.OrderAdapter
import com.example.soa.ui.fragment.base.BaseFragment
import com.example.soa.ui.model.IOrdersViewModel
import com.example.soa.ui.model.OrdersViewModel
import com.example.soa.ui.model.OrdersViewModelFactory
import javax.inject.Inject


class OrdersFragment : BaseFragment() {

    @Inject
    internal lateinit var repository: IDataRepository

    @Inject
    internal lateinit var ordersManager: IOrdersManager

    private val viewModel: IOrdersViewModel by lazy {
        val factory = OrdersViewModelFactory(repository, ordersManager, preference)
        ViewModelProvider(this, factory).get(OrdersViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        (context.applicationContext as SoaApplication).applicationComponent.inject(this)

        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ordersManager.socketConnect()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<OrderFragmentBinding>(
            inflater,
            R.layout.fragment_orders,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.items.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.items.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        binding.items.adapter = OrderAdapter(requireContext())
        binding.executePendingBindings()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.update.observe(viewLifecycleOwner, Observer {
            when (it) {
                null -> {
                    requireActivity().finish()
                }
                else -> {
//                    startActivity(Intent(activity, ClassScreen::class.java).putExtra(KEY_PROGRAM, it))
                }
            }
        })

        viewModel.pay.observe(this, Observer {
            Toast.makeText(requireContext(), "The payment is processed, please wait!", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        ordersManager.socketDisconnect()
    }
}