package com.example.swim_1.DogDatabase

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData



class DogRepository(application : Application) {

    val database : DogDatabase = DogDatabase.getDatabase(application)


    fun insertNewDog(dog : DogInfo) {
        InsertNewDogTask(database.dogDao()).execute(dog)
    }

    fun getDogs() : LiveData<List<DogInfo>> {
        return database.dogDao().getAll()
    }

    fun removeAllDogs() {
        DeleteAllTask(database.dogDao()).execute()
    }

    private class InsertNewDogTask (private val dogDao: DogDao) : AsyncTask<DogInfo, Unit, Unit>() {
        override fun doInBackground(vararg params: DogInfo) {
            dogDao.insertAll(params[0])
        }
    }

    private class DeleteAllTask (private val dogDao: DogDao) : AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg params: Unit) {
            dogDao.deleteAll()
        }
    }
}