import java.net.CookieHandler
import java.net.URI
import java.util.*
import javafx.scene.web.WebEngine
import javafx.scene.web.WebView
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap


fun WebView.load(url: String) {
    engine.load(url)
}

fun WebView.loadContent(content: String) {
    engine.loadContent(content)
}

fun WebView.stopLoading() {
    engine.stopLoading()
}

fun WebView.goForward() {
    engine.goForward()
}

fun WebView.goBack() {
    engine.goBack()
}

fun WebView.canGoBack(): Boolean {
    return engine.canGoBack()
}

fun WebView.canGoForward(): Boolean {
    return engine.canGoForward()
}


fun WebView.getCurrentUrl(): String? {
    return engine.getCurrentUrl()
}

fun WebView.goRoot() {
    engine.history.go(-engine.history.currentIndex)
}

fun WebView.executeScript(script: String) {
    engine.executeScript(script)
}


fun WebView.setCookie(url: String) {
    println("setCookie")
    kotlin.runCatching {
        val uri: URI = URI.create(url)
        DataStoreKit.getFirstBlock(uri.host, setOf<String>()).let {


            val headers: MutableMap<String, List<String>> = LinkedHashMap()
            headers["Set-Cookie"] = it.toList()
            println("url:$uri\n${it.toString()}")
            CookieHandler.getDefault().put(uri, headers)
        }
    }
}


fun WebView.getCookies() {
    println("getCookies")
    val cookieHandler = CookieHandler.getDefault()
    val uri = URI.create(engine.location)
    val mutableMap = cookieHandler.get(uri, HashMap<String, List<String>>())
    mutableMap["Cookie"]?.let {
        println("url:${uri.host}\n$it")
        DataStoreKit.saveBlock(uri.host,it.toSet())
    }
}


internal fun WebEngine.getCurrentUrl(): String? {
    if (history.entries.size <= 0) return null
    return history.entries[history.currentIndex].url
}

internal fun WebEngine.stopLoading() {
    loadWorker.cancel()
}

internal fun WebEngine.goForward() {
    if (canGoForward()) {
        history.go(1)
    }
}

internal fun WebEngine.goBack() {
    if (canGoBack()) {
        history.go(-1)
    }
}

internal fun WebEngine.canGoBack(): Boolean {
    return history.maxSize > 0 && history.currentIndex != 0
}

internal fun WebEngine.canGoForward(): Boolean {
    return history.maxSize > 0 && history.currentIndex != history.maxSize - 1
}




