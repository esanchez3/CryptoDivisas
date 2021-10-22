package com.estiven.cryptodivisas.ui.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.estiven.cryptodivisas.R
import com.estiven.cryptodivisas.data.model.Currencies
import com.estiven.cryptodivisas.databinding.FragmentHomeBinding
import com.estiven.cryptodivisas.ui.view.adapters.CurrenciesAdapter
import com.estiven.cryptodivisas.ui.viewmodel.CurrenciesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val currenciesViewModel: CurrenciesViewModel by viewModels()
    private lateinit var mContext: Context
    private lateinit var currenciesAdapter: CurrenciesAdapter
    private lateinit var keyPrimary: String
    private var positionItem = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.swipeRefresh.setOnRefreshListener { showInfo() }
        setHasOptionsMenu(true)
        setupUI()
        observerViewModel()
        showInfo()
        return binding.root
    }

    private fun setupUI() {
        val decoration = DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL)
        currenciesAdapter = CurrenciesAdapter(object : CurrenciesAdapter.OnClickListener {
            override fun onClick(model: Currencies, position: Int) {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToCurrenciesDetailsFragment(
                        model.fullName,
                        model.networks[0].network,
                        position,
                        model.networks[0].protocol,
                        model.networks[0].default,
                        model.networks[0].payinEnabled,
                        model.networks[0].payoutEnabled,
                        model.networks[0].precisionPayout,
                        model.networks[0].payoutFee,
                        model.networks[0].payoutIsPaymentId,
                        model.networks[0].payinPaymentId,
                        model.networks[0].payinConfirmations,
                        model.networks[0].lowProcessingTime,
                        model.networks[0].highProcessingTime,
                        model.networks[0].avgProcessingTime,
                        keyPrimary
                    )
                )
                positionItem = position
            }
        })
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = currenciesAdapter
            addItemDecoration(decoration)
        }
    }

    private fun showInfo() {
        currenciesViewModel.getCurrencies(positionItem)
    }

    private fun observerViewModel() {
        // Start a coroutine in the lifecycle scope
        currenciesViewModel.apply {
            loading.observe(viewLifecycleOwner, { loading ->
                binding.progress.isVisible = loading
            })
            message.observe(viewLifecycleOwner, { message ->
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
            })
            refreshLayout.observe(viewLifecycleOwner, { loading ->
                binding.swipeRefresh.isRefreshing = loading
            })
            key.observe(viewLifecycleOwner, { key ->
                keyPrimary = key
            })
            list.observe(viewLifecycleOwner, { list ->
                if (!list.isEmpty()) {
                    currenciesAdapter.addData(list)
                } else {
                    Toast.makeText(mContext, "Empty Value", Toast.LENGTH_SHORT).show()
                }
            })

        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.view_favorites, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.viewFavorites -> findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToFavoritesFragment())
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}