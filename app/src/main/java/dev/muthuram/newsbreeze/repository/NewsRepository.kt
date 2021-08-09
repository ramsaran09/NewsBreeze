package dev.muthuram.newsbreeze.repository

import dev.muthuram.newsbreeze.data.mapper.ArticlesMapper
import dev.muthuram.newsbreeze.data.model.ArticleDetails
import dev.muthuram.newsbreeze.data.model.NewsArticleResponseModel
import dev.muthuram.newsbreeze.handler.CustomResponse
import dev.muthuram.newsbreeze.handler.ServiceException
import dev.muthuram.newsbreeze.manager.PreferenceManager
import dev.muthuram.newsbreeze.service.ServiceApi

class NewsRepository(
    private val serviceApi: ServiceApi,
    private val preferenceManager: PreferenceManager,
) {

    suspend fun getSearchedArticle(
        searchedTitle: String?,
        fromDate: String? = null,
        toDate: String? = null,
        sortBy: String? = null
    ): CustomResponse<NewsArticleResponseModel, ServiceException> {
        return ArticlesMapper.map(
            serviceApi.getSearchedNewsArticle(searchedTitle, fromDate, toDate, sortBy)
        )
    }

    suspend fun getNewsHeadLinesArticles(): CustomResponse<NewsArticleResponseModel, ServiceException> {
        return ArticlesMapper.map(serviceApi.getNewsHeadlines())
    }

    fun getSavedArticles(): ArrayList<ArticleDetails>? = preferenceManager.getSavedArticles()

    fun saveArticlesToReadLater(articles : ArticleDetails) {
        preferenceManager.saveArticles(articles)
    }

    fun clearSavedArticles() {
        preferenceManager.clearPreferences()
    }
}