package com.example.swim_1.DogDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DogDao {
    @Query("SELECT * FROM doginfo")
    fun getAll(): List<DogInfo>

    @Insert
    fun insertAll(vararg dogs: DogInfo)

    @Delete
    fun delete(dog: DogInfo)
}