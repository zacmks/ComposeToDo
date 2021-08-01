package com.zacmks.composetodo

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.zacmks.composetodo.ui.theme.ComposeToDoTheme

class MainActivity : ComponentActivity() {

    private val todoItemViewModel: TodoItemViewModel by viewModels {
        TodoItemViewModelFactory((application as App).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        setContent {
            ComposeToDoTheme(darkTheme = false, secondVariant = false) {
                Surface(color = MaterialTheme.colors.background) {
                    TodoItemScreen(todoItemViewModel)
                }
            }
        }
    }
}

@Composable
fun TodoItemScreen(viewModel: TodoItemViewModel) {
    TodoItemListScreen(
        viewModel.todoItems,
        viewModel.isItemChecked,
        viewModel::addItem,
        viewModel::toggleItem,
        viewModel::deleteSelected
    )
}