package com.example.findname.source.dataBase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity (
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id : Int,
    @ColumnInfo(name = "answer")
    var answer : String,
    @ColumnInfo(name = "image")
    var image : String,
    @ColumnInfo(name = "variants")
    var variants : String,
    @ColumnInfo(name = "state")
    var state : String
)