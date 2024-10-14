package com.kiki.todolistmvp.model

import android.util.Log

class ToDoRepository {
    private val toDoItems = mutableListOf<ToDoItem>()

    fun getToDoItems(): List<ToDoItem> {
        Log.d("REPO", "FETCHING ITEM: $toDoItems")
        return toDoItems
    }

    fun addToDoItem(item: ToDoItem) {
        toDoItems.add(item)
        Log.d("REPO", "ADDED ITEM: $item")
    }

    fun completeToDoItem(itemId: Int, isCompleted: Boolean) {
        val item = toDoItems.find { it.id == itemId }
        if (item != null) {
            item.isCompleted = isCompleted
            Log.d("REPO", "UPDATED COMPLETION STATUS: $item")
        } else {
            Log.d("REPO", "ID : $itemId , UPDATED COMPLETION STATUS FAIL")
        }
    }

    fun updateToDoItem(itemId: Int, newTitle: String) {
        val item = toDoItems.find { it.id == itemId }
        if (item != null) {
            item.title = newTitle
            Log.d("REPO", "UPDATED ITEM: $item")
        } else {
            Log.d("REPO", "ID : $itemId , UPDATED ITEM FAIL")
        }
    }

    fun deleteToDoItem(itemId: Int) {
        val removed = toDoItems.removeAll { it.id == itemId }
        if (removed) {
            Log.d("REPO", "DELETED ITEM: $itemId")
        } else {
            Log.d("REPO", "ID $itemId , DELETED ITEM FAIL")
        }
    }
}




