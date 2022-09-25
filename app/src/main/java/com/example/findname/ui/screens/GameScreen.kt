package com.example.findname.ui.screens

import com.example.findname.source.models.TaskModel

interface GameScreen {

    fun initViews(task: TaskModel, score: Int)

    fun loadFirstQuestion(task: TaskModel)

    fun loadQuestion(task: TaskModel)

    fun onCorrectAnswer()

    fun onWrongAnswer()

    fun onHelpClicked()

    fun onAnswerOpenClicked()

    fun onShareClicked()

    fun onBack()

    fun startFireWorks()

}