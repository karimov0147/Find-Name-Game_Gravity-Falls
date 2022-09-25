package com.example.findname.source.models

import java.io.Serializable

data class TaskModel(
    var id: Int,
    var image : String,
    var answer : String,
    var variants : String,
    var state : String
) : Serializable