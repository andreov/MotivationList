package ru.netology.motivationlist.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

import ru.netology.motivationlist.dao.MotivationDao
import ru.netology.motivationlist.dto.Motivation
import ru.netology.motivationlist.entity.MotivationEntity

class MotivationRepositoryImplSQL(private val dao: MotivationDao) : MotivationRepository {

//    override fun getAll() = dao.getAll().map { entity ->
//        entity.toDto()
//    }

    //private var name:String="aaa"


    override fun getAll() = Transformations.map(dao.getAll()) { mutableList ->
        mutableList.map { entity ->
            entity.toDto()
        }.toMutableList()
    }




    override fun saveMotivation(motivation: Motivation) {
        if (motivation.id == 0L) {
            //motivation.author = "AndreOv"
            dao.saveMotivation(MotivationEntity.fromDto(motivation))
        } //else dao.editPost(MotivationEntity.fromDto(motivation)

    }

    override fun getName(name:String) = dao.getName(name).map { entity ->
            entity.toDto()
        }.toMutableList()




    override fun likeUp(id: Long) {
        dao.likeUp(id)
    }

    override fun likeDown(id: Long) {
        dao.likeDown(id)
    }

    override fun remove(id: Long) {
        dao.remove(id)
    }

    override fun share(id: Long) {
        dao.share(id)
    }

}