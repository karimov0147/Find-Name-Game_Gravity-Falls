package com.example.findname.ui.presenters

import androidx.lifecycle.LifecycleOwner
import com.example.findname.source.models.TaskModel

interface LevelsPresenter {

    fun initUiComponents()

    fun onClickLevel(task : TaskModel)

    fun clear()

}