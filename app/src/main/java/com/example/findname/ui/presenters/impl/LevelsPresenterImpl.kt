package com.example.findname.ui.presenters.impl

import com.example.Storage
import com.example.findname.repository.GameRepository
import com.example.findname.source.models.TaskModel
import com.example.findname.ui.presenters.LevelsPresenter
import com.example.findname.ui.screens.LevelsScreen

class LevelsPresenterImpl(var repository: GameRepository , var view : LevelsScreen) : LevelsPresenter{

    override  fun initUiComponents() {
        var list = repository.getAllTasks()
        view.initAdapter(list)
    }

    override fun onClickLevel(task: TaskModel) {
        view.goToGameScreen(task)
    }

}
