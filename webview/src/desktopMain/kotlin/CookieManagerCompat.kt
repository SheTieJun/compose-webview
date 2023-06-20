import java.net.CookieHandler
import java.net.URI

actual object CookieManagerCompat{

    actual   fun setCookie(url: String,  headers: HashMap<String, String>) {
        val uri: URI = URI.create(url)
        val stringList = headers.map {
            "${it.key}=${it.value}"
        }
        val headers: MutableMap<String, List<String>> = LinkedHashMap()
        headers["Set-Cookie"] = stringList
        CookieHandler.getDefault().put(uri, headers)
    }



    actual fun getCookie(uri: URI): MutableMap<String, String>? {
        return CookieHandler.getDefault().get(uri,HashMap()).map {
            it.key to it.value[0]
        }.toMap().toMutableMap()
    }

}