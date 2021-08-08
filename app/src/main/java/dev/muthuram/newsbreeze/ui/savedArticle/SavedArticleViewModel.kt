package dev.muthuram.newsbreeze.ui.savedArticle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.muthuram.newsbreeze.data.model.ArticleDetails
import dev.muthuram.newsbreeze.repository.NewsRepository

class SavedArticleViewModel(
    private val newsRepository: NewsRepository,
    ) : ViewModel() {

    private val noSavedNewsLd = MutableLiveData<Boolean>()
    private val getSavedNewsLD = MutableLiveData<ArrayList<ArticleDetails>>()

    val noSavedNews: LiveData<Boolean> = noSavedNewsLd
    val getSavedNews: LiveData<ArrayList<ArticleDetails>> = getSavedNewsLD

    init {

    }
}