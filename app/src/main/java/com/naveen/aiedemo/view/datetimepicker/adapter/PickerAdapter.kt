package com.naveen.aiedemo.view.datetimepicker.adapter

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.naveen.aiedemo.view.datetimepicker.fragment.DatePickerFragment
import com.naveen.aiedemo.view.datetimepicker.fragment.TimePickerFragment
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

abstract class PickerAdapter(fm: FragmentManager, val initDate: Date? = null) : FragmentPagerAdapter(fm) {

    companion object {
        val DEFAULT_DATETIME_FORMAT = SimpleDateFormat("dd-MM-yyyy hh.mm aa", Locale.US)
    }

    protected var timePickerFragment: TimePickerFragment? = null
    protected var datePickerFragment: DatePickerFragment? = null

    abstract fun getTitle(position: Int): Int

    fun getTimestamp(dateFormat: DateFormat = DEFAULT_DATETIME_FORMAT): String {
        return dateFormat.format(getDate())
    }

    fun getDate(): Date {
        val cal = Calendar.getInstance()
        setDate(cal)
        setTime(cal)
        return cal.time
    }

    private fun setDate(calendar: Calendar) {
        val datePickerFragment = this.datePickerFragment ?: return
        calendar.set(datePickerFragment.year, datePickerFragment.month, datePickerFragment.dayOfMonth)
    }

    private fun setTime(calendar: Calendar) {
        val timePickerFragment = this.timePickerFragment ?: return
        calendar.set(Calendar.HOUR_OF_DAY, timePickerFragment.hours)
        calendar.set(Calendar.MINUTE, timePickerFragment.minutes)
    }
}
