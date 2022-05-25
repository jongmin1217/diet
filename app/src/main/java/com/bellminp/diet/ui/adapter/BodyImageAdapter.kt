package com.bellminp.diet.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bellminp.diet.databinding.ItemBodyImageBinding
import com.bellminp.diet.databinding.ItemFoodImageBinding
import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.domain.model.FoodData
import com.bellminp.diet.ui.base.BaseViewModel

class BodyImageAdapter(private val onClick : (dietData: DietData) -> Unit) : RecyclerView.Adapter<BodyImageAdapter.ViewHolder>(){

    var items = ArrayList<DietData>()
        @SuppressLint("NotifyDataSetChanged")
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBodyImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItems(value: ArrayList<DietData>) {
        items.addAll(value)
        notifyItemRangeInserted(itemCount - value.size, value.size)
    }

    override fun getItemId(position: Int): Long {
        return items[position].id
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(val binding: ItemBodyImageBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(dietData: DietData){
            binding.model = dietData

            binding.layout.setOnClickListener {
                onClick(dietData)
            }
        }
    }
}