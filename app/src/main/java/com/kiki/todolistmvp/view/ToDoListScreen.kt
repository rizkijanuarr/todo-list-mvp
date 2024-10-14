package com.kiki.todolistmvp.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.kiki.todolistmvp.presenter.ToDoListPresenter
import com.kiki.todolistmvp.model.ToDoRepository
import com.kiki.todolistmvp.model.ToDoItem

@Composable
fun ToDoListScreen() {
    var toDoItems by remember { mutableStateOf<List<ToDoItem>>(emptyList()) }
    var showEmptyState by remember { mutableStateOf(false) }
    var newItemTitle by remember { mutableStateOf("") }

    var isEditing by remember { mutableStateOf(false) }
    var editItemId by remember { mutableStateOf<Int?>(null) }
    var editItemTitle by remember { mutableStateOf("") }

    val context = LocalContext.current
    val repository = remember { ToDoRepository() }

    val view = object : ToDoListView {
        override fun showToDoItems(items: List<ToDoItem>) {
            // update ui
            toDoItems = items
            showEmptyState = items.isEmpty()
        }

        override fun showEmptyState() {
            toDoItems = emptyList()
            showEmptyState = true
        }
    }

    val presenter = remember { ToDoListPresenter(view, repository) }

    LaunchedEffect(Unit) {
        presenter.loadToDoItems()  // load
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        TextField(
            value = if (isEditing) editItemTitle else newItemTitle,
            onValueChange = { if (isEditing) editItemTitle = it else newItemTitle = it },
            label = { Text(if (isEditing) "Edit ToDo Item" else "New ToDo Item") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (isEditing) {
                    editItemId?.let { id ->
                        presenter.updateToDoItem(id, editItemTitle)
                        isEditing = false
                        editItemTitle = ""
                        Toast.makeText(context, "Task updated", Toast.LENGTH_LONG).show()
                    }
                } else {
                    if (newItemTitle.isNotEmpty()) {
                        presenter.addToDoItem(newItemTitle)
                        newItemTitle = ""
                        Toast.makeText(context, "Task added", Toast.LENGTH_LONG).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isEditing) "Update Task" else "Add Task")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (showEmptyState) {
            Text("No ToDo Items", style = MaterialTheme.typography.bodyLarge)
        } else {
            // cek item ketika berubah
            LazyColumn {
                items(toDoItems) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(item.title, modifier = Modifier.weight(1f))

                        Checkbox(
                            checked = item.isCompleted,
                            onCheckedChange = { isChecked ->
                                // kirim status true or false ke presenter
                                presenter.completeToDoItem(item.id, isChecked)
                                Toast.makeText(context, if (isChecked) "Task completed" else "Task incomplete", Toast.LENGTH_SHORT).show()
                            }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(onClick = {
                            isEditing = true
                            editItemId = item.id
                            editItemTitle = item.title
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(onClick = {
                            presenter.deleteToDoItem(item.id)
                            Toast.makeText(context, "Task deleted", Toast.LENGTH_LONG).show()
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            }
        }
    }
}