package com.naveen.aiedemo.view.datetimepicker.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import android.view.ViewGroup
import com.naveen.aiedemo.R
import com.naveen.aiedemo.view.datetimepicker.fragment.DatePickerFragment
import com.naveen.aiedemo.view.datetimepicker.fragment.TimePickerFragment
import java.util.Date

open class DateTimePickerAdapter(fm: FragmentManager, initDate: Date? = null, val firstPickDate: Boolean = true) : PickerAdapter(fm, initDate) {

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        if (firstPickDate) {
            return when (position) {
                0 -> DatePickerFragment.newInstance(initDate)
                1 -> TimePickerFragment.newInstance(initDate)
                else -> DatePickerFragment.newInstance(initDate)
            }
        } else {
            return when (position) {
                0 -> TimePickerFragment.newInstance(initDate)
                1 -> DatePickerFragment.newInstance(initDate)
                else -> DatePickerFragment.newInstance(initDate)
            }
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val instance =  super.instantiateItem(container, position)
        if (firstPickDate) {
            when (position) {
                0 -> datePickerFragment = instance as DatePickerFragment
                1 -> timePickerFragment = instance as TimePickerFragment
            }
        } else {
            when (position) {
                0 -> timePickerFragment = instance as TimePickerFragment
                1 -> datePickerFragment = instance as DatePickerFragment
            }
        }
        return instance
    }

    override fun getTitle(position: Int): Int {
        if (firstPickDate) {
            return when (position) {
                0 -> R.string.datetimepicker_date
                else -> R.string.datetimepicker_time
            }
        } else {
            return when (position) {
                1 -> R.string.datetimepicker_date
                else -> R.string.datetimepicker_time
            }
        }

    }
}
