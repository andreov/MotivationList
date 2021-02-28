package ru.netology.motivationlist.dto

import java.text.SimpleDateFormat
import java.util.*

data class Motivation(
        val id: Long,
        var author: String,
        val content: String,
        var published: String,// = SimpleDateFormat("dd-MM-yyyy").format(Date()).toString(),
        val countLike: Long = 0,
        val countShare: Long = 0,
        val urlContent: String = ""
)
