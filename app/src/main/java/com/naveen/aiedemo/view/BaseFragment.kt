package com.naveen.aiedemo.view

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.naveen.aiedemo.view.notifications.NotificationPublisher
import com.naveen.aiedemo.view.room.model.TodoTableModel

abstract class BaseFragment : Fragment() {

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun createNotificationAlarm(todoTaskObject: TodoTableModel) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationPublisher::class.java).apply {
            putExtra(NotificationPublisher.TASK_NAME, todoTaskObject.TaskTitle)
            putExtra(NotificationPublisher.TASK_DESCRIPTION, todoTaskObject.TaskInfo)
        }
        // Used for filtering inside Broadcast receiver
        intent.action = NotificationPublisher.ACTION_NAME
        val pendingIntent = PendingIntent.getBroadcast(context, NotificationPublisher.NOTIFICATION_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        // In this particular example we are going to set it to trigger after 30 -> 30000 seconds.
        // val alarmTimeAtUTC: Long = System.currentTimeMillis() + msUntilTriggerHour
        // You can work with time later when you know this works for sure.
        // val msUntilTriggerHour: Long = 10000
        val alarmTimeAtUTC: Long = todoTaskObject.TaskTime
        // Depending on the version of Android use different function for setting an Alarm.
        // setAlarmClock() - used for everything lower than Android M
        //alarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(alarmTimeAtUTC, pendingIntent), pendingIntent)
        // setExactAndAllowWhileIdle() - used for everything on Android M and higher
        alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC,
                alarmTimeAtUTC,
                pendingIntent
        )
    }

    fun deleteAlarm() {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(
                PendingIntent.getBroadcast(
                        activity?.applicationContext, NotificationPublisher.NOTIFICATION_ID, Intent(activity?.applicationContext, NotificationPublisher::class.java), PendingIntent.FLAG_CANCEL_CURRENT))
    }

    fun showAlertDialog(message: String) {
        Toast.makeText(
                activity,
                message,
                Toast.LENGTH_LONG
        ).show()
        findNavController().popBackStack()
    }
}