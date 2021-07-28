package com.zacmks.composetodo

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zacmks.composetodo.ui.theme.ComposeToDoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setContent {
            ComposeToDoTheme() {
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("ToDo List")
                },
                actions = {
                    IconButton(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            Icons.Rounded.Delete,
                            contentDescription = "Remover items"
                        )
                    }
                }
            )
        }
    ) {
        Column(Modifier.fillMaxSize()) {
            LazyColumn(Modifier
                .fillMaxWidth()
                .weight(1.0f)) {
                items(100) {
                    val (select, setSelected) = remember { mutableStateOf(false) }
                    ToDoItem("Texto exemplo", select, setSelected)
                }
            }
            Divider()
            Row(Modifier.fillMaxWidth()) {
                TextField(
                    modifier = Modifier.weight(1.0f),
                    value = "",
                    onValueChange = {})
                IconButton(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .align(Alignment.CenterVertically),
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        Icons.Rounded.Send,
                        contentDescription = "Adicionar item"
                    )
                }
            }
        }
    }
}

@Composable
fun ToDoItem(
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
