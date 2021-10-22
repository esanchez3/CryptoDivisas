package com.estiven.cryptodivisas.ui.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.estiven.cryptodivisas.R
import com.estiven.cryptodivisas.data.model.Currency
import com.estiven.cryptodivisas.databinding.TemplateCurrenciesBinding

class FavoritesAdapter(val onClickDelete: OnClickDelete) :
    RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    val list = mutableListOf<Currency>()

    inner class ViewHolder(private val binding: TemplateCurrenciesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(model: Currency, position: Int) {
            with(binding) {
                model.network.apply {
                    nameCurrency.text = this
                    abbreviature.text = this
                    abbreviature.isAllCaps = true
                }
                // show icon delete
                binding.deleteFavorite.isVisible = true
                when (position % 2) {
                    0 -> intoImage(
                        itemView.context,
                        R.drawable.ic_baseline_brightness_blue_1_24,
                        backgroundCurrency
                    )
                    1 -> intoImage(
                        itemView.context,
                        R.drawable.ic_baseline_brightness_purple_1_24,
                        backgroundCurrency
                    )
                    else -> intoImage(
                        itemView.context,
                        R.drawable.ic_baseline_brightness_purple_1_24,
                        backgroundCurrency
                    )
                }
                binding.deleteFavorite.setOnClickListener {
                    onClickDelete.onClick(
                        position,
                        model.id
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TemplateCurrenciesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun addData(listCurrency: List<Currency>) {
        list.clear()
        list.addAll(listCurrency)
        notifyDataSetChanged()
    }

    fun intoImage(context: Context, image: Int, into: ImageView) {
        Glide.with(context).load(image)
            .into(into)
    }

    interface OnClickDelete {
        fun onClick(position: Int, id: Int)
    }
}