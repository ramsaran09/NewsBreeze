package dev.muthuram.newsbreeze.data

import dev.muthuram.newsbreeze.data.model.ArticleDetails
import dev.muthuram.newsbreeze.data.model.NewsArticleResponseModel
import dev.muthuram.newsbreeze.data.model.SourceDetails
import okhttp3.ResponseBody
import java.time.Instant
import java.util.*

object NewsArticleResponseTestModel {

    fun getData() = NewsArticleResponseModel(
        status = "ok",
        articles = arrayListOf(
            ArticleDetails(
                source = SourceDetails(
                    id = "Times of India",
                    name = "Times of India",
                ),
                author = "Kane williamson",
                title = "Kane shows a classic century",
                description = "Kane shows a classic century against India which leads to victory over the series",
                urlToImage = "https://www.sharedPic.png",
                publishedAt =  getDate(),
                content = "Kane shows a classic century against India which leads to victory over the series"
            )
        )
    )

    private fun getDate() = run {
        val input = Date()
        val instant: Instant = input.toInstant()
        Date.from(instant)
    }

    fun getFailureData() = ResponseBody
}