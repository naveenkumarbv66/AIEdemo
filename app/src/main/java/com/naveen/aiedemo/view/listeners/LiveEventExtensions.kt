package com.naveen.aiedemo.view.listeners

import androidx.annotation.MainThread
import com.hadilq.liveevent.LiveEvent

@MainThread
fun LiveEvent<Unit>.call(){
    value = null
}