package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.foundation.layout.Box
import  androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
Scaffold(modifier = Modifier.fillMaxSize()) {
    innerPadding-> Todo(modifier = Modifier.padding(innerPadding))
}

        }
    }
}

@Composable
fun Todo(modifier: Modifier){
    var taskText by remember { mutableStateOf("") }
    val tasks = remember {
        mutableStateListOf(
            Task("Buy groceries", false),
            Task("Walk the dog", true),
            Task("Finish Jetpack Compose project", false),
            Task("Read a book", false),
            Task("Exercise for 30 minutes", true)
        )
    }

    Column(Modifier.padding(16.dp)){
        Spacer(modifier = Modifier.height(130.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "My Tasks",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF17203A)
            )
            ThemeToggleIcon()

        }
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Get things done 🌱",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF7B849C)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Textformfie(
            value = taskText,
            onValueChange = { taskText = it },
            onAdd = {
                if (taskText.isNotBlank()) {
                    tasks.add(0, Task(taskText, false))
                    taskText = ""
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TaskList(
            tasks = tasks,
            onToggleComplete = { task ->
                val index = tasks.indexOf(task)
                if (index != -1) {
                    tasks[index] = task.copy(isCompleted = !task.isCompleted)
                }
            },
            onDelete = { task ->
                tasks.remove(task)
            }
        )
    }
}

data class Task(val title: String, val isCompleted: Boolean)

@Composable
fun TaskList(
    tasks: List<Task>,
    onToggleComplete: (Task) -> Unit,
    onDelete: (Task) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(tasks) { task ->
            TaskItemRow(
                task = task,
                onToggleComplete = { onToggleComplete(task) },
                onDelete = { onDelete(task) }
            )
        }
    }
}

@Composable
fun TaskItemRow(
    task: Task,
    onToggleComplete: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF7F8FA), RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = if (task.isCompleted) Color(0xFF6254E8) else Color.Transparent,
                        shape = CircleShape
                    )
                    .border(2.dp, Color(0xFF6254E8), CircleShape)
                    .clickable { onToggleComplete() },
                contentAlignment = Alignment.Center
            ) {
                if (task.isCompleted) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Completed",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = task.title,
                fontSize = 16.sp,
                color = if (task.isCompleted) Color(0xFF9EA4B8) else Color(0xFF17203A),
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null,
                modifier = Modifier.weight(1f)
            )
        }

        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete task",
            tint = Color(0xFF8A91A5),
            modifier = Modifier
                .size(24.dp)
                .clickable { onDelete() }
        )
    }
}

@Composable
fun ThemeToggleIcon(

){
    Icon(imageVector = Icons.Default.WbSunny,"Toggle theme" , tint = Color.Black,
        modifier = Modifier
            .size(24.dp)
            .clickable {}
    )

}
@Composable
fun Textformfie(
    value: String,
    onValueChange: (String) -> Unit,
    onAdd: () -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,

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
                        color = Color(0xFF6254E8),
                        shape = CircleShape
                    )
                    .clickable { onAdd() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Add task",
                    tint = Color.White
                )
            }
        },

        singleLine = true,

        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp),

        shape = RoundedCornerShape(18.dp),

        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFE3E6F0),
            unfocusedBorderColor = Color(0xFFE3E6F0),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}