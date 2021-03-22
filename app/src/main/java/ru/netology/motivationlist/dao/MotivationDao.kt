package ru.netology.motivationlist.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.netology.motivationlist.entity.MotivationEntity

@Dao
interface MotivationDao {

    @Query("SELECT * FROM MotivationEntity ORDER BY countLike DESC")
    fun getAll(): LiveData<List<MotivationEntity>>

    @Query("SELECT * FROM MotivationEntity WHERE author = :author ORDER BY countLike DESC")
    fun getName(author:String): List<MotivationEntity>

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

    @Query("DELETE FROM MotivationEntity WHERE id = :id")
    fun remove(id:Long)

//    @Delete
//    fun remove(motivation: MotivationEntity)

    @Insert
    fun saveMotivation(motivation: MotivationEntity)

//    @Update
//    fun editPost(motivation: MotivationEntity)
}