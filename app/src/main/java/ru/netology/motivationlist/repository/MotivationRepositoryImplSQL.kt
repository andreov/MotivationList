package ru.netology.motivationlist.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

import ru.netology.motivationlist.dao.MotivationDao
import ru.netology.motivationlist.db.AppDb
import ru.netology.motivationlist.dto.Motivation
import ru.netology.motivationlist.entity.MotivationEntity
import ru.netology.motivationlist.viewModel.MotivationViewModel

class MotivationRepositoryImplSQL(private val dao: MotivationDao) : MotivationRepository {

    override fun getAll() = Transformations.map(dao.getAll()) { mutableList ->
        mutableList.map { entity ->
            entity.toDto()
        }.toMutableList()
    }

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

//    override fun getName(name: String): PagedList<Motivation> {
//        val factory: DataSource.Factory<Int, Motivation> = dao.getName(name).map { entity ->
//            entity.toDto()
//            }
//        val pagedListBuilder: LivePagedListBuilder<Int, Motivation> = LivePagedListBuilder(factory,5)
//        var data: LiveData<PagedList<Motivation>> = pagedListBuilder.build()
//        return data.value
//    }

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