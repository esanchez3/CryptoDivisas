package com.estiven.cryptodivisas.ui.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.estiven.cryptodivisas.CurrenciesApplication
import com.estiven.cryptodivisas.R
import com.estiven.cryptodivisas.data.model.Currency
import com.estiven.cryptodivisas.databinding.FragmentCurrenciesDetailsBinding
import com.estiven.cryptodivisas.databinding.LayoutOperationsBinding
import com.estiven.cryptodivisas.ui.view.adapters.OperationsAdapter
import com.estiven.cryptodivisas.ui.viewmodel.RequestOperationsViewModel
import com.estiven.cryptodivisas.utils.ShowMessage
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.Exception

@AndroidEntryPoint
class CurrenciesDetailsFragment : Fragment() {

    private var _binding: FragmentCurrenciesDetailsBinding? = null
    private val binding get() = _binding!!
    private var checkedFavorite = false
    private val args: CurrenciesDetailsFragmentArgs by navArgs()
    private val requestOperationsViewModel: RequestOperationsViewModel by viewModels()
    private lateinit var mContext: Context
    private var optionSelected = ""
    private lateinit var operationsAdapter: OperationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCurrenciesDetailsBinding.inflate(inflater, container, false)
        // enabled icons appbar
        setHasOptionsMenu(true)
        // change title appbar
        (activity as AppCompatActivity).supportActionBar?.title = args.fullName
        with(binding) {
            textNetwork.text = args.network
            if (!args.protocol.isNullOrEmpty()) {
                textProtocol.text = args.protocol
            } else {
                textProtocol.text = "N/A"
            }
            textDefault.text = args.default.toString()
            textPayinEnabled.text = args.payinEnabled.toString()
            textPayoutEnabled.text = args.payoutEnabled.toString()
            textPrecisionPayout.text = args.precisionPayout.toString()
            textPayoutFee.text = args.payoutFee.toString()
            textPayoutIsPaymentID.text = args.payoutIsPaymentId.toString()
            textPayinPaymentID.text = args.payinPaymentId.toString()
            textPayinConfirmations.text = args.payinConfirmations.toString()
            textLowProcessingTime.text = args.lowProcessingTime.toString()
            textHighProcessingTime.text = args.highProcessingTime.toString()
            avgProcessingTime.text = args.avgProcessingTime.toString()
            requestOperations.setOnClickListener { dialogInfo() }

        }
        return binding.root
    }

    private fun observerViewModel(templateOperationsBinding: LayoutOperationsBinding) {
        requestOperationsViewModel.apply {
            loading.observe(viewLifecycleOwner, { loading ->
                templateOperationsBinding.progress.isVisible = loading
            })
            message.observe(viewLifecycleOwner, { message ->
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
            })
            list.observe(viewLifecycleOwner, { list ->
                if (!list.isEmpty()) {
                    operationsAdapter.addData(list)
                } else {
                    Toast.makeText(mContext, "Empty Value", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun showOperations() {
        requestOperationsViewModel.requestOperations("${args.key}$optionSelected", 1)
    }

    private fun dialogInfo() {
        val items = listOf("BTC", "USDT")
        val adapter = ArrayAdapter(mContext, R.layout.list_item, items)
        val dialog = MaterialAlertDialogBuilder(mContext)
        val bindingTheme = LayoutOperationsBinding.inflate(layoutInflater, null, false)
        dialog.setView(bindingTheme.root)
        val autoComplete = (bindingTheme.search)
        autoComplete.setAdapter(adapter)
        autoComplete.setOnItemClickListener { adapterView, _, i, _ ->
            val selected = adapterView.getItemAtPosition(i)
            optionSelected = selected as String
            setupShowData(bindingTheme)
            observerViewModel(bindingTheme)
            showOperations()
        }
        dialog.show()
    }

    private fun setupShowData(binding: LayoutOperationsBinding) {
        val decoration = DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL)
        operationsAdapter = OperationsAdapter()
        binding.recyclerView.apply {
            adapter = operationsAdapter
            layoutManager = LinearLayoutManager(mContext)
            addItemDecoration(decoration)
        }
    }

    private fun favoriteIcon(menuItem: MenuItem) {
        lifecycleScope.launch {
            val data = CurrenciesApplication.database.currencyDao().getFavorite(args.idCurrency)
            var icon: Int
            try {
                if (data.favorite) {
                    checkedFavorite = true
                    icon = R.drawable.ic_baseline_favorite_24
                } else {
                    checkedFavorite = false
                    icon = R.drawable.ic_baseline_favorite_border_24
                }
            } catch (e: Exception) {
                checkedFavorite = false
                icon = R.drawable.ic_baseline_favorite_border_24
            }
            menuItem.icon = ContextCompat.getDrawable(mContext, icon)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_favorite, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val addFavorite = menu.findItem(R.id.addFavorite)
        favoriteIcon(addFavorite)
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // change checked item
            R.id.addFavorite -> {
                checkedFavorite = if (checkedFavorite) {
                    ShowMessage.show(requireView(), getString(R.string.text_delete_favorite))
                    deleteFavorite()
                    false
                } else {
                    ShowMessage.show(requireView(), getString(R.string.text_add_favorite))
                    addFavorite()
                    true
                }
                favoriteIcon(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteFavorite() {
        lifecycleScope.launch {
            val data = Currency(id = args.idCurrency)
            CurrenciesApplication.database.currencyDao().delete(data)
        }
    }

    private fun addFavorite() {
        lifecycleScope.launch {
            val data = Currency(network = args.network, id = args.idCurrency, favorite = true)
            CurrenciesApplication.database.currencyDao().insert(data)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }
}