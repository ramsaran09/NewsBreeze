package dev.muthuram.newsbreeze.ui.home

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.muthuram.newsbreeze.data.model.ArticleDetails
import dev.muthuram.newsbreeze.data.model.NavigationModel
import dev.muthuram.newsbreeze.handler.CustomResponse
import dev.muthuram.newsbreeze.helper.defaultValue
import dev.muthuram.newsbreeze.helper.toArrayList
import dev.muthuram.newsbreeze.repository.NewsRepository
import dev.muthuram.newsbreeze.ui.articleDetails.NewsArticleDetailsActivity
import dev.muthuram.newsbreeze.ui.articleDetails.NewsArticleDetailsViewModel.Companion.KEY_ARTICLE_DETAILS
import dev.muthuram.newsbreeze.ui.savedArticle.SavedArticleActivity
import kotlinx.coroutines.launch

class HomeViewModel(
    private val newsRepository: NewsRepository,
) : ViewModel() {

    private val navigateLd = MutableLiveData<NavigationModel>()
    private val errorLd = MutableLiveData<String>()
    private val loaderLd = MutableLiveData<Boolean>()
    private val getNewsHeadLinesLD = MutableLiveData<ArrayList<ArticleDetails>>()

    val navigate: LiveData<NavigationModel> = navigateLd
    val error: LiveData<String> = errorLd
    val loader: LiveData<Boolean> = loaderLd
    val getNewsHeadLines: LiveData<ArrayList<ArticleDetails>> = getNewsHeadLinesLD

    init {
        loaderLd.value = true
        viewModelScope.launch {
            when (val response = newsRepository.getNewsHeadLinesArticles()) {
                is CustomResponse.Success -> getNewsHeadLinesLD.value = response.data.articles.toArrayList()
                is CustomResponse.Failure -> errorLd.value = response.error.message.defaultValue()
            }.also { loaderLd.value = false }
        }
    }

    fun showMoreDetails(articleDetails: ArticleDetails) {
        val bundle = Bundle().also {
            it.putParcelable(KEY_ARTICLE_DETAILS,articleDetails)
        }
        navigateLd.value = NavigationModel(
            NewsArticleDetailsActivity::class.java,
            extras = bundle
        )
    }

    fun saveArticle(articleDetails: ArticleDetails) {

    }

    fun onTextSearched(searchedText : String?) {
        loaderLd.value = true
        viewModelScope.launch {
            when (val response = newsRepository.getSearchedArticle(searchedText)) {
                is CustomResponse.Success -> getNewsHeadLinesLD.value = response.data.articles.toArrayList()
                is CustomResponse.Failure -> errorLd.value = response.error.message.defaultValue()
            }.also { loaderLd.value = false }
        }
    }

    fun onSavedClicked() {
        navigateLd.value = NavigationModel(
            SavedArticleActivity::class.java
        )
    }
}