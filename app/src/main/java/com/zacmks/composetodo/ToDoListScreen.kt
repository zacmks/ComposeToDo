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

@Composable
fun ToDoListScreen(
    items: List<ToDoItem>, selectedItems: List<ToDoItem>,
    onAddItem: (ToDoItem) -> Unit, onToggleItem: (ToDoItem) -> Unit,
    onDeleteItems: () -> Unit,
) {
    val (text, onTextChange) = rememberSaveable { mutableStateOf("") }

    val submitItem = {
        if (text.isNotBlank()) {
            onAddItem(ToDoItem(text))
            onTextChange("")
        }
    }

    Scaffold(topBar = {
        val deleteEnabled = selectedItems.isNotEmpty()
        TopAppBar(
            title = { Text(stringResource(id = R.string.app_name)) },
            actions = {
                IconButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { onDeleteItems() },
                    enabled = deleteEnabled
                ) {
                    Icon(
                        Icons.Rounded.Delete,
                        contentDescription = "Remover items",
                        tint = LocalContentColor.current.copy(
                            alpha = if (deleteEnabled) 1.0f else 0.38f
                        )
                    )
                }
            }
        )
    }) {
        Column(Modifier.fillMaxSize()) {
            LazyColumn(Modifier
                .fillMaxWidth()
                .weight(1.0f)) {
                items(items = items) { toDoItem ->
                    val selected = selectedItems.find { it == toDoItem } != null
                    ToDoRow(toDoItem, selected, onToggleItem)
                }
            }
            ToDoFieldAndButton(text, onTextChange, submitItem)
            Divider()
        }
    }
}

@Composable
fun ToDoRow(
    toDoItem: ToDoItem,
    selected: Boolean = false,
    doToggle: (ToDoItem) -> Unit,
) {
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
                onCheckedChange = { doToggle(toDoItem) }
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
            modifier = Modifier.weight(1.0f))
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
    ToDoRow(ToDoItem("Preview of Todo item"), true, {})
}

@Preview(showBackground = true)
@Composable
fun PreviewToDoFieldAndButton() {
    ToDoFieldAndButton("Adding some text", {}, {})
}

@Preview(showBackground = true)
@Composable
fun PreviewToDoListScreen() {
    val firstItem = ToDoItem("Wash the Car")
    val secondItem = ToDoItem("Buy Groceries")
    ToDoListScreen(listOf(firstItem, secondItem), listOf(secondItem), {}, {}, {})
}
