package com.github.gunin_igor75.onlineshopapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.OnlineShopAppTheme
import com.github.gunin_igor75.onlineshopapp.utils.readJsonFromAssets
import com.github.gunin_igor75.onlineshopapp.utils.UIContentDto
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val jsonString = readJsonFromAssets(baseContext, "data.json")
        val data = Gson().fromJson(jsonString, UIContentDto::class.java)
        Log.d("MainActivity", data.toString())
        setContent {
            OnlineShopAppTheme {
            }
        }
    }
}

