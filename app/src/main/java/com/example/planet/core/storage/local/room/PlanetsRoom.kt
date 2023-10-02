package com.example.planet.core.storage.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.planet.core.storage.local.room.dao.UserDao
import com.example.planet.core.storage.local.room.entitys.User

private const val DATABASE_NAME = "planet_database"

@Database(entities = [User::class], version = 1)
abstract class PlanetsRoom: RoomDatabase() {

    abstract fun getUsersDao(): UserDao

    companion object {
        private var instance: PlanetsRoom? = null

        @Synchronized
        fun getInstance(context: Context): PlanetsRoom {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, PlanetsRoom::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }
            return instance as PlanetsRoom
        }

        private val roomCallback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }
    }

}