package com.bellminp.diet.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bellminp.diet.databinding.ItemCalendarBinding
import com.bellminp.diet.ui.data.CalendarData
import com.bellminp.diet.ui.main.fragment.CalendarViewModel

class CalendarAdapter (private val viewModel : CalendarViewModel) : RecyclerView.Adapter<CalendarAdapter.CalendarHolder>(){

    var items = ArrayList<CalendarData>()
        @SuppressLint("NotifyDataSetChanged")
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarHolder {
        val binding = ItemCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    override fun getItemId(position: Int): Long {
        return items[position].id.toLong()
    }

    override fun onBindViewHolder(holder: CalendarHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class CalendarHolder(val binding: ItemCalendarBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(calendarData: CalendarData){
            binding.model = calendarData

            binding.layout.setOnClickListener {
                viewModel.calendarClick(calendarData)
            }
        }
    }
}