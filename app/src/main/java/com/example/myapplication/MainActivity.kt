package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Captain()
                }
            }
        }
    }
}

@Composable
fun Captain() {
    val treasure = remember { mutableStateOf(0) }
    val direction = remember { mutableStateOf("north") }
    val showCongrats = remember { mutableStateOf(false) }

    LaunchedEffect(treasure.value) {
        if (treasure.value >= 3) {
            showCongrats.value = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (showCongrats.value) {
            CongratsMessage()
        } else {
            Text(
                text = "Treasure Hunt",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Treasure found: ${treasure.value}",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Current direction is: ${direction.value}",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            DirectionButton(direction, treasure, "Sail east", "east")
            DirectionButton(direction, treasure, "Sail north", "north")
            DirectionButton(direction, treasure, "Sail west", "west")
            DirectionButton(direction, treasure, "Sail south", "south")
        }
    }
}

@Composable
fun DirectionButton(direction: MutableState<String>, treasure: MutableState<Int>, buttonText: String, newDirection: String) {
    Button(
        onClick = {
            direction.value = newDirection
            if (Random.nextBoolean()) {
                treasure.value += 1
            }
        },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Text(
            text = buttonText,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }

    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun CongratsMessage() {
    val size by animateDpAsState(targetValue = 200.dp)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(Color.Yellow)
            .padding(16.dp)
    ) {
        Text(
            text = "Ayee Captain!",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Captain()
    }
}
