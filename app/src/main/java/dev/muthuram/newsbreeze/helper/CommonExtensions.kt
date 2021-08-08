package dev.muthuram.newsbreeze.helper

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.core.content.getSystemService
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("MissingPermission")
@Suppress("DEPRECATION")
fun Context.isInternetAvailable(): Boolean? {
    val cm = getSystemService<ConnectivityManager>()
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        cm?.run {
            getNetworkCapabilities(this.activeNetwork)?.run {
                when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        }
    } else {
        return cm?.run {
            activeNetworkInfo?.run {
                this.isConnected
            }
        }
    }
}

fun ViewGroup.inflate(@LayoutRes id: Int): View =
    LayoutInflater.from(this.context).inflate(id, this, false)


inline fun <T> T?.ifNotNull(block: (T) -> Unit): Boolean {
    return if (this !== null) {
        block(this)
        true
    } else false
}

inline fun <T> T?.ifNull(block: () -> Unit): Boolean {
    return if (this === null) {
        block()
        true
    } else false
}
fun String?.defaultValue(defaultValue: String = "") = this ?: defaultValue

fun Int?.defaultValue(defaultValue: Int = 0) = this ?: defaultValue

fun Boolean?.defaultValue(defaultValue: Boolean = false) = this ?: defaultValue

fun <T> List<T>.toArrayList(): ArrayList<T>{
    return ArrayList(this)
}

fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    formatter.timeZone = timeZone
    return formatter.format(this)
}

fun EditText.afterTextChangeListener(text: (String) -> Unit) {
    addTextChangedListener(object : MyTextWatcher() {
        override fun afterTextChanged(s: Editable) {
            text(s.toString())
        }
    })
}