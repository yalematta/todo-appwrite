package com.yalematta.todoappwrite.ui.tasks

import androidx.lifecycle.ViewModel
import com.yalematta.todoappwrite.data.TaskDao
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskDao: TaskDao
): ViewModel() {

    val searchQuery =  MutableStateFlow("")
    private val tasksFlow = searchQuery.flatMapLatest {
        taskDao.getTasks(it)
    }

    val tasks = tasksFlow.asLiveData()

}