package com.naveen.aiedemo.view.todolist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.naveen.aiedemo.R
import com.naveen.aiedemo.databinding.FragmentTodolistBinding
import com.naveen.aiedemo.view.BaseFragment
import com.naveen.aiedemo.view.adapter.TodoTaskListAdapter
import com.naveen.aiedemo.view.listeners.RecyclerViewListener
import com.naveen.aiedemo.view.room.model.TodoTableModel
import com.naveen.aiedemo.view.room.repository.TodoTaskRepositoryImpl
import kotlinx.android.synthetic.main.fragment_todolist.*

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
                Log.d("Naveen", "=====>>>\n Selected => ".plus(todoTaskObject.Id.toString())
                        .plus(" : ").plus(todoTaskObject.TaskTitle)
                        .plus(" ~~~ ").plus(todoTaskObject.TaskInfo))
                todoTaskViewModel.selectedTaskObject = todoTaskObject
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
}