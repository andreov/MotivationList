package ru.netology.motivationlist.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import ru.netology.motivationlist.db.AppDb
import ru.netology.motivationlist.dto.Motivation
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

    val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(4)
        .build()

    var dataPaging: LiveData<PagedList<Motivation>> = LivePagedListBuilder(
        repository.getPaged(), config
    )
        .build()

    var edited = MutableLiveData(empty)

    var dataName = MutableLiveData<PagedList<Motivation>>()
    val mediator = MediatorLiveData<Unit>()

    init {
        mediator.run { addSource(dataPaging, { dataName.value = it }) }
    }

    fun likeUp(id: Long) = repository.likeUp(id)
    fun likeDown(id: Long) = repository.likeDown(id)
    fun share(id: Long) = repository.share(id)
    fun remove(id: Long) = repository.remove(id)
    fun isClickName(motivation: Motivation) {

        val liveName: LiveData<PagedList<Motivation>> = LivePagedListBuilder(
            repository.getName(name = motivation.author), config
        )
            .build()
        mediator.removeSource(dataPaging)
        mediator.addSource(liveName) {
            dataName.value = it
        }
    }

    fun removeFilter() {
        mediator.removeSource(dataPaging)
        mediator.addSource(dataPaging, { dataName.value = it })
    }

    fun saveMotivation() {
        edited.value?.let {
            repository.saveMotivation(it)
        }
        edited.value = empty
    }

    fun changeContent(content: String) {
        edited.value = edited.value?.copy(content = content)
    }

    fun changeUrlContent(content: String) {
        val text = content.trim()
        edited.value = edited.value?.copy(urlContent = text)
    }

    fun changeUrlImage(content: String) {
        val text = content.trim()
        edited.value = edited.value?.copy(urlImage = text)
    }

    fun changeAuthor(content: String) {
        val text = content.trim()
        edited.value = edited.value?.copy(author = text)
    }
}