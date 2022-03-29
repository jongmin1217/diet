package com.bellminp.diet.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.bellminp.diet.databinding.ItemCalendarBinding
import com.bellminp.diet.databinding.LayoutCalendarBinding
import com.bellminp.diet.ui.data.CalendarData
import com.bellminp.diet.ui.data.ViewPagerCalendar
import com.bellminp.diet.ui.main.fragment.CalendarViewModel
import com.bellminp.diet.utils.BindAdapter
import timber.log.Timber

class CalendarListAdapter(private val viewModel : CalendarViewModel) : RecyclerView.Adapter<CalendarListAdapter.CalendarHolder>(){

    var items = ArrayList<ViewPagerCalendar>()
        @SuppressLint("NotifyDataSetChanged")
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarHolder {
        val binding = LayoutCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun beforeAddItem(value : ViewPagerCalendar){
        items.add(0,value)
        notifyItemInserted(0)
    }

    fun afterAddItem(value : ViewPagerCalendar){
        items.add(value)
        notifyItemInserted(itemCount-1)
    }


    override fun getItemId(position: Int): Long {
        return items[position].getId()
    }

    override fun onBindViewHolder(holder: CalendarHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class CalendarHolder(val binding: LayoutCalendarBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(viewPagerCalendar: ViewPagerCalendar){
            binding.model = viewPagerCalendar

            if(!viewPagerCalendar.adapter.hasObservers()) viewPagerCalendar.adapter.setHasStableIds(true)

            binding.recyclerviewCalendar.layoutManager =
                GridLayoutManager(binding.recyclerviewCalendar.context, 7)
            binding.recyclerviewCalendar.adapter = viewPagerCalendar.adapter


            val animator = binding.recyclerviewCalendar.itemAnimator
            if(animator is SimpleItemAnimator){
                animator.supportsChangeAnimations = false
            }

            viewPagerCalendar.adapter.items = viewPagerCalendar.data
        }
    }
}