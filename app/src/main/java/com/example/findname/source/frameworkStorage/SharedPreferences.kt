package com.example.findname.source.frameworkStorage

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

object SharedPreferences {
    var sharedPreferences : SharedPreferences? = null

    fun initSharedPreferences(context: Context){
        if (sharedPreferences==null)
            sharedPreferences = context.getSharedPreferences("Score" , MODE_PRIVATE)
    }

    fun getScore() : Int {
       return sharedPreferences!!.getInt("score" ,  -1)
    }

    fun setScore(score : Int){
        sharedPreferences!!.edit()?.putInt("score" , score)?.apply()
    }




}