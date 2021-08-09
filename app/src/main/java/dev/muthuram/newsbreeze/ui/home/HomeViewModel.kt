package dev.muthuram.newsbreeze.ui.home

import android.os.Bundle
import androidx.lifecycle.*
import dev.muthuram.newsbreeze.constants.DATE_FORMAT
import dev.muthuram.newsbreeze.data.model.ArticleDetails
import dev.muthuram.newsbreeze.data.model.NavigationModel
import dev.muthuram.newsbreeze.data.model.Trigger
import dev.muthuram.newsbreeze.handler.CustomResponse
import dev.muthuram.newsbreeze.helper.defaultValue
import dev.muthuram.newsbreeze.helper.toArrayList
import dev.muthuram.newsbreeze.helper.trigger
import dev.muthuram.newsbreeze.repository.NewsRepository
import dev.muthuram.newsbreeze.ui.articleDetails.NewsArticleDetailsActivity
import dev.muthuram.newsbreeze.ui.articleDetails.NewsArticleDetailsViewModel.Companion.KEY_ARTICLE_DETAILS
import dev.muthuram.newsbreeze.ui.savedArticle.SavedArticleActivity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeViewModel(
    private val newsRepository: NewsRepository,
) : ViewModel() {

    private val navigateLd = MutableLiveData<NavigationModel>()
    private val errorLd = MutableLiveData<String>()
    private val loaderLd = MutableLiveData<Boolean>()
    private val articleSavedLd = MutableLiveData<Trigger>()
    private val fromDateLd = MutableLiveData<Date>()
    private val toDateLd = MutableLiveData<Date>()
    private val getNewsHeadLinesLD = MutableLiveData<ArrayList<ArticleDetails>>()

    val navigate: LiveData<NavigationModel> = navigateLd
    val error: LiveData<String> = errorLd
    val loader: LiveData<Boolean> = loaderLd
    val articleSaved : LiveData<Trigger> = articleSavedLd
    val getNewsHeadLines: LiveData<ArrayList<ArticleDetails>> = getNewsHeadLinesLD
    private val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)

    init {
        loaderLd.value = true
        viewModelScope.launch {
            when (val response = newsRepository.getNewsHeadLinesArticles()) {
                is CustomResponse.Success -> getNewsHeadLinesLD.value = response.data.articles.toArrayList()
                is CustomResponse.Failure -> errorLd.value = response.error.message.defaultValue()
            }.also { loaderLd.value = false }
        }
    }

    val fromDate: LiveData<String> = fromDateLd.map { fromDate ->
        dateFormat.format(fromDate)
    }

    val toDate: LiveData<String> = toDateLd.map { toDate ->
        dateFormat.format(toDate)
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
        newsRepository.saveArticlesToReadLater(articleDetails)
        articleSavedLd.trigger()
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

    fun onFromDateSelected(year: Int, month: Int, dayOfMonth: Int) {
        fromDateLd.value = Calendar.getInstance().apply { set(year, month, dayOfMonth) }.time
    }

    fun onToDateSelected(year: Int, month: Int, dayOfMonth: Int) {
        toDateLd.value = Calendar.getInstance().apply { set(year, month, dayOfMonth) }.time
    }

    fun onActivityDestroyed() {
        newsRepository.clearSavedArticles()
    }
}