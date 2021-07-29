package com.zacmks.composetodo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ToDoListScreen(viewModel: ToDoViewModel) {

    val (text, onTextChange) = rememberSaveable { mutableStateOf("") }

    val submitItem = {
        if (text.isNotBlank()) {
            viewModel.addItem(ToDoItem(text))
            onTextChange("")
        }
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("ToDo List") },
            actions = {
                IconButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Icon(Icons.Rounded.Delete, contentDescription = "Remover items")
                }
            }
        )
    }) {
        Column(Modifier.fillMaxSize()) {
            LazyColumn(Modifier
                .fillMaxWidth()
                .weight(1.0f)) {
                items(100) {
                    val (select, setSelected) = remember { mutableStateOf(false) }
                    ToDoRow("Texto exemplo", select, setSelected)
                }
            }
            ToDoFieldAndButton(text, onTextChange, submitItem)
            Divider()
        }
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
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                onAddItem()
                keyboardController?.hide()
            }),
            maxLines = 1,
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


@Composable
fun ToDoRow(
    text: String = "Texto Exemplo",
    selected: Boolean = false,
    setSelected: (Boolean) -> Unit,
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
                text = text,
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
                onCheckedChange = { setSelected(!selected) }
            )
        }
        Divider()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewToDoRow() {
    ToDoRow("Preview de Todo item", true, {})
}
