package ru.netology.motivationlist.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.motivationlist.dto.Motivation

interface MotivationRepository {
    fun getAll(): LiveData<MutableList<Motivation>>
    fun likeUp(id: Long)
    fun likeDown(id: Long)
    fun share(id: Long)
    fun remove(id: Long)
    fun saveMotivation(motivation: Motivation)
    fun getName(name:String): MutableList<Motivation>
}