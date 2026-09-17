package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.ui.theme.TodoAppTheme
import java.io.Serializable
import java.util.UUID

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val isCompleted: Boolean = false
) : Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkMode by rememberSaveable { mutableStateOf(false) }

            TodoAppTheme(darkTheme = isDarkMode, dynamicColor = false) {
                val backgroundColor = if (isDarkMode) Color(0xFF121829) else Color(0xFFF4F6FB)

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = backgroundColor
                ) {
                    Todo(
                        isDarkMode = isDarkMode,
                        onToggleTheme = { isDarkMode = !isDarkMode }
                    )
                }
            }
        }
    }
}

@Composable
fun Todo(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean = false,
    onToggleTheme: () -> Unit = {}
) {
    var tasks by rememberSaveable {
        mutableStateOf(
            listOf(
                Task(text = "Complete Kotlin project"),
                Task(text = "Read tech article"),
                Task(text = "Go for a walk"),
                Task(text = "Plan tomorrow"),
                Task(text = "Buy groceries")
            )
        )
    }

    val titleColor = if (isDarkMode) Color(0xFFFFFFFF) else Color(0xFF17203A)
    val subtitleColor = if (isDarkMode) Color(0xFF9EA8C3) else Color(0xFF7B849C)

    val completedCount = tasks.count { it.isCompleted }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "My Tasks",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = titleColor
            )
            ThemeToggleIcon(
                isDarkMode = isDarkMode,
                onToggleTheme = onToggleTheme
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (tasks.isEmpty()) "Get things done 🌱" else "Get things done 🌱 • $completedCount of ${tasks.size} completed",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = subtitleColor
        )

        Spacer(modifier = Modifier.height(16.dp))

        Textformfie(
            isDarkMode = isDarkMode,
            onAddTask = { newTaskText ->
                if (newTaskText.isNotBlank()) {
                    tasks = tasks + Task(text = newTaskText.trim())
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (tasks.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No tasks yet! Add one above 🎉",
                    fontSize = 16.sp,
                    color = subtitleColor
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(tasks, key = { it.id }) { task ->
                    TaskCard(
                        task = task,
                        isDarkMode = isDarkMode,
                        onToggleComplete = {
                            tasks = tasks.map {
                                if (it.id == task.id) it.copy(isCompleted = !it.isCompleted) else it
                            }
                        },
                        onDeleteTask = {
                            tasks = tasks.filterNot { it.id == task.id }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ThemeToggleIcon(
    isDarkMode: Boolean = false,
    onToggleTheme: () -> Unit = {}
) {
    Icon(
        imageVector = if (isDarkMode) Icons.Default.NightsStay else Icons.Default.WbSunny,
        contentDescription = "Toggle theme",
        tint = if (isDarkMode) Color(0xFFFFD54F) else Color(0xFF17203A),
        modifier = Modifier
            .size(28.dp)
            .clickable { onToggleTheme() }
    )
}

@Composable
fun Textformfie(
    isDarkMode: Boolean = false,
    onAddTask: (String) -> Unit = {}
) {
    var taskText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    val submitTask = {
        if (taskText.isNotBlank()) {
            onAddTask(taskText)
            taskText = ""
            keyboardController?.hide()
        }
    }

    val containerColor = if (isDarkMode) Color(0xFF1E2640) else Color.White
    val borderColor = if (isDarkMode) Color(0xFF2C3655) else Color(0xFFE3E6F0)
    val textColor = if (isDarkMode) Color.White else Color(0xFF17203A)

    OutlinedTextField(
        value = taskText,
        onValueChange = { taskText = it },
        placeholder = {
            Text(
                text = "Add a new task...",
                color = Color(0xFF8A91A5)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add task",
                tint = Color(0xFF6254E8)
            )
        },
        trailingIcon = {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(
                        color = if (taskText.isNotBlank()) Color(0xFF6254E8) else Color(0xFFB0B7C6),
                        shape = CircleShape
                    )
                    .clickable { submitTask() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Submit task",
                    tint = Color.White
                )
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { submitTask() }),
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp),
        shape = RoundedCornerShape(18.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF6254E8),
            unfocusedBorderColor = borderColor,
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            focusedTextColor = textColor,
            unfocusedTextColor = textColor
        )
    )
}

@Composable
fun TaskCard(
    task: Task,
    isDarkMode: Boolean = false,
    onToggleComplete: () -> Unit = {},
    onDeleteTask: () -> Unit = {}
) {
    val cardBackground = if (isDarkMode) Color(0xFF1E2640) else Color.White
    val textColor = if (task.isCompleted) {
        if (isDarkMode) Color(0xFF6C768E) else Color(0xFF8A91A5)
    } else {
        if (isDarkMode) Color.White else Color(0xFF17203A)
    }

    val checkboxBorderColor = if (task.isCompleted) Color(0xFF6254E8) else if (isDarkMode) Color(0xFF4A5578) else Color(0xFF8A93A8)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(
                color = cardBackground,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Checkbox Circle
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .background(
                        color = if (task.isCompleted) Color(0xFF6254E8) else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 2.dp,
                        color = checkboxBorderColor,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { onToggleComplete() },
                contentAlignment = Alignment.Center
            ) {
                if (task.isCompleted) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Completed",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Task Text
            Text(
                text = task.text,
                fontSize = 16.sp,
                color = textColor,
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onToggleComplete() }
            )

            // Delete Icon Button
            IconButton(
                onClick = onDeleteTask,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete task",
                    tint = if (isDarkMode) Color(0xFFFF6B6B) else Color(0xFFE57373),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}