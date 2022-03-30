package com.bellminp.diet.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.*
import com.bellminp.diet.databinding.ItemCalendarBinding
import com.bellminp.diet.databinding.LayoutCalendarBinding
import com.bellminp.diet.ui.data.CalendarData
import com.bellminp.diet.ui.data.ViewPagerCalendar
import com.bellminp.diet.ui.main.fragment.CalendarViewModel
import com.bellminp.diet.utils.BindAdapter
import timber.log.Timber

class CalendarListAdapter(
    private val viewModel: CalendarViewModel,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<CalendarListAdapter.CalendarHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<ViewPagerCalendar>() {
        override fun areContentsTheSame(
            oldItem: ViewPagerCalendar,
            newItem: ViewPagerCalendar
        ): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(
            oldItem: ViewPagerCalendar,
            newItem: ViewPagerCalendar
        ): Boolean {
            return oldItem.getId() == newItem.getId()
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    init {
        with(viewModel) {
            initCalendar.observe(lifecycleOwner, { items ->
                differ.submitList(items)
                initScroll()
            })

            beforeAddCalendar.observe(lifecycleOwner, { item ->
                val list = differ.currentList.toMutableList()
                list.add(0, item)
                differ.submitList(list)
            })

            afterAddCalendar.observe(lifecycleOwner, { item ->
                val list = differ.currentList.toMutableList()
                list.add(item)
                differ.submitList(differ.currentList)
            })
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarHolder {
        val binding =
            LayoutCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun getItemId(position: Int): Long {
        return differ.currentList[position].getId()
    }

    override fun onBindViewHolder(holder: CalendarHolder, position: Int) {
        val viewPagerCalendar = differ.currentList[position]

        holder.binding.model = viewPagerCalendar

        if (!viewPagerCalendar.adapter.hasObservers()) viewPagerCalendar.adapter.setHasStableIds(
            true
        )

        holder.binding.recyclerviewCalendar.layoutManager =
            GridLayoutManager(holder.binding.recyclerviewCalendar.context, 7)
        holder.binding.recyclerviewCalendar.adapter = viewPagerCalendar.adapter


        val animator = holder.binding.recyclerviewCalendar.itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }

        viewPagerCalendar.adapter.items = viewPagerCalendar.data
    }

    inner class CalendarHolder(val binding: LayoutCalendarBinding) :
        RecyclerView.ViewHolder(binding.root)
}