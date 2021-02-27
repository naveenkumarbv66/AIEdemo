package com.naveen.aiedemo.view.displaytask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import com.naveen.aiedemo.R
import com.naveen.aiedemo.databinding.FragmentDisplayTodoBinding
import com.naveen.aiedemo.view.BaseFragment
import com.naveen.aiedemo.view.datetimepicker.DateTimePickerDialog
import com.naveen.aiedemo.view.listeners.longToDateString
import com.naveen.aiedemo.view.todolist.TodoTaskViewModel
import java.util.*

class DisplayTodoFragment : BaseFragment() {

    private val todoTaskViewModel: TodoTaskViewModel by activityViewModels()
    lateinit var binding: FragmentDisplayTodoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_display_todo,
            container,
            false
        )

        binding.lifecycleOwner = this;

        return binding.root
    }

    private fun hideKeyPad() {
        activity?.applicationContext?.let {
            hideKeyboardFrom(it, binding.taskNameInputEditText)
            hideKeyboardFrom(it, binding.taskDescInputEditText)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = todoTaskViewModel
        todoTaskViewModel.userSelectedDateTime.value =
            Date(todoTaskViewModel.selectedTaskObject.TaskTime)

        todoTaskViewModel.updateTaskOnClick.observe(viewLifecycleOwner, {
            checkTodoTaskExistList()
        })

        todoTaskViewModel.deleteTaskOnClick.observe(viewLifecycleOwner, {
            activity?.let { it1 ->
                hideKeyPad()
                todoTaskViewModel.deleteData(
                    it1.applicationContext,
                    todoTaskViewModel.selectedTaskObject.Id ?: 0
                )
                showAlertDialog(getString(R.string.successfully_deleted_stored_data))
            }
        })

        todoTaskViewModel.dateTimePickerOnClick.observe(viewLifecycleOwner, {
            openDateTimePickerDialog()
        })

        todoTaskViewModel.userSelectedDateTime.value =
            Date(todoTaskViewModel.selectedTaskObject.TaskTime)

        binding.executePendingBindings()
    }

    private fun openDateTimePickerDialog() {
        val callback: (date: Date) -> Unit = { newDate ->
            todoTaskViewModel.userSelectedDateTime.value = newDate
        }

        activity?.let {
            DateTimePickerDialog.show(
                it.supportFragmentManager,
                getString(R.string.fragment_tag),
                callback,
                Date(),
                DateTimePickerDialog.TIME_DATE
            )
        }
    }

    private fun checkTodoTaskExistList() {
        activity?.let { it1 ->
            todoTaskViewModel.getTodoTaskExistList(
                binding.taskNameInputEditText.text.toString(),
                it1.applicationContext
            )
                ?.observe(viewLifecycleOwner, {
                    if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                        if (it.isNullOrEmpty() || !todoTaskViewModel.isSameDateExistsForOldRecode(
                                it,
                                Date().longToDateString(todoTaskViewModel.userSelectedDateTime.value!!.time)
                            )
                        ) {
                            hideKeyPad()
                            todoTaskViewModel.updateData(
                                it1.applicationContext,
                                binding.taskNameInputEditText.text.toString(),
                                binding.taskDescInputEditText.text.toString(),
                                todoTaskViewModel.selectedTaskObject.Id ?: 0,
                                todoTaskViewModel.userSelectedDateTime.value!!.time
                            )
                            showAlertDialog(getString(R.string.successfully_updated_stored_data))
                        } else binding.taskName.error = getString(R.string.name_exists)
                    }
                })
        }
    }
}