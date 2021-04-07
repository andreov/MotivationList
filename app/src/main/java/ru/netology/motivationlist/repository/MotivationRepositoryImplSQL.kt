package ru.netology.motivationlist.repository


import androidx.paging.DataSource
import ru.netology.motivationlist.dao.MotivationDao
import ru.netology.motivationlist.dto.Motivation
import ru.netology.motivationlist.entity.MotivationEntity

class MotivationRepositoryImplSQL(private val dao: MotivationDao) : MotivationRepository {


    override fun getPaged(): DataSource.Factory<Int, Motivation> {
        return dao.getAllPaged().map { entity ->
            entity.toDto()
        }
    }

    override fun saveMotivation(motivation: Motivation) {
        if (motivation.id == 0L) {
            dao.saveMotivation(MotivationEntity.fromDto(motivation))
        }
    }

    override fun getName(name: String): DataSource.Factory <Int, Motivation> {
        return dao.getName(name).map { entity ->
            entity.toDto()
        }
    }

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