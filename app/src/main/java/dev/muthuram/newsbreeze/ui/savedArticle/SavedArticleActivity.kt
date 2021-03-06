package dev.muthuram.newsbreeze.ui.savedArticle

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dev.muthuram.newsbreeze.R
import dev.muthuram.newsbreeze.adapter.SavedNewsArticlesAdapter
import dev.muthuram.newsbreeze.data.model.ArticleDetails
import dev.muthuram.newsbreeze.data.model.NavigationModel
import dev.muthuram.newsbreeze.helper.observeLiveData
import dev.muthuram.newsbreeze.helper.startActivity
import kotlinx.android.synthetic.main.activity_saved_news.*
import org.koin.android.ext.android.inject

class SavedArticleActivity : AppCompatActivity() {

    private val savedArticleViewModel : SavedArticleViewModel by inject()
    private val savedNewsAdapter = SavedNewsArticlesAdapter(this,::onItemClickListener)
    private val linearLayoutManager by lazy { LinearLayoutManager(this@SavedArticleActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_news)
        setUi()
        setUpListeners()
    }

    private fun setUi() {
        setupAdapter()
        savedArticleViewModel.navigate.observeLiveData(this, ::navigate)
        savedArticleViewModel.noSavedNews.observeLiveData(this, ::showEmptyUi)
        savedArticleViewModel.getSavedNews.observeLiveData(this,::updateArticleList)
    }

    private fun setUpListeners() {
        uiIvBack.setOnClickListener { onBackPressed() }
    }

    private fun setupAdapter() {
        uiRvSavedNews.apply {
            layoutManager = linearLayoutManager
            adapter = savedNewsAdapter
        }
    }

    private fun showEmptyUi(isNoNewsSaved : Boolean) {
        if (isNoNewsSaved) {
            uiIvNoSavedNewsIcon.visibility = View.VISIBLE
            uiTvCaptionNoSavedNews.visibility = View.VISIBLE
            uiCvSavedCardContainer.visibility = View.GONE
        } else{
            uiIvNoSavedNewsIcon.visibility = View.GONE
            uiTvCaptionNoSavedNews.visibility = View.GONE
            uiCvSavedCardContainer.visibility = View.VISIBLE
        }
    }

    private fun updateArticleList(articleList : ArrayList<ArticleDetails>) {
        savedNewsAdapter.updateList(articleList)
    }

    private fun onItemClickListener(articleDetails: ArticleDetails) {
        savedArticleViewModel.showMoreDetails(articleDetails)
    }

    private fun navigate(navigationModel: NavigationModel) {
        startActivity(navigationModel)
    }
}