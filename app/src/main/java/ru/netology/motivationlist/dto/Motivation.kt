package ru.netology.motivationlist.dto


data class Motivation(
        val id: Long,
        var author: String,
        val content: String,
        var published: String,
        val countLike: Long = 0,
        val countShare: Long = 0,
        val urlContent: String = "",
        val urlImage: String = ""
)
