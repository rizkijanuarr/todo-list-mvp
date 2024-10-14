package com.kiki.todolistmvp.model


data class ToDoItem(
    val id: Int,
    var title: String,
    var isCompleted: Boolean
)