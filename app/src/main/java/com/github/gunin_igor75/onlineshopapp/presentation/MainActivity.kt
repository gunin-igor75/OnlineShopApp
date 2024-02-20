package com.github.gunin_igor75.onlineshopapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.github.gunin_igor75.onlineshopapp.OnlineShopApp
import com.github.gunin_igor75.onlineshopapp.presentation.component.CardItem
import com.github.gunin_igor75.onlineshopapp.presentation.root.DefaultRootComponent
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.OnlineShopAppTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var defaultRootComponentFactory: DefaultRootComponent.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext  as OnlineShopApp).component.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            OnlineShopAppTheme {
//                RootContent(
//                    component = defaultRootComponentFactory.create(
//                        componentContext = defaultComponentContext()
//                    )
//                )
                CardItem()
            }
        }
    }
}

