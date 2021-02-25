package ru.netology.motivationlist.dao

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.netology.motivationlist.dto.Motivation
import ru.netology.motivationlist.entity.MotivationEntity

interface MotivationDao {

    @Query("SELECT * FROM MotivationEntity ORDER BY countLike DESC")
    fun getAll(): LiveData<List<MotivationEntity>>

    @Query(
            """
           UPDATE MotivationEntity SET
               countLike = countLike + 1               
           WHERE id = :id
        """)
    fun likeUp(id:Long)

    @Query(
            """
           UPDATE MotivationEntity SET
               countLike = countLike - 1               
           WHERE id = :id
        """)
    fun likeDown(id:Long)

    @Query("""
           UPDATE MotivationEntity SET
               countShare = countShare + 1               
           WHERE id = :id
        """)
    fun share(id:Long)

//    @Query("DELETE FROM PostEntity WHERE id = :id")
//    fun remove(id:Long)

//    @Delete
//    fun remove(post: PostEntity)

    @Insert
    fun savePost(post: MotivationEntity)

//    @Update
//    fun editPost(post: PostEntity)
}