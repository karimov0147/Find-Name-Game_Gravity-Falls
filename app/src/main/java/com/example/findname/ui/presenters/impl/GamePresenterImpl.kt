package com.example.findname.ui.presenters.impl

import com.example.findname.repository.GameRepository
import com.example.findname.source.models.TaskModel
import com.example.findname.ui.presenters.GamePresenter
import com.example.findname.ui.screens.GameScreen

class GamePresenterImpl(var repository : GameRepository , var view : GameScreen) :GamePresenter {

    override fun setScore(score: Int) {
        repository.setScore(score)
    }

    override fun getScore(): Int {
       return repository.getScore()
    }


    override fun initViews(task: TaskModel) {
        view.initViews(task ,
            repository.getScore()
        )
    }


    override fun getStartLevel(task: TaskModel) {
        view.loadFirstQuestion(task)
    }

    override fun onCompleteLevel(taskId: Int) {
        repository.onCompleteTask(taskId)
    }


    override fun loadTask(taskId: Int ) {
       view.loadQuestion(repository.getTask(taskId + 1))
    }

    override fun onCorrectAnswer() {
        view.onCorrectAnswer()
    }

    override fun onWrongAnswer() {
        view.onWrongAnswer()
    }

    override fun onBack() {
        view.onBack()
    }

    override fun onHelpClicked() {
        view.onHelpClicked()
    }

    override fun onAnswerClicked() {
        view.onAnswerOpenClicked()
    }

    override fun onShareClicked() {
        view.onShareClicked()
    }

    override fun startFireWorks() {
        view.startFireWorks()
    }
}