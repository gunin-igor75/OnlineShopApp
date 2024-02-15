package com.github.gunin_igor75.onlineshopapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.github.gunin_igor75.onlineshopapp.presentation.root.DefaultRootComponent
import com.github.gunin_igor75.onlineshopapp.presentation.root.RootContent
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.OnlineShopAppTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var defaultRootComponentFactory: DefaultRootComponent.Factory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val jsonString = readJsonFromAssets(baseContext, "data.json")
//        val data = Gson().fromJson(jsonString, UIContentDto::class.java)
//        Log.d("MainActivity", data.toString())

        setContent {
            OnlineShopAppTheme {
                RootContent(
                    component = defaultRootComponentFactory.create(
                        componentContext = defaultComponentContext()
                    )
                )
            }
        }
    }
}

