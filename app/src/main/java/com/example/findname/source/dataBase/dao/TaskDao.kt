package com.example.findname.source.dataBase.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.findname.source.dataBase.entity.TaskEntity
import com.example.findname.source.models.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.selects.select

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun getAllTask(): List<TaskModel>

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getTaskById(id: Int): TaskModel

    @Query("UPDATE tasks SET state = :state WHERE id = :id")
    fun updateTask(id: Int, state: String)
}