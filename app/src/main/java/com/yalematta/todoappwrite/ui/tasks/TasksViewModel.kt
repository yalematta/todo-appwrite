package com.yalematta.todoappwrite.ui.tasks

import androidx.lifecycle.ViewModel
import com.yalematta.todoappwrite.data.TaskDao
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yalematta.todoappwrite.data.PreferencesManager
import com.yalematta.todoappwrite.data.SortOrder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskDao: TaskDao,
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val searchQuery =  MutableStateFlow("")

    val preferencesFlow = preferencesManager.preferencesFlow

    val sortOrder = MutableStateFlow(SortOrder.BY_DATE)
    val hideCompleted = MutableStateFlow(false)

    private val tasksFlow = combine(
        searchQuery,
        preferencesFlow
    ) {
        query, filterPreferences -> Pair(query, filterPreferences)
    }.flatMapLatest { (query, filterPreferences) ->
        taskDao.getTasks(query, filterPreferences.sortOrder, filterPreferences.hideCompleted)
    }

    fun onSortOrderSelected(sortOrder: SortOrder) =
        viewModelScope.launch {
            preferencesManager.updateSortOrder(sortOrder)
        }

    fun onHideCompletedClick(hideCompleted: Boolean) =
        viewModelScope.launch {
            preferencesManager.updateHideCompleted(hideCompleted)
        }

    val tasks = tasksFlow.asLiveData()
}