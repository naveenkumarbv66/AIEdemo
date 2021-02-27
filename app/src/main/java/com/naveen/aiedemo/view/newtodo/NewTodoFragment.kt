package com.naveen.aiedemo.view.newtodo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.naveen.aiedemo.R
import com.naveen.aiedemo.databinding.FragmentNewtodoBinding
import com.naveen.aiedemo.view.BaseFragment
import com.naveen.aiedemo.view.datetimepicker.DateTimePickerDialog
import com.naveen.aiedemo.view.todolist.TodoTaskViewModel
import java.util.*

class NewTodoFragment : BaseFragment() {

    private val todoTaskViewModel: TodoTaskViewModel by activityViewModels()
    lateinit var binding: FragmentNewtodoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_newtodo,
            container,
            false
        )

        binding.lifecycleOwner = this;

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = todoTaskViewModel
        todoTaskViewModel.userSelectedDateTime.value = Date()

        todoTaskViewModel.createNewTaskOnClick.observe(viewLifecycleOwner, {
            checkTodoTaskExistList()
        })

        todoTaskViewModel.dateTimePickerOnClick.observe(viewLifecycleOwner, {
            openDateTimePickerDialog()
        })

        binding.executePendingBindings()
    }

    private fun showAlertDialog() {
        Toast.makeText(activity, getString(R.string.successfully_stored_data), Toast.LENGTH_LONG).show()
        findNavController().popBackStack()
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
            todoTaskViewModel.getTodoTaskExistList(binding.taskNameInputEditText.text.toString(),
                todoTaskViewModel.userSelectedDateTime.value!!.time,
                it1.applicationContext)
                ?.observe(viewLifecycleOwner, {
                    if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                        if (it.isNullOrEmpty()) {
                            hideKeyboardFrom(it1, binding.taskNameInputEditText)
                            hideKeyboardFrom(it1, binding.taskBio)
                            todoTaskViewModel.insertData(
                                it1.applicationContext,
                                binding.taskNameInputEditText.text.toString(),
                                binding.taskDescInputEditText.text.toString(),
                                todoTaskViewModel.userSelectedDateTime.value!!.time
                            )
                            showAlertDialog()
                        } else binding.taskNameInputLayout.error = getString(R.string.name_exists)
                    }
                })
        }
    }
}