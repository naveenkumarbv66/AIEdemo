package com.naveen.aiedemo.view.displaytask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.naveen.aiedemo.R
import com.naveen.aiedemo.databinding.FragmentDisplayTodoBinding
import com.naveen.aiedemo.view.BaseFragment
import com.naveen.aiedemo.view.datetimepicker.DateTimePickerDialog
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = todoTaskViewModel

        todoTaskViewModel.updateTaskOnClick.observe(viewLifecycleOwner, {
            activity?.let { it1 ->
                hideKeyboardFrom(it1, binding.taskName)
                hideKeyboardFrom(it1, binding.taskBio)
                todoTaskViewModel.updateData(
                    it1.applicationContext,
                    binding.taskNameInputEditText.text.toString(),
                    binding.taskDescInputEditText.text.toString(),
                    todoTaskViewModel.selectedTaskObject.Id ?: 0,
                    todoTaskViewModel.userSelectedDateTime.value!!.time
                )
                showAlertDialog(getString(R.string.successfully_updated_stored_data))
            }
        })

        todoTaskViewModel.deleteTaskOnClick.observe(viewLifecycleOwner, {
            activity?.let { it1 ->
                hideKeyboardFrom(it1, binding.taskNameInputEditText)
                hideKeyboardFrom(it1, binding.taskDescInputEditText)
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
}