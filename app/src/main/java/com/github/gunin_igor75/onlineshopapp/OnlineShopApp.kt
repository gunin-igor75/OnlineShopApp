package com.github.gunin_igor75.onlineshopapp

import android.app.Application
import com.github.gunin_igor75.onlineshopapp.di.ApplicationComponent
import com.github.gunin_igor75.onlineshopapp.di.DaggerApplicationComponent

class OnlineShopApp: Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.factory().create(this)
    }
}