package com.example.findname.ui.presenters.impl

import android.util.Log
import androidx.lifecycle.*
import com.example.Storage
import com.example.findname.repository.GameRepository
import com.example.findname.source.models.TaskModel
import com.example.findname.ui.presenters.LevelsPresenter
import com.example.findname.ui.screens.LevelsScreen
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

class LevelsPresenterImpl(var repository: GameRepository , var view : LevelsScreen) : LevelsPresenter , CoroutineScope{

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private val scope = CoroutineScope(coroutineContext)

    override fun initUiComponents() {
        repository.getAllTasks()
//            .map { array.addAll(it) }
//            .filter { array.isNotEmpty() }
            .onEach { view.initAdapter(it) }
//            .flowOn(Dispatchers.Main)
            .launchIn(scope)
    }

    override fun onClickLevel(task: TaskModel) {
        view.goToGameScreen(task)
    }



    override fun clear() {
        this.job.cancel()
    }


}
