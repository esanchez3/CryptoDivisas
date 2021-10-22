package com.estiven.cryptodivisas.ui.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.estiven.cryptodivisas.CurrenciesApplication
import com.estiven.cryptodivisas.data.model.Currency
import com.estiven.cryptodivisas.databinding.FragmentFavoritesBinding
import com.estiven.cryptodivisas.ui.view.adapters.FavoritesAdapter
import com.estiven.cryptodivisas.ui.viewmodel.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private lateinit var mContext: Context
    private lateinit var favoritesAdapter: FavoritesAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        setupUI()
        observerViewModel()
        listData()
        return binding.root
    }

    private fun observerViewModel() {
        favoritesViewModel.list.observe(viewLifecycleOwner, { list ->
            if (list.isNotEmpty()) {
                favoritesAdapter.addData(list)
            } else {
                binding.apply {
                    recycler.isVisible = false
                    listEmpty.isVisible = true
                }
            }
        })
    }

    private fun setupUI() {
        val decoration = DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL)
        favoritesAdapter = FavoritesAdapter(object : FavoritesAdapter.OnClickDelete {
            override fun onClick(position: Int, id: Int) {
                // eliminamos y notificamos el cambio a la lista
                deleteFavorite(id)
                favoritesAdapter.list.removeAt(position)
                favoritesAdapter.notifyItemRemoved(position)
                // cuando esa lista sea vacia mostramos el texto de listEmpty
                if (favoritesAdapter.list.size <= 0) {
                    binding.apply {
                        recycler.isVisible = false
                        listEmpty.isVisible = true
                    }
                }
            }
        })
        binding.recycler.apply {
            adapter = favoritesAdapter
            layoutManager = LinearLayoutManager(mContext)
            addItemDecoration(decoration)
        }
    }

    private fun deleteFavorite(position: Int) {
        lifecycleScope.launch {
            val data = Currency(id = position)
            CurrenciesApplication.database.currencyDao().delete(data)
        }
    }

    private fun listData() {
        favoritesViewModel.getFavorites()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}