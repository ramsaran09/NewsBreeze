package dev.muthuram.newsbreeze.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

data class NewsArticleResponseModel(
    val status : String?,
    val articles : List<ArticleDetails> = arrayListOf(),
)
@Parcelize
data class ArticleDetails(
    val source : SourceDetails,
    val author : String?,
    val title : String?,
    val description : String?,
    val urlToImage : String?,
    val publishedAt : Date?,
    val content : String?,
) : Parcelable

@Parcelize
data class SourceDetails(
    val id: String?,
    val name : String?,
) : Parcelable