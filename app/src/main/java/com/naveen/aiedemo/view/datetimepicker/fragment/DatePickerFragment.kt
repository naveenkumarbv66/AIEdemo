package com.naveen.aiedemo.view.datetimepicker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.naveen.aiedemo.R
import kotlinx.android.synthetic.main.fragment_datepicker.*
import java.util.*

class DatePickerFragment : androidx.fragment.app.Fragment() {

    companion object {
        private const val ARG_INIT_DATETIME = "ARG_INIT_DATETIME"

        fun newInstance(initDatetime: Date? = null): DatePickerFragment {
            val fragment = DatePickerFragment()
            if (initDatetime !=null ) {
                val bundle = Bundle()
                bundle.putSerializable(ARG_INIT_DATETIME, initDatetime)
                fragment.arguments = bundle
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_datepicker, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Get yesterday's date :  calendar.add(Calendar.DATE, -1)
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, 1)

        val datetime  = arguments?.get(ARG_INIT_DATETIME) as Date? ?: Date()
        val cal = Calendar.getInstance()
        cal.time = datetime

        date.minDate = calendar.timeInMillis
        date.updateDate(cal[Calendar.YEAR], cal[Calendar.MONTH], cal[Calendar.DAY_OF_MONTH])

    }

    val dayOfMonth: Int
        get() = date.dayOfMonth

    val month: Int
        get() = date.month

    val year: Int
        get() = date.year
}
