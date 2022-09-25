package com.example.findname.ui.presenters.impl

import com.example.findname.ui.presenters.MenuPresenter
import com.example.findname.ui.screens.MenuScreen
import com.example.findname.ui.screens.impl.MenuScreenImpl

class MenuPresenterImpl(var view : MenuScreen ) : MenuPresenter {
    override fun onClickStartBtn() {
        view.goToLevelScreen()
    }

    override fun onStartView() {
        view.startAnimation()
    }

}