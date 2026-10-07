package com.ad4lb3rt.displayit

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.ad4lb3rt.displayit.ui.theme.DisplayItTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.em


class MainActivity : ComponentActivity()
{

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DisplayItTheme {
                var displayText by rememberSaveable { mutableStateOf("") }
                var isDisplaying by rememberSaveable { mutableStateOf(false) }
                val insetsController = WindowCompat.getInsetsController(window, window.decorView)

            LaunchedEffect(isDisplaying) {
                    if (isDisplaying) {
                        insetsController.hide(WindowInsetsCompat.Type.systemBars())
                        insetsController.systemBarsBehavior =
                            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                    } else {
                        insetsController.show(WindowInsetsCompat.Type.systemBars())
                    }
                }

                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize()
                )
                {

                    if (!isDisplaying) {
                        DisplayTextField(displayText, "Write here...",
                            onValueChange = { displayText = it })
                        ShowButton(onClick = {
                            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
                            isDisplaying = true
                        })
                    } else {
                        DisplayClickableText(
                            text = displayText,
                            onClick = {
                                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                                isDisplaying = false
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun DisplayTextField(displayText: String, placeholderText: String, onValueChange: (String) -> Unit)
    {
        Box(modifier = Modifier.fillMaxSize())
        {
            TextField(
                value=displayText,
                placeholder = { Text(
                    text=placeholderText,
                    fontSize = 40.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(align = Alignment.CenterVertically)
                )},
                onValueChange = onValueChange,
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 40.sp,
                    lineHeight = 1.em
                ),
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxHeight()
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )
        }
    }

    @Composable
    fun DisplayClickableText(text: String, modifier: Modifier = Modifier,
                             onClick: () -> Unit = {})
    {
        val interactionSource = remember { MutableInteractionSource() }
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .clickable(interactionSource = interactionSource, indication = null) {
                onClick()
            },
            )
        {
            Text(
                color = MaterialTheme.colorScheme.primary,
                text = text,
                textAlign = TextAlign.Center,
                autoSize = TextAutoSize.StepBased(maxFontSize = 428.sp, minFontSize = 1.sp, stepSize = (0.1).sp),
                lineHeight = 1.em
            )
        }
    }

    @Composable
    fun ShowButton(onClick: () -> Unit)
    {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
        ) {
            Button(
                onClick = onClick,
                modifier = Modifier.align(Alignment.TopEnd).offset((-15).dp, 0.dp)
            ) {
                Text(text = "Show")
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview()
    {
        DisplayItTheme{
            DisplayTextField("", "Write here...") {}
            ShowButton {}
        }
    }
}