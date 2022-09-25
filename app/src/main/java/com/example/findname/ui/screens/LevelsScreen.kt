package com.example.findname.ui.screens

import com.example.findname.source.models.TaskModel

interface LevelsScreen {

    fun initAdapter(list : List<TaskModel>)

    fun goToGameScreen(taskModel: TaskModel)

}