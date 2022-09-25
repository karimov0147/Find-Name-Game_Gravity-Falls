package com.example.findname.source.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.findname.source.dataBase.dao.TaskDao
import com.example.findname.source.dataBase.entity.TaskEntity

@Database(entities = [TaskEntity::class] , version = 1)
abstract class AppDatabase  : RoomDatabase() {

    abstract fun getTaskDao() : TaskDao

    companion object{
        private var appDatabase : AppDatabase? = null

        fun initDataBase(context: Context){
            appDatabase = Room.databaseBuilder(context , AppDatabase::class.java , "databese.db")
                .createFromAsset("task.db")
                .allowMainThreadQueries()
                .build()
        }

       fun getInstance() = appDatabase!!
    }


}