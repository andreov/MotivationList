package ru.netology.motivationlist.viewModel

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.motivationlist.db.AppDb
import ru.netology.motivationlist.dto.Motivation
import ru.netology.motivationlist.entity.MotivationEntity
import ru.netology.motivationlist.repository.MotivationRepository
import ru.netology.motivationlist.repository.MotivationRepositoryImplSQL
import java.text.SimpleDateFormat
import java.util.*

private val empty = Motivation(   //data post для заполнения нового поста
    id = 0,
    author = "",
    content = "",
    published = SimpleDateFormat("dd-MM-yyyy").format(Date()).toString(),
    countShare = 0,
    countLike = 0,
    urlContent = "",
    urlImage = ""


)

class MotivationViewModel(application: Application) : AndroidViewModel(application) {


    private val repository: MotivationRepository = MotivationRepositoryImplSQL(
        AppDb.getInstance(application).motivationDao()
    )

    var data: LiveData<MutableList<Motivation>> = repository.getAll()

    var edited = MutableLiveData(empty)

    var dataName = MutableLiveData<MutableList<Motivation>>() //= MutableLiveData()
    val mediator = MediatorLiveData<Unit>()

    init {
        mediator.addSource(data, { dataName.value = it })
    }

    fun likeUp(id: Long) = repository.likeUp(id)
    fun likeDown(id: Long) = repository.likeDown(id)
    fun share(id: Long) = repository.share(id)
    fun remove(id: Long) = repository.remove(id)
    fun isClickName(motivation: Motivation) {
        mediator.removeSource(data)
        mediator.addSource(data, { dataName.value = repository.getName(motivation.author) })
    }

    fun removeFilter() {
        mediator.removeSource(data)
        mediator.addSource(data, { dataName.value = it })
    }

    fun saveMotivation() {                 //сохранение поста
        edited.value?.let {
            repository.saveMotivation(it)
        }
        edited.value = empty
    }

    fun changeContent(content: String) {   // изменение контента поста
        edited.value = edited.value?.copy(content = content)
    }

    fun changeUrlContent(content: String) {   // изменение url video
        val text = content.trim()
        edited.value = edited.value?.copy(urlContent = text)
    }

    fun changeUrlImage(content: String) {   // изменение url video
        val text = content.trim()
        edited.value = edited.value?.copy(urlImage = text)
    }

    fun changeAuthor(content: String) {   // изменение url video
        val text = content.trim()
        edited.value = edited.value?.copy(author = text)
    }
}