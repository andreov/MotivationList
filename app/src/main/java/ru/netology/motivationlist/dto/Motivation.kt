package ru.netology.motivationlist.dto


data class Motivation(
        val id: Long,
        val author: String,
        val content: String,
        val published: String,
        val countLike: Long = 0,
        val countShare: Long = 0,
        val urlContent: String = "",
        val urlImage: String = ""
)
