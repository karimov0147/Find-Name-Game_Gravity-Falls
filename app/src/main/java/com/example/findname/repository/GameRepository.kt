package com.example.findname.repository

import com.example.findname.source.dataBase.dao.TaskDao
import com.example.findname.source.models.TaskModel

interface GameRepository {
    fun getTask(level: Int) : TaskModel

    fun onCompleteTask(id: Int)

    fun nextTask(level:Int)

    fun getAllTasks() : List<TaskModel>

    fun getScore() : Int

    fun setScore(score : Int)
}