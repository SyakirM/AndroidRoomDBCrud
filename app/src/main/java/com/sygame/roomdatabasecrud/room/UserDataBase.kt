package com.sygame.roomdatabasecrud.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [User::class],
    version = 1
)
abstract class UserDataBase: RoomDatabase() {
    abstract fun userDao(): Dao

    companion object{
        @Volatile private var instance: UserDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance?: buildDataBase(context).also {
                instance = it
            }
        }

        private fun buildDataBase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            UserDataBase::class.java,
            "user.db"
        ).build()
    }
}