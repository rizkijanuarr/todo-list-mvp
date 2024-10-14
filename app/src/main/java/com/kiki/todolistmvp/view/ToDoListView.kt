package com.kiki.todolistmvp.view

import com.kiki.todolistmvp.model.ToDoItem

interface ToDoListView {
    fun showToDoItems(items: List<ToDoItem>)
    fun showEmptyState()
}
