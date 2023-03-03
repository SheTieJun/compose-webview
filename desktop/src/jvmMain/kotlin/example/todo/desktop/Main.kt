package example.todo.desktop

import LoadingState.Finished
import LoadingState.Initializing
import LoadingState.Loading
import WebView
import androidx.compose.desktop.DesktopTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import rememberWebViewNavigator
import rememberWebViewState


fun main() {
    application {
        val windowState = rememberWindowState(size = DpSize(1400.dp, 1000.dp))
        val webViewState = rememberWebViewState("https://baidu.com")
        val icon = painterResource("jenkins_logo.png")
        Window(
            onCloseRequest = { exitApplication() },
            state = windowState,
            title = when (webViewState.loadingState) {
                Finished -> webViewState.pageTitle ?: "WebView"
                Initializing -> "初始化成功"
                is Loading -> "加载成功${((webViewState.loadingState as Loading).progress * 100f).toInt()}%"
            }, icon = icon
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {
                MaterialTheme {
                    DesktopTheme {
                        WebView(
                            state = webViewState,
                            modifier = Modifier.fillMaxSize(),
                            navigator = rememberWebViewNavigator()
                        )
                    }
                }
            }
        }
    }
}

