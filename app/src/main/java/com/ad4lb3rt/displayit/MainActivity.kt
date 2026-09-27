package com.ad4lb3rt.displayit

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.ad4lb3rt.displayit.ui.theme.DisplayItTheme

class MainActivity : ComponentActivity()
{
    val TEXT = "Sample Text!"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DisplayItTheme {
                val configuration = LocalConfiguration.current
                val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
                val insetsController = WindowCompat.getInsetsController(window, window.decorView)
                val screenWidth = LocalWindowInfo.current.containerSize.width
                val screenHeight = LocalWindowInfo.current.containerSize.height

                LaunchedEffect(isLandscape) {
                    if (isLandscape) {
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

                    if (!isLandscape) {
                        DisplayText(TEXT)
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .windowInsetsPadding(WindowInsets.safeDrawing)
                        ) {
                            Button(
                                onClick = {
                                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                                },
                                modifier = Modifier.align(Alignment.TopEnd)
                            ) {
                                Text(text = "Show")
                            }
                        }
                    } else {
                        DisplayClickableText(
                            text = TEXT,
                            onClick = {
                                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                            },
                            screenWidthSp = screenWidth.sp,
                            screenHeightSp = screenHeight.sp
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun DisplayText(text: String, modifier: Modifier = Modifier)
    {
        Surface(color = MaterialTheme.colorScheme.background)
        {
            Text(
                color = MaterialTheme.colorScheme.primary,
                text = text,
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                modifier = modifier
                    .padding(24.dp)
                    .fillMaxSize()
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )
        }
    }

    @Composable
    fun DisplayClickableText(text: String, modifier: Modifier = Modifier,
                             onClick: () -> Unit = {},
                             screenWidthSp: TextUnit, screenHeightSp: TextUnit)
    {
        val interactionSource = remember { MutableInteractionSource() }
        Surface(color = Color.Transparent)
        {
            Text(
                color = MaterialTheme.colorScheme.primary,
                text = text,
                textAlign = TextAlign.Center,
                autoSize = TextAutoSize.StepBased(
                ),
                modifier = modifier
                    .clickable(interactionSource = interactionSource, indication = null) {
                        onClick()
                    }
                    .fillMaxSize()
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview()
    {
        DisplayItTheme{
            DisplayText(TEXT)
            Box(modifier = Modifier.fillMaxSize())
            {
                Button(onClick = {
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .absoluteOffset(x = (-10).dp, y = 10.dp)
                )
                {
                    Text(text = "Show")
                }
            }
        }
    }
}