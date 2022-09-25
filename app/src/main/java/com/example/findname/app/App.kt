package com.example.findname.app

import android.app.Application
import com.example.findname.repository.GameRepository
import com.example.findname.repository.impl.GameRepositoryImpl
import com.example.findname.source.dataBase.AppDatabase
import com.example.findname.source.frameworkStorage.SharedPreferences

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPreferences.initSharedPreferences(this)
        AppDatabase.initDataBase(this)
        GameRepositoryImpl.init()
    }
}