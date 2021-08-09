package dev.muthuram.newsbreeze.data

import dev.muthuram.newsbreeze.data.model.NewsArticleResponseModel
import dev.muthuram.newsbreeze.service.ServiceApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class MockServiceApiForFailure : ServiceApi {

    override suspend fun getSearchedNewsArticle(
        searchedTitle: String?,
        fromDate: String?,
        to: String?,
        sortBy: String?
    ): Response<NewsArticleResponseModel> {
        return Response.error(400,
            "".toByteArray().toResponseBody("application/json; charset=utf-8".toMediaType())
        )
    }

    override suspend fun getNewsHeadlines(
        category: String?,
        country: String?
    ): Response<NewsArticleResponseModel> {
        return Response.error(400,
            "".toByteArray().toResponseBody("application/json; charset=utf-8".toMediaType())
        )
    }
}