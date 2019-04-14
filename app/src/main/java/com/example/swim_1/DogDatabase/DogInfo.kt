package com.example.swim_1.DogDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class DogInfo (@PrimaryKey(autoGenerate = true)
               val uid: Int,
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "photo") val pathToPhoto : String,
    @ColumnInfo(name = "can_fetch") val canFetch : Boolean)