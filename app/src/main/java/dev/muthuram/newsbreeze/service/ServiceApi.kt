package dev.muthuram.newsbreeze.service

import dev.muthuram.newsbreeze.constants.COUNTRY_TYPE_INDIA
import dev.muthuram.newsbreeze.data.model.NewsArticleResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {

    @GET("v2/everything")
    suspend fun getSearchedNewsArticle(
        @Query("q") searchedTitle: String?,
        @Query("from") fromDate: String? = null,
        @Query("to") to: String? = null,
        @Query("sortBy") sortBy: String? = null,
    ) : Response<NewsArticleResponseModel>

    @GET("v2/top-headlines")
    suspend fun getNewsHeadlines(
        @Query("category") category : String? = null,
        @Query("country") country: String? = COUNTRY_TYPE_INDIA,
    ) : Response<NewsArticleResponseModel>

}