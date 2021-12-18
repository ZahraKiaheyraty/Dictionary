package ir.afagh.dictionary.common

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.lifecycle.SavedStateHandle

inline fun<reified T> SavedStateHandle.sendArgument(data:T): T? {
    return get<T>(data.toString()).let { it }
}

fun String.textPersian(): Boolean {
    for (i in 0 until Character.codePointCount(this, 0, this.length)) {
        val c = this.codePointAt(i)
        if (c in 0x0600..0x06FF || c == 0xFB8A || c == 0x067E || c == 0x0686 || c == 0x06AF) return true
    }
    return false
}

fun Int.kindWord():String{
    return when (this){
        1 -> "Noun"
        2->  "Verb"
        else -> ""
    }
}

fun Context.getVersionName():String{
    return this.packageManager.getPackageInfo(this.packageName,PackageManager.GET_ACTIVITIES).versionName

}