package com.naveen.aiedemo.view.todolist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import com.naveen.aiedemo.R
import com.naveen.aiedemo.view.room.model.TodoTableModel
import com.naveen.aiedemo.view.room.repository.TodoTaskRepositoryImpl
import kotlinx.android.synthetic.main.fragment_todolist.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TodoListFragment : Fragment() {

    private val todoTaskViewModel: TodoTaskViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todolist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        todoTaskViewModel.todoTaskRepository = TodoTaskRepositoryImpl()

        insertData.setOnClickListener {
            // findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            activity?.let { it1 ->
                todoTaskViewModel.insertData(
                    it1.applicationContext,
                    todotTitle.text.toString(),
                    todotInfo.text.toString()
                )
            }
        }

        read.setOnClickListener {
            activity?.let { it1 ->
                todoTaskViewModel.getData(it1.applicationContext)
                    ?.observe(viewLifecycleOwner, Observer {
                        if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                            it.forEachIndexed { index: Int, todoTableModel: TodoTableModel ->
                                Log.d(
                                    "Naveen",
                                    "====> " + todoTableModel.Id.toString().plus(" => ").plus(
                                        todoTableModel.TaskTitle.plus(" : ")
                                            .plus(todoTableModel.TaskInfo)
                                    )
                                )
                            }
                        }
                    })
            }
        }
    }
}