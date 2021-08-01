package com.zacmks.composetodo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.zacmks.composetodo.datasource.TodoItem
import kotlin.reflect.KFunction1

@Composable
fun TodoItemListScreen(
    items: LiveData<List<TodoItem>>, selectedItems: LiveData<List<TodoItem>>?,
    onAddItem: KFunction1<TodoItem, Unit>, onToggleItem: KFunction1<TodoItem, Unit>,
    onDeleteItems: () -> Unit,
) {
    val itemsList = items.observeAsState(listOf()).value
    val selectedItemsList = selectedItems?.observeAsState(listOf())?.value
    val (text, onTextChange) = rememberSaveable { mutableStateOf("") }

    val submitItem = {
        if (text.isNotBlank()) {
            onAddItem(TodoItem(text = text, checked = false))
            onTextChange("")
        }
    }

    Scaffold(topBar = {
        val selected = !selectedItemsList.isNullOrEmpty()
        TopAppBar(
            title = { Text(stringResource(id = R.string.app_name)) },
            actions = {
                IconButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { onDeleteItems() },
                    enabled = selected
                ) {
                    Icon(
                        Icons.Rounded.Delete,
                        contentDescription = "Remover items",
                        tint = LocalContentColor.current.copy(
                            alpha = if (selected) 1.0f else 0.38f
                        )
                    )
                }
            }
        )
    }) {
        Column(Modifier.fillMaxSize()) {
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .weight(1.0f)
            ) {
                items(items = itemsList) { todoItem ->
                    ToDoRow(todoItem, onToggleItem)
                }
            }
            ToDoFieldAndButton(text, onTextChange, submitItem)
            Divider()
        }
    }
}

@Composable
fun ToDoRow(
    toDoItem: TodoItem,
    doToggle: (TodoItem) -> Unit,
) {
    var selected = toDoItem.checked

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(1.0f),
                text = toDoItem.text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = if (selected)
                    MaterialTheme.typography.h6.copy(textDecoration = TextDecoration.LineThrough)
                else
                    MaterialTheme.typography.h6
            )
            Checkbox(
                modifier = Modifier.padding(horizontal = 8.dp),
                checked = selected,
                onCheckedChange = {
                    selected = it
                    toDoItem.checked = selected
                    doToggle(toDoItem)
                }
            )
        }
        Divider()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ToDoFieldAndButton(
    text: String,
    onTextChange: (String) -> Unit,
    onAddItem: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Divider(Modifier.fillMaxWidth())
    Row(Modifier.fillMaxWidth()) {
        TextField(
            value = text, onValueChange = onTextChange,
            singleLine = true,
            maxLines = 1,
            textStyle = MaterialTheme.typography.h6,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                onAddItem()
                keyboardController?.hide()
            }),
            modifier = Modifier.weight(1.0f)
        )
        IconButton(
            onClick = onAddItem,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Icon(Icons.Rounded.Send, contentDescription = "Adicionar item")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewToDoRow() {
    ToDoRow(TodoItem(text = "Preview of Todo item", checked = true)) {}
}

@Preview(showBackground = true)
@Composable
fun PreviewToDoFieldAndButton() {
    ToDoFieldAndButton("Adding some text", {}, {})
}