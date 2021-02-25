package ru.netology.motivationlist.repository

import androidx.lifecycle.Transformations
import ru.netology.motivationlist.dao.MotivationDao
import ru.netology.motivationlist.dto.Motivation
import ru.netology.motivationlist.entity.MotivationEntity

class MotivationRepositoryImplSQL(private val dao: MotivationDao) : MotivationRepository {

    override fun getAll() = Transformations.map(dao.getAll()) { list ->
        list.map { entity ->
            entity.toDto()
        }
    }

    override fun savePost(motivation: Motivation) {
        if (motivation.id == 0L) {
            //post.author = "AndreOv"
            //post.published = "12 december 10:12"
            dao.savePost(MotivationEntity.fromDto(motivation))
        } //else dao.editPost(MotivationEntity.fromDto(motivation)

    }

    override fun likeUp(id: Long) {
        dao.likeUp(id)
    }

    override fun likeDown(id: Long) {
        dao.likeDown(id)
    }

//    override fun remove(id: Long) {
//        dao.remove(id)
//    }

    override fun share(id: Long) {
        dao.share(id)
    }

}