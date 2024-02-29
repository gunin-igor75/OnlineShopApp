package com.github.gunin_igor75.onlineshopapp.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.gunin_igor75.onlineshopapp.data.local.model.UserDbModel
import com.github.gunin_igor75.onlineshopapp.data.local.model.UserItemDbModel

@Database(
    version = 1,
    entities = [
        UserDbModel::class,
        UserItemDbModel::class
    ],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    abstract fun getItemDao(): ItemDao

    companion object {
        private const val DB_NAME = "AppDatabase"
        private var INSTANCE: AppDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): AppDatabase {
            INSTANCE?.let { return it }

            synchronized(lock) {
                INSTANCE?.let { return it }
                val database = Room.databaseBuilder(
                    context = context,
                    klass = AppDatabase::class.java,
                    name = DB_NAME
                ).build()
                INSTANCE = database
                return database
            }
        }
    }
}