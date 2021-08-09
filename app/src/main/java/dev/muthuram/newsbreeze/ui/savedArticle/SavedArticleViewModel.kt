package dev.muthuram.newsbreeze.ui.savedArticle

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.muthuram.newsbreeze.data.model.ArticleDetails
import dev.muthuram.newsbreeze.data.model.NavigationModel
import dev.muthuram.newsbreeze.repository.NewsRepository
import dev.muthuram.newsbreeze.ui.articleDetails.NewsArticleDetailsActivity
import dev.muthuram.newsbreeze.ui.articleDetails.NewsArticleDetailsViewModel

class SavedArticleViewModel(
    private val newsRepository: NewsRepository,
) : ViewModel() {

    private val navigateLd = MutableLiveData<NavigationModel>()
    private val noSavedNewsLd = MutableLiveData<Boolean>()
    private val getSavedNewsLD = MutableLiveData<ArrayList<ArticleDetails>>()

    val navigate: LiveData<NavigationModel> = navigateLd
    val noSavedNews: LiveData<Boolean> = noSavedNewsLd
    val getSavedNews: LiveData<ArrayList<ArticleDetails>> = getSavedNewsLD

    init {
        val savedArticles = newsRepository.getSavedArticles()
        Log.d("savedArticle",savedArticles.toString())
        if (savedArticles?.size != 0 && !savedArticles.isNullOrEmpty()) {
            Log.d("savedArticles",savedArticles.toString())
            getSavedNewsLD.value = savedArticles!!
        } else noSavedNewsLd.value = true
    }

    fun showMoreDetails(articleDetails: ArticleDetails) {
        val bundle = Bundle().also {
            it.putParcelable(NewsArticleDetailsViewModel.KEY_ARTICLE_DETAILS, articleDetails)
        }
        navigateLd.value = NavigationModel(
            NewsArticleDetailsActivity::class.java,
            extras = bundle
        )
    }
}