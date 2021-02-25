package com.naveen.aiedemo.view.displaytask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.naveen.aiedemo.R
import com.naveen.aiedemo.databinding.FragmentDisplayTodoBinding
import com.naveen.aiedemo.databinding.FragmentNewtodoBinding
import com.naveen.aiedemo.view.BaseFragment
import com.naveen.aiedemo.view.listeners.RecyclerViewListener
import com.naveen.aiedemo.view.todolist.TodoTaskViewModel
import kotlinx.android.synthetic.main.fragment_newtodo.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
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
                hideKeyboardFrom(it1, taskName)
                hideKeyboardFrom(it1, taskBio)
                todoTaskViewModel.updateData(
                        it1.applicationContext,
                        taskName.text.toString(),
                        taskBio.text.toString(),
                        todoTaskViewModel.selectedTaskObject.Id ?: 0
                )
                showAlertDialog()
            }
        })

        todoTaskViewModel.deleteTaskOnClick.observe(viewLifecycleOwner, {
            activity?.let { it1 ->
                hideKeyboardFrom(it1, taskName)
                hideKeyboardFrom(it1, taskBio)
                todoTaskViewModel.deleteData(
                        it1.applicationContext,
                        todoTaskViewModel.selectedTaskObject.Id ?: 0
                )
                showAlertDialog()
            }
        })

        binding.executePendingBindings()
    }

    private fun showAlertDialog() {
        Toast.makeText(activity, "successfully updated in Room data base.", Toast.LENGTH_LONG).show()
        findNavController().popBackStack()
    }
}