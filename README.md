An example of Kotlin Multiplatform WebView app with shared Android/Desktop 

Libraries used:
- [JavaFx](https://openjfx.io) - JavaFX is an open source, next generation client application platform for desktop, mobile and embedded systems built on Java


development environment 
- Android Studio Chipmunk | 2021.2.1 Patch 1
- Java 16 (JDK version  must be greater than 15 When Release desktop App)
- kotlin.version=1.6.10
- compose.version=1.3.0

There are multiple modules:
- `:webview` - - Shared WebView Compose UI for Android( Android WebView) and Desktop(JavaFX WebView)
- `:android` - Android application
- `:desktop` - Desktop application

use in Android And Desktop

1. load url
```kotlin
val webViewState = rememberWebViewState("https://example.com")
WebView(
    state = webViewState,
    modifier = Modifier.fillMaxSize(),
    navigator = rememberWebViewNavigator()

)
```
2. load data
```kotlin
val webViewState = rememberWebViewStateWithHTMLData("test")
WebView(
    state = webViewState,
    modifier = Modifier.fillMaxSize(),
    navigator = rememberWebViewNavigator()
)
```


thanks **https://google.github.io/accompanist/web/ **

### [**Q&A**](doc/Q&A.md)

![](image/wepapp_Compose.jpg)


