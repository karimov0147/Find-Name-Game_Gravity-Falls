package com.example.findname.ui.presenters

import com.example.findname.source.models.TaskModel

interface GamePresenter {

    fun onBack()

    fun setScore(score : Int)

    fun getScore() : Int

    fun initViews(task: TaskModel)

    fun getStartLevel(task: TaskModel)

    fun onCompleteLevel(taskId : Int)

    fun loadTask(taskId : Int)

    fun onCorrectAnswer()

    fun onWrongAnswer()

    fun onHelpClicked()

    fun onAnswerClicked()

    fun onShareClicked()

    fun startFireWorks()

}