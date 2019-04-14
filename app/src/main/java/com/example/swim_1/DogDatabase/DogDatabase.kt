package com.example.swim_1.DogDatabase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(DogInfo::class), version = 1)
abstract class DogDatabase : RoomDatabase() {
    abstract fun dogDao() : DogDao
}