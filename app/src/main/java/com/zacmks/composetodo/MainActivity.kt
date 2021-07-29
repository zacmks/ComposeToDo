package com.zacmks.composetodo

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import com.zacmks.composetodo.ui.theme.ComposeToDoTheme

class MainActivity : ComponentActivity() {

    private val toDoViewModel by viewModels<ToDoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setContent {
            ComposeToDoTheme() {
                Surface(color = MaterialTheme.colors.background) {
                    TodoScreen(toDoViewModel)
                }
            }
        }
    }
}

@Composable
fun TodoScreen(viewModel: ToDoViewModel) {
    ToDoListScreen(viewModel)
}
