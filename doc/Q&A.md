# Q&A

## Why release Desktop App WebView no Work?
**The Gradle plugin does not automatically determine the necessary JDK modules.**

so,we should use `suggestModules` task to get need modules

## How to Save the State of a Web Page ?
I try use datastore save set Cookie ,but some Web Page it is not useful

~ 这是一个错误的想法 it's a wrong idea

`CookieManagerCompat` 添加`setCookie` 和 `getCookie` 方法来保存和获取Cookie，进行持久化

Replace the original idea, use `CookieManagerCompat` to add `setCookie` and `getCookie` methods to save and get cookies for persistence