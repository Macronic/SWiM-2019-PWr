package com.example.swim_1.DogDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



@Database(entities = [DogInfo::class], version = 1)
abstract class DogDatabase : RoomDatabase() {
    abstract fun dogDao() : DogDao

    companion object {
        @Volatile
        private var database: DogDatabase? = null

        fun getDatabase(context: Context): DogDatabase {
            if (database == null) {
                synchronized(DogDatabase::class.java) {
                    if (database == null) {
                        database = Room.databaseBuilder(context, DogDatabase::class.java, "dog_database").build()
                    }
                }
            }
            return database!!
        }
    }
}