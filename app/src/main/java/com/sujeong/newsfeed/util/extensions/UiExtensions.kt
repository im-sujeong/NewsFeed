package com.sujeong.newsfeed.util.extensions

import android.content.res.Resources

fun Int.toPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}