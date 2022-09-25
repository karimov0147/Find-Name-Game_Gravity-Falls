package com.example.findname.ui.presenters

import com.example.findname.source.models.TaskModel

interface LevelsPresenter {

     fun initUiComponents()

    fun onClickLevel(task : TaskModel)

}