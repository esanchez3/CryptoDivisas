package com.estiven.cryptodivisas.ui.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.estiven.cryptodivisas.R
import com.estiven.cryptodivisas.databinding.TemplateCurrenciesBinding
import com.estiven.cryptodivisas.data.model.Currencies

class CurrenciesAdapter(val onClickListener: OnClickListener) :
    RecyclerView.Adapter<CurrenciesAdapter.ViewHolder>() {

    private val list = mutableListOf<Currencies>()

    inner class ViewHolder(private val binding: TemplateCurrenciesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(model: Currencies, position: Int) {
            with(binding) {
                nameCurrency.text = model.fullName
                abbreviature.isAllCaps = true
                abbreviature.text = model.networks[0].network
                // cambiar color de fondo cada dos posiciones
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
            }
            // click item
            itemView.setOnClickListener { onClickListener.onClick(model, position) }
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
    fun addData(data: Collection<Currencies>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onClick(model: Currencies, position: Int)
    }

    fun intoImage(context: Context, image: Int, into: ImageView) {
        Glide.with(context).load(image)
            .into(into)
    }
}