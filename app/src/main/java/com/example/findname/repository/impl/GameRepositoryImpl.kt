package com.example.findname.repository.impl

import com.example.findname.repository.GameRepository
import com.example.findname.source.dataBase.AppDatabase
import com.example.findname.source.dataBase.dao.TaskDao
import com.example.findname.source.frameworkStorage.SharedPreferences
import com.example.findname.source.models.TaskModel

class GameRepositoryImpl : GameRepository {

    private var taskDao = AppDatabase.getInstance().getTaskDao()

    companion object {
        var repository : GameRepository? = null

        fun init(){
            repository = GameRepositoryImpl()
        }

        fun getInstance() = repository
    }

    override fun getTask(level: Int): TaskModel = taskDao.getTaskById(level)


    override fun onCompleteTask(id: Int) = taskDao.updateTask(id , "true")


    override fun nextTask(level: Int) {

    }

    override fun getAllTasks(): List<TaskModel> = taskDao.getAllTask()


    override fun getScore(): Int {
        if (SharedPreferences.getScore() == -1)
                SharedPreferences.setScore(10)

        return SharedPreferences.getScore()
    }

    override fun setScore(score: Int) {
        SharedPreferences.setScore(score)
    }


}