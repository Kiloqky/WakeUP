package ru.kiloqky.wakeup.rest.room.news.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import ru.kiloqky.wakeup.rest.NewsData
import ru.kiloqky.wakeup.rest.retrofit.news.entitiesNews.Source

@Keep
@Entity(tableName = "news_table")
data class News(
    var name: String? = null,
    var title: String? = null,
    var description: String? = null,
    var url: String? = null,
    var urlToImage: String? = null
) : NewsData() {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

