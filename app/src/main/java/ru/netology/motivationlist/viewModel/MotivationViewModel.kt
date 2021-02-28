package ru.netology.motivationlist.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
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
        published = "", //SimpleDateFormat("dd-MM-yyyy").format(Date()).toString(),
        countShare = 0,
        countLike = 0,
        urlContent = ""
)

class MotivationViewModel (application: Application) : AndroidViewModel(application) {
    private val repository: MotivationRepository = MotivationRepositoryImplSQL(
            AppDb.getInstance(application).motivationDao()
    )
    val data = repository.getAll()
    val edited = MutableLiveData(empty)

    fun likeUp(id: Long) = repository.likeUp(id)
    fun likeDown(id: Long) = repository.likeDown(id)
    fun share(id: Long) = repository.share(id)
    //fun remove(id: Long) = repository.remove(id)


    fun saveMotivation() {                 //сохранение поста
        edited.value?.let {
            repository.saveMotivation(it)
        }
        edited.value = empty
    }

//    fun changeId() {
//        edited.value = empty
//    }
//
//    fun editPost(post: Post) {
//        edited.value = post
//    }

//    fun changeContent(content: String) {   // изменение контента поста
//        val text = content.trim()
//        if (edited.value?.content == text) {
//            return
//        }
//        edited.value = edited.value?.copy(content = text)
//    }
//
//    fun changeUrl(content: String) {   // изменение url video
//        val text = content.trim()
//        if (edited.value?.urlVideo == text) {
//            return
//        }
//        edited.value = edited.value?.copy(urlVideo = text)
//    }
}