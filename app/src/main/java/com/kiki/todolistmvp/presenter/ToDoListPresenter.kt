package com.kiki.todolistmvp.presenter

import android.util.Log
import com.kiki.todolistmvp.model.ToDoItem
import com.kiki.todolistmvp.model.ToDoRepository
import com.kiki.todolistmvp.view.ToDoListView

class ToDoListPresenter(
    private val view: ToDoListView,
    private val repository: ToDoRepository
) {
    fun loadToDoItems() {
        val items = repository.getToDoItems()
        Log.d("PRESENTER", "LOAD ITEM: $items")
        if (items.isEmpty()) {
            view.showEmptyState()
        } else {
            view.showToDoItems(items)
        }
    }

    fun addToDoItem(title: String) {
        val newItem = ToDoItem(id = repository.getToDoItems().size + 1, title = title, isCompleted = false)
        repository.addToDoItem(newItem)
        Log.d("PRESENTER", "ADDED ITEM: $newItem")
        loadToDoItems()
    }

    fun completeToDoItem(itemId: Int, isCompleted: Boolean) {
        Log.d("PRESENTER", "ID: $itemId , STATUS COMPLETED = $isCompleted")
        repository.completeToDoItem(itemId, isCompleted)
        loadToDoItems()
    }

    fun updateToDoItem(itemId: Int, newTitle: String) {
        Log.d("PRESENTER", "ID: $itemId TITLE = $newTitle")
        repository.updateToDoItem(itemId, newTitle)
        loadToDoItems()
    }

    fun deleteToDoItem(itemId: Int) {
        Log.d("PRESENTER", "ID: $itemId")
        repository.deleteToDoItem(itemId)
        loadToDoItems()
    }
}