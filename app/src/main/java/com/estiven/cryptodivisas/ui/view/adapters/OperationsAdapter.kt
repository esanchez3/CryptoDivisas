package com.estiven.cryptodivisas.ui.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.estiven.cryptodivisas.data.model.Operations
import com.estiven.cryptodivisas.databinding.TemplateOperationsBinding
import com.estiven.cryptodivisas.utils.ConverterDate

class OperationsAdapter :
    RecyclerView.Adapter<OperationsAdapter.ViewHolder>() {

    private val list = mutableListOf<Operations>()

    inner class ViewHolder(private val binding: TemplateOperationsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(model: Operations) {
            with(binding) {
                textID.text = model.id.toString()
                textPrice.text = model.price
                textQty.text = model.qty
                textSide.text = model.side
                textTimestamp.text = ConverterDate.date(model.timestamp).toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TemplateOperationsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun addData(data: Collection<Operations>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

}