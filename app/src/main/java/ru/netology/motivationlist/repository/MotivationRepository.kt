package ru.netology.motivationlist.repository

import androidx.lifecycle.LiveData
import ru.netology.motivationlist.dto.Motivation

interface MotivationRepository {
    fun getAll(): LiveData<List<Motivation>>
    fun likeUp(id:Long)
    fun likeDown(id:Long)
    fun share(id:Long)
    //fun remove(id:Long)
    fun savePost(motivation: Motivation)
}