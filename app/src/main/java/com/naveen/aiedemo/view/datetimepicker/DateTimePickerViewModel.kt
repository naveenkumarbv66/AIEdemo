package com.naveen.aiedemo.view.datetimepicker

import androidx.lifecycle.ViewModel
import java.util.Date

class DateTimePickerViewModel: ViewModel() {
    var onSuccess: ((Date) -> Unit)? = null
}
