package com.naveen.aiedemo.view.listeners

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.annotation.MainThread
import androidx.databinding.BindingAdapter
import com.hadilq.liveevent.LiveEvent
import java.text.SimpleDateFormat
import java.util.*

@MainThread
fun LiveEvent<Unit>.call(){
    value = null
}

@SuppressLint("SimpleDateFormat")
fun Date.LongToDateString(dateLong: Long): String{
    if(dateLong == 0.toLong()) return ""
    val date = Date(dateLong)
    val format = SimpleDateFormat("dd.MM.yyyy - HH:mm")
    return format.format(date)
}

@BindingAdapter("dateLongToString")
fun parseDateValue(view: TextView, dateLong: Long){
    view.text = Date().LongToDateString(dateLong)
}

