package ru.netology.motivationlist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.netology.motivationlist.dao.MotivationDao
import ru.netology.motivationlist.dto.Motivation
import ru.netology.motivationlist.entity.MotivationEntity

@Database(entities = [MotivationEntity::class], version = 1)
abstract class AppDb : RoomDatabase() {
    abstract fun motivationDao(): MotivationDao

    companion object {
        @Volatile
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context, AppDb::class.java, "motivationApp3.db")
                        .allowMainThreadQueries()
                        .build()
    }
}