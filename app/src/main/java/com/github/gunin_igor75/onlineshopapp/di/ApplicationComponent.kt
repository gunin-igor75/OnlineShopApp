package com.github.gunin_igor75.onlineshopapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DataModule::class
    ]
)
@ApplicationScope
interface ApplicationComponent {

    @Component.Factory
    interface Factory {

        fun create(
           @BindsInstance context: Context
        ): ApplicationComponent
    }
}