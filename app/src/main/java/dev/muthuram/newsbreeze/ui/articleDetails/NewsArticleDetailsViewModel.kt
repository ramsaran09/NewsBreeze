package dev.muthuram.newsbreeze.ui.articleDetails

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.muthuram.newsbreeze.data.model.ArticleDetails
import dev.muthuram.newsbreeze.repository.NewsRepository

class NewsArticleDetailsViewModel(
    private val newsRepository: NewsRepository,
    ) : ViewModel() {

    private val showArticleInfoLD = MutableLiveData<ArticleDetails>()

    val showArticleInfo : LiveData<ArticleDetails> = showArticleInfoLD

    fun processIntent(intent: Intent?) {
        intent ?: return
        showArticleInfoLD.value = intent.getParcelableExtra(KEY_ARTICLE_DETAILS)
    }
    companion object {
        const val KEY_ARTICLE_DETAILS = "key.article.details"
    }
}