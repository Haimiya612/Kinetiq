package com.kinetiq.player

import android.util.Log

/**
 * Central logging boundary. Never pass credentials, cookies, signed URLs,
 * request headers, or personal media paths to these methods.
 */
object KinetiqLogger {
    fun info(tag: String, message: String) {
        Log.i("Kinetiq/$tag", message)
    }

    fun warn(tag: String, message: String, error: Throwable? = null) {
        if (error == null) Log.w("Kinetiq/$tag", message)
        else Log.w("Kinetiq/$tag", message, error)
    }

    fun error(tag: String, message: String, error: Throwable? = null) {
        if (error == null) Log.e("Kinetiq/$tag", message)
        else Log.e("Kinetiq/$tag", message, error)
    }
}
