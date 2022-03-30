package com.bellminp.diet.ui.main.fragment

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.*
import androidx.viewpager.widget.ViewPager
import com.bellminp.diet.R
import com.bellminp.diet.common.listener.SnapPagerScrollListener
import com.bellminp.diet.databinding.FragmentCalendarBinding
import com.bellminp.diet.ui.adapter.CalendarListAdapter
import com.bellminp.diet.ui.base.BaseFragment
import com.bellminp.diet.ui.data.CalendarData
import com.bellminp.diet.ui.data.ViewPagerCalendar
import com.bellminp.diet.ui.dialog.BottomMonthDialog
import com.bellminp.diet.ui.dialog.BottomMonthViewModel
import com.bellminp.diet.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import timber.log.Timber
import kotlin.math.floor

@DelicateCoroutinesApi
@AndroidEntryPoint
class CalendarFragment :
    BaseFragment<FragmentCalendarBinding, CalendarViewModel>(R.layout.fragment_calendar) {
    override val viewModel by activityViewModels<CalendarViewModel>()
    private val monthViewModel by activityViewModels<BottomMonthViewModel>()


    private val calendarListAdapter: CalendarListAdapter by lazy {
        CalendarListAdapter(viewModel,viewLifecycleOwner)
    }

    override fun initBinding() {
        binding.vm = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initAdapter()
        viewModel.initCalendar(Utils.getYear(), Utils.getMonth(), 0)
    }

    override fun initViewModelObserve() {
        with(viewModel) {
            initScroll.observe(viewLifecycleOwner, {
                binding.recyclerviewCalendar.scrollToPosition(1)
            })
        }

        with(monthViewModel) {
            monthSelect.observe(viewLifecycleOwner, { data ->
                binding.btnDate.text = String.format("%d.%s", data.year, Utils.dateText(data.month))
                viewModel.initCalendar(data.year, data.month, 0)
            })
        }
    }

    private fun initAdapter() {
        if (binding.recyclerviewCalendar.adapter == null) {
            if (!calendarListAdapter.hasObservers()) calendarListAdapter.setHasStableIds(true)

            binding.recyclerviewCalendar.layoutManager =
                LinearLayoutManager(
                    binding.recyclerviewCalendar.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            binding.recyclerviewCalendar.adapter = calendarListAdapter

            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(binding.recyclerviewCalendar)

            val animator = binding.recyclerviewCalendar.itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            val listener = SnapPagerScrollListener(
                pagerSnapHelper,
                SnapPagerScrollListener.ON_SCROLL,
                true
            ) { position ->
                val year = calendarListAdapter.differ.currentList[position].year
                val month = calendarListAdapter.differ.currentList[position].month
                if (position == 0) viewModel.initCalendar(
                    Utils.beforeYear(year, month),
                    Utils.beforeMonth(month),
                    1
                )
                if (position == calendarListAdapter.itemCount - 1) viewModel.initCalendar(
                    Utils.afterYear(
                        year,
                        month
                    ), Utils.afterMonth(month), 2
                )
                binding.btnDate.text = String.format("%d.%s", year, Utils.dateText(month))
            }

            binding.recyclerviewCalendar.addOnScrollListener(listener)

        }
    }

    private fun initListener() {
        binding.btnDate.setOnClickListener {
            BottomMonthDialog(binding.btnDate.text.toString()).show(parentFragmentManager, "month")
        }


    }

}