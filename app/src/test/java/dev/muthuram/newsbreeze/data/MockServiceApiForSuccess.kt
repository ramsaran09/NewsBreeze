package dev.muthuram.newsbreeze.data

import dev.muthuram.newsbreeze.data.model.NewsArticleResponseModel
import dev.muthuram.newsbreeze.service.ServiceApi
import retrofit2.Response

class MockServiceApiForSuccess : ServiceApi {

    override suspend fun getSearchedNewsArticle(
        searchedTitle: String?,
        fromDate: String?,
        to: String?,
        sortBy: String?
    ): Response<NewsArticleResponseModel> {
        return Response.success(NewsArticleResponseTestModel.getData())
    }

    override suspend fun getNewsHeadlines(
        category: String?,
        country: String?
    ): Response<NewsArticleResponseModel> {
        return Response.success(NewsArticleResponseTestModel.getData())
    }
}