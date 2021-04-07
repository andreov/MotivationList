package ru.netology.motivationlist.repository


import androidx.paging.DataSource
import ru.netology.motivationlist.dto.Motivation


interface MotivationRepository {
    fun getPaged(): DataSource.Factory<Int, Motivation>
    fun likeUp(id: Long)
    fun likeDown(id: Long)
    fun share(id: Long)
    fun remove(id: Long)
    fun saveMotivation(motivation: Motivation)
    fun getName(name:String): DataSource.Factory<Int, Motivation>
}