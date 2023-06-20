import android.webkit.CookieManager
import java.net.URI

actual object CookieManagerCompat {
    actual fun getCookie(uri: URI): MutableMap<String, String>? {
        CookieManager.getInstance().getCookie(uri.toString())?.let {
            val map = mutableMapOf<String, String>()
            it.split(";").forEach { cookie ->
                val pair = cookie.split("=")
                if (pair.size == 2) {
                    map[pair[0]] = pair[1]
                }
            }
            return map
        }
        return mutableMapOf()
    }


    actual fun setCookie(url: String, headers: HashMap<String, String>) {
        val cookieManager: CookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        headers.onEach { entry ->
            cookieManager.setCookie(url, entry.key+"="+entry.value)
        }
        cookieManager.flush()
    }
}