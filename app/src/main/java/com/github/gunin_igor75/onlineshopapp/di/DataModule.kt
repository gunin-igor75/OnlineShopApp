package com.github.gunin_igor75.onlineshopapp.di

import android.content.Context
import com.github.gunin_igor75.onlineshopapp.data.local.db.AppDatabase
import com.github.gunin_igor75.onlineshopapp.data.local.db.ItemDao
import com.github.gunin_igor75.onlineshopapp.data.local.db.UserDao
import com.github.gunin_igor75.onlineshopapp.data.repository.ItemRepositoryImpl
import com.github.gunin_igor75.onlineshopapp.data.repository.UserRepositoryImpl
import com.github.gunin_igor75.onlineshopapp.domain.repository.ItemRepository
import com.github.gunin_igor75.onlineshopapp.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @[Binds ApplicationScope]
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @[Binds ApplicationScope]
    fun bindItemRepository(impl: ItemRepositoryImpl): ItemRepository

    companion object {
        @[Provides ApplicationScope]
        fun provideAppDatabase(context: Context): AppDatabase {
            return AppDatabase.getInstance(context)
        }

        @[Provides ApplicationScope]
        fun provideUserDao(appDatabase: AppDatabase): UserDao {
            return appDatabase.getUserDao()
        }

        @[Provides ApplicationScope]
        fun provideItemDao(appDatabase: AppDatabase): ItemDao {
            return appDatabase.getItemDao()
        }
    }
}