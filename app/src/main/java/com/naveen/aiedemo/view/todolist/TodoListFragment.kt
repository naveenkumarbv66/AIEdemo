package com.naveen.aiedemo.view.todolist

import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.naveen.aiedemo.R
import com.naveen.aiedemo.databinding.FragmentTodolistBinding
import com.naveen.aiedemo.view.BaseFragment
import com.naveen.aiedemo.view.adapter.TodoTaskListAdapter
import com.naveen.aiedemo.view.listeners.RecyclerViewListener
import com.naveen.aiedemo.view.notifications.NotificationPublisher
import com.naveen.aiedemo.view.notifications.NotificationPublisher.Companion.ACTION_NAME
import com.naveen.aiedemo.view.notifications.NotificationPublisher.Companion.NOTIFICATION_ID
import com.naveen.aiedemo.view.notifications.NotificationPublisher.Companion.TASK_DESCRIPTION
import com.naveen.aiedemo.view.notifications.NotificationPublisher.Companion.TASK_NAME
import com.naveen.aiedemo.view.room.model.TodoTableModel
import com.naveen.aiedemo.view.room.repository.TodoTaskRepositoryImpl
import kotlinx.android.synthetic.main.fragment_todolist.*
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TodoListFragment : BaseFragment() {

    private val todoTaskViewModel: TodoTaskViewModel by activityViewModels()
    lateinit var binding: FragmentTodolistBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_todolist,
                container,
                false
        )

        binding.lifecycleOwner = this;

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        todoTaskViewModel.todoTaskRepository = TodoTaskRepositoryImpl()

        val layoutManager = LinearLayoutManager(context)
        todoListRecyclerView.layoutManager = layoutManager
        todoListRecyclerView.hasFixedSize()
        todoListRecyclerView.adapter = TodoTaskListAdapter(object : RecyclerViewListener {
            override fun onClick(todoTaskObject: TodoTableModel) {
                Log.d(
                        "Naveen", "=====>>>\n Selected => ".plus(todoTaskObject.Id.toString())
                        .plus(" : ").plus(todoTaskObject.TaskTitle)
                        .plus(" ~~~ ").plus(todoTaskObject.TaskInfo)
                )
                todoTaskViewModel.selectedTaskObject = todoTaskObject
//                createNotificationAlaram(todoTaskObject)
                 findNavController().navigate(R.id.action_DisplayTaskListFragment_to_DisplaySingleTaskFragment)
            }
        })
        todoListRecyclerView.addItemDecoration(
                DividerItemDecoration(
                        context,
                        layoutManager.orientation
                )
        )

        binding.viewModel = todoTaskViewModel

        todoTaskViewModel.liveDataTodoTableModelTest.observe(viewLifecycleOwner, {
            it.forEachIndexed { index: Int, todoTableModel: TodoTableModel ->
                Log.d(
                        "Naveen",
                        "====> " + todoTableModel.Id.toString().plus(" => ").plus(
                                todoTableModel.TaskTitle.plus(" : ")
                                        .plus(todoTableModel.TaskInfo)
                        )
                )
            }
        })

        binding.executePendingBindings()
        getTodoCompleteList();
    }

    private fun getTodoCompleteList() {
        activity?.let { it1 ->
            todoTaskViewModel.getData(it1.applicationContext)
                    ?.observe(viewLifecycleOwner, {
                        if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                            if (it.isNullOrEmpty()) todoTaskViewModel.liveDataTodoTableModelTest.value =
                                    todoTaskViewModel.getIDefaultNoDataMessage()
                            else todoTaskViewModel.liveDataTodoTableModelTest.value = it
                        }
                    })
        }
    }

    private fun createNotificationAlaram(todoTaskObject: TodoTableModel) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationPublisher::class.java).apply {
            putExtra(TASK_NAME, todoTaskObject.TaskTitle)
            putExtra(TASK_DESCRIPTION, todoTaskObject.TaskInfo)
        }
        // Used for filtering inside Broadcast receiver
        intent.action = ACTION_NAME
        val pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        // In this particular example we are going to set it to trigger after 30 -> 30000 seconds.
        // You can work with time later when you know this works for sure.
        val msUntilTriggerHour: Long = 10000
        val alarmTimeAtUTC: Long = System.currentTimeMillis() + msUntilTriggerHour
        // Depending on the version of Android use different function for setting an Alarm.
        // setAlarmClock() - used for everything lower than Android M
        //alarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(alarmTimeAtUTC, pendingIntent), pendingIntent)
        // setExactAndAllowWhileIdle() - used for everything on Android M and higher
        alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alarmTimeAtUTC,
                pendingIntent
        )
    }
}