package com.ad4lb3rt.displayit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ad4lb3rt.displayit.ui.theme.DisplayItTheme

class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DisplayItTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background)
                {
                    Greeting("Sample Text")
                }
            }
        }
    }
}

@Composable
fun Greeting(text: String, modifier: Modifier = Modifier)
{
    Surface(color = Color.Black){
        Text(
            color = Color.White,
            text = text,
            fontSize = 40.sp,
            modifier = modifier.padding(24.dp).fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview()
{
    DisplayItTheme {
        Greeting("Sample Text")
    }
}