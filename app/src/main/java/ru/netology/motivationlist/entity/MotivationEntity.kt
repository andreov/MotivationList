package ru.netology.motivationlist.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.motivationlist.dto.Motivation
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class MotivationEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        val author: String,
        val content: String,
        val published: String = SimpleDateFormat("dd-MM-yyyy").format(Date()).toString(),
        val countLike: Long = 0,
        val countShare: Long = 0,
        val urlContent: String = ""
) {
    fun toDto() = Motivation(id, author, content, published, countLike, countShare, urlContent)

    companion object {
        fun fromDto(dto: Motivation) =
                MotivationEntity(dto.id, dto.author, dto.content, dto.published, dto.countLike, dto.countShare, dto.urlContent)

    }
}
