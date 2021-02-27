package com.naveen.aiedemo.view.listeners

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.annotation.MainThread
import androidx.databinding.BindingAdapter
import com.hadilq.liveevent.LiveEvent
import com.naveen.aiedemo.view.datetimepicker.adapter.PickerAdapter.Companion.DEFAULT_DATETIME_FORMAT
import java.util.*

@MainThread
fun LiveEvent<Unit>.call(){
    value = null
}

@SuppressLint("SimpleDateFormat")
fun Date.longToDateString(dateLong: Long): String{
    return if(dateLong == 0.toLong())  ""
    else DEFAULT_DATETIME_FORMAT.format(Date(dateLong))
}

@BindingAdapter("dateLongToString")
fun parseDateValue(view: TextView, dateLong: Long){
    view.text = Date().longToDateString(dateLong)
}

