package ru.netology.motivationlist.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import ru.netology.motivationlist.dto.Motivation
import ru.netology.motivationlist.entity.MotivationEntity

interface MotivationRepository {
    fun getAll(): LiveData<MutableList<Motivation>>
    fun getPaged(): LiveData<PagedList<Motivation>>
    fun likeUp(id: Long)
    fun likeDown(id: Long)
    fun share(id: Long)
    fun remove(id: Long)
    fun saveMotivation(motivation: Motivation)
    fun getName(name:String): MutableList<Motivation>
}