package com.bellminp.diet.ui.main.fragment

import androidx.fragment.app.activityViewModels
import com.bellminp.diet.R
import com.bellminp.diet.databinding.FragmentCalendarBinding
import com.bellminp.diet.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalendarFragment : BaseFragment<FragmentCalendarBinding,CalendarViewModel>(R.layout.fragment_calendar) {
    override val viewModel by activityViewModels<CalendarViewModel>()

    override fun initBinding() {
        binding.vm = viewModel
    }
}