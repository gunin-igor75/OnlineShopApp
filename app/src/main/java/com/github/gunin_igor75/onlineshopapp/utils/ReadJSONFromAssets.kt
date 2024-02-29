package com.github.gunin_igor75.onlineshopapp.utils

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader

private const val TAG = "ReadJSONFromAssets"

fun readJsonFromAssets(context: Context, path: String): String {

    return try {
        val file = context.assets.open(path)
        val bufferedReader = BufferedReader(InputStreamReader(file))
        val stringBuilder = StringBuilder()
        bufferedReader.useLines { lines ->
            lines.forEach {
                stringBuilder.append(it)
            }
        }
        stringBuilder.toString()
    } catch (e: Exception) {
        Log.e(TAG, "Error reading JSON: $e.")
        e.printStackTrace()
        ""
    }
}