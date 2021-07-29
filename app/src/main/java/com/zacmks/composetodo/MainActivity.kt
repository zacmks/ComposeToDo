package com.zacmks.composetodo

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zacmks.composetodo.ui.theme.ComposeToDoTheme

class MainActivity : ComponentActivity() {

    private val toDoViewModel by viewModels<ToDoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setContent {
            ComposeToDoTheme(darkTheme = false, secondVariant = true) {
                Surface(color = MaterialTheme.colors.background) {
                    PlayGround()
//                    TodoScreen(toDoViewModel)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayGround() {
    Column {
        Text(text = "Texto simples")
        Text(text = "Texto com cor",
            style = TextStyle.Default.copy(color = Color.Blue))
        Text(text = "Texto h6",
            style = MaterialTheme.typography.h6)
        Text(text = "Texto simples com modifiers",
            Modifier
                .alpha(alpha = 0.8f)
                .background(Color.Red)
                .padding(16.dp)
                .background(Color.Blue)
                .padding(12.dp))
        TextField(value = "Fixed Text state", onValueChange = {})
        val (text, onTextChange) = remember { mutableStateOf("TextField change state")}
        TextField(value = text, onValueChange = onTextChange)
        val text2 = rememberSaveable { mutableStateOf("TextField change state saved")}
        TextField(value = text2.value, onValueChange = { text2.value = it })

        Button(onClick = { }) {
            Text(text = "Botão exemplo, shape padrão")
        }
        Button(onClick = { }, shape =
        MaterialTheme.shapes.small.copy(topEnd = CornerSize(8.dp), bottomEnd = CornerSize(8.dp))) {
            Text(text = "Botão exemplo, shape copy")
        }

        Row() {
            Text(text = "Text in a row", Modifier.weight(1.0f))
            Text(text = "End in a row")
        }
        Divider()
        Row() {
            Text(text = "Big Text", style = MaterialTheme.typography.h3)
            Text(text = "Normal Text")
            Text(text = "Centralized text", Modifier.align(Alignment.CenterVertically))
//            Text(text = "Centralized text", Modifier.align(Alignment.CenterHorizontally))
        }
        Divider()
        IconButton(onClick = { /*TODO*/ }, Modifier.padding(12.dp)) {
            Text(text = "Hi")
        }
        Checkbox(checked = true, onCheckedChange = {})
    }
}


@Composable
fun TodoScreen(viewModel: ToDoViewModel) {
    ToDoListScreen(
        viewModel.toDoItems,
        viewModel.selectedItems,
        viewModel::addItem,
        viewModel::toggleItem,
        viewModel::deleteSelected)
}
