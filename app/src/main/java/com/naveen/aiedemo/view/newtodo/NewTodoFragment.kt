package com.naveen.aiedemo.view.newtodo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.naveen.aiedemo.R
import com.naveen.aiedemo.databinding.FragmentNewtodoBinding
import com.naveen.aiedemo.view.BaseFragment
import com.naveen.aiedemo.view.todolist.TodoTaskViewModel
import kotlinx.android.synthetic.main.fragment_newtodo.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
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

        todoTaskViewModel.createNewTaskOnClick.observe(viewLifecycleOwner, {
            activity?.let { it1 ->
                todoTaskViewModel.insertData(
                    it1.applicationContext,
                    taskName.text.toString(),
                    taskBio.text.toString()
                )
            }
        })

        binding.executePendingBindings()
    }
}