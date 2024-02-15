package com.github.gunin_igor75.onlineshopapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.github.gunin_igor75.onlineshopapp.OnlineShopApp
import com.github.gunin_igor75.onlineshopapp.domain.entity.Item.Companion.ITEM_DEFAULT
import com.github.gunin_igor75.onlineshopapp.domain.entity.User
import com.github.gunin_igor75.onlineshopapp.domain.entity.User.Companion.USER_DEFAULT
import com.github.gunin_igor75.onlineshopapp.presentation.main.catalogs.details.DefaultDetailsComponent
import com.github.gunin_igor75.onlineshopapp.presentation.main.catalogs.details.DetailsContent
import com.github.gunin_igor75.onlineshopapp.presentation.root.DefaultRootComponent
import com.github.gunin_igor75.onlineshopapp.presentation.root.RootContent
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.OnlineShopAppTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

//    @Inject
//    lateinit var defaultRootComponentFactory: DefaultRootComponent.Factory

    @Inject
    lateinit var defaultDetailsComponent: DefaultDetailsComponent.Factory
    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext  as OnlineShopApp).component.inject(this)
        super.onCreate(savedInstanceState)
//        val jsonString = readJsonFromAssets(baseContext, "data.json")
//        val data = Gson().fromJson(jsonString, UIContentDto::class.java)
//        Log.d("MainActivity", data.toString())

        val component = defaultDetailsComponent.create(
            user = USER_DEFAULT,
            item = ITEM_DEFAULT,
            onBackClicked = {},
            componentContext = defaultComponentContext()
        )
        setContent {
            OnlineShopAppTheme {
                DetailsContent(
                    component = component
                )
            }
        }
    }
}

