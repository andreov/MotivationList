package ru.netology.motivationlist.viewModel

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
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

    //var data = repository.getAll()

    var data: LiveData<MutableList<Motivation>> = repository.getAll()

//    init {
//        data.value = repository.getAll()
//    }

    var dataName: MutableLiveData<MutableList<Motivation>> = MutableLiveData()

    //data=repository.getAll()
    var edited = MutableLiveData(empty)



    fun likeUp(id: Long) = repository.likeUp(id)
    fun likeDown(id: Long) = repository.likeDown(id)
    fun share(id: Long) = repository.share(id)
    fun remove(id: Long) = repository.remove(id)
    fun isClickName(motivation: Motivation) {
        dataName.value = repository.getName(motivation.author)

    }


//    fun isClick(author:String){
//        dataName = repository.getName()
//    }


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

    fun changeContent(content: String) {   // изменение контента поста
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun changeUrlContent(content: String) {   // изменение url video
        val text = content.trim()
        if (edited.value?.urlContent == text) {
            return
        }
        edited.value = edited.value?.copy(urlContent = text)
    }

    fun changeUrlImage(content: String) {   // изменение url video
        //val text = content.substringAfter(':')
//        if (edited.value?.urlImage == text) {
//            return
//        }
        edited.value = edited.value?.copy(urlImage = content)
    }

    fun changeAuthor(content: String) {   // изменение url video
        val text = content.trim()
        if (edited.value?.author == text) {
            return
        }
        edited.value = edited.value?.copy(author = text)
    }
}