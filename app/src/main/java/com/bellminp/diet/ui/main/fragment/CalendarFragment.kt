package com.bellminp.diet.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.*
import com.bellminp.diet.R
import com.bellminp.diet.databinding.FragmentCalendarBinding
import com.bellminp.diet.ui.adapter.CalendarAdapter
import com.bellminp.diet.ui.base.BaseFragment
import com.bellminp.diet.ui.dialog.month.BottomMonthDialog
import com.bellminp.diet.ui.dialog.month.BottomMonthViewModel
import com.bellminp.diet.ui.write_type.WriteTypeActivity
import com.bellminp.diet.utils.Constants
import com.bellminp.diet.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi

@AndroidEntryPoint
class CalendarFragment :
    BaseFragment<FragmentCalendarBinding, CalendarViewModel>(R.layout.fragment_calendar) {
    override val viewModel by activityViewModels<CalendarViewModel>()
    private val monthViewModel by activityViewModels<BottomMonthViewModel>()


    override fun initBinding() {
        binding.vm = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initValue()
        initListener()
        initAdapter()
        viewModel.initCalendar(Utils.getYear(), Utils.getMonth())
        viewModel.initLastWeight()
        viewModel.initProfile()
    }

    override fun initViewModelObserve() {
        with(viewModel) {
            initCalendar.observe(viewLifecycleOwner,{ items ->
                binding.recyclerviewCalendar.adapter?.let { adapter ->
                    if(adapter is CalendarAdapter){
                        adapter.items = items
                    }
                }
            })

            goWriteType.observe(viewLifecycleOwner, { data ->
                val intent = Intent(context, WriteTypeActivity::class.java)
                intent.putExtra(Constants.DATA,data)
                startActivity(intent)
            })
        }

        with(monthViewModel) {
            monthSelect.observe(viewLifecycleOwner, { data ->
                binding.btnDate.text = String.format("%d.%s", data.year, Utils.dateText(data.month))
                viewModel.initCalendar(data.year, data.month)
            })
        }
    }

    private fun initAdapter() {
        if (binding.recyclerviewCalendar.adapter == null) {
            val adapter = CalendarAdapter(viewModel)
            if (!adapter.hasObservers()) adapter.setHasStableIds(true)

            binding.recyclerviewCalendar.layoutManager =
                GridLayoutManager(binding.recyclerviewCalendar.context, 7)
            binding.recyclerviewCalendar.adapter = adapter


            val animator = binding.recyclerviewCalendar.itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }
        }
    }

    private fun initValue(){
        binding.btnDate.text = String.format("%d.%s",Utils.getYear(),Utils.dateText(Utils.getMonth()))
    }

    private fun initListener() {
        binding.btnDate.setOnClickListener {
            BottomMonthDialog(binding.btnDate.text.toString()).show(parentFragmentManager, "month")
        }
    }

    override fun onDestroy() {
        viewModel.clearDietDataObserve()
        super.onDestroy()
    }

}