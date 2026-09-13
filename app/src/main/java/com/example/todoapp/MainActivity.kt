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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.foundation.layout.Box
import  androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape

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
    var task by remember { mutableStateOf("") }

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
        Textformfie()
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
fun Textformfie() {

    var task by remember { mutableStateOf("") }

    OutlinedTextField(
        value = task,
        onValueChange = {
            task = it
        },

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
                    ),
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