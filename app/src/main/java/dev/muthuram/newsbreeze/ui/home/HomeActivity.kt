package dev.muthuram.newsbreeze.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dev.muthuram.newsbreeze.R
import dev.muthuram.newsbreeze.adapter.NewsArticlesAdapter
import dev.muthuram.newsbreeze.data.model.ArticleDetails
import dev.muthuram.newsbreeze.data.model.NavigationModel
import dev.muthuram.newsbreeze.data.model.Trigger
import dev.muthuram.newsbreeze.helper.afterTextChangeListener
import dev.muthuram.newsbreeze.helper.observeLiveData
import dev.muthuram.newsbreeze.helper.startActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class HomeActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by inject()
    private val newsArticlesAdapter =
        NewsArticlesAdapter(
            this,
            ::onItemClickListener,
            ::onSaveClickListener
        )

    private val linearLayoutManager by lazy { LinearLayoutManager(this@HomeActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpUi()
        setUpListeners()
    }

    private fun setUpUi() {
        setupAdapter()
        homeViewModel.navigate.observeLiveData(this, ::navigate)
        homeViewModel.error.observeLiveData(this, ::handleError)
        homeViewModel.loader.observeLiveData(this, ::handleLoaderVisibility)
        homeViewModel.articleSaved.observeLiveData(this, ::onArticleSaved)
        homeViewModel.getNewsHeadLines.observeLiveData(this,::updateArticleList)
    }

    private fun setUpListeners() {
        uiCvSaveContainer.setOnClickListener { homeViewModel.onSavedClicked() }
        uiEtSearchText.afterTextChangeListener { text  -> searchNewsFromText(text) }
    }

    private fun setupAdapter() {
        uiRvNews.apply {
            layoutManager = linearLayoutManager
            adapter = newsArticlesAdapter
        }
    }

    private fun navigate(navigationModel: NavigationModel) {
        startActivity(navigationModel)
    }

    private fun onItemClickListener(articleDetails: ArticleDetails) {
        homeViewModel.showMoreDetails(articleDetails)
    }

    private fun onSaveClickListener(articleDetails: ArticleDetails) {
        homeViewModel.saveArticle(articleDetails)
    }

    private fun handleError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    private fun onArticleSaved(t : Trigger) {
        Toast.makeText(this, getString(R.string.str_saved_successfully), Toast.LENGTH_LONG).show()
    }

    private fun handleLoaderVisibility(isLoading: Boolean) {
        uiPbLoader.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun updateArticleList(articleList : ArrayList<ArticleDetails>) {
        newsArticlesAdapter.updateList(articleList)
    }

    private fun searchNewsFromText(text : String) {
        if (text.isNotBlank()) {
            homeViewModel.onTextSearched(text).toString()
                .replace("[^A-Za-z0-9 ]".toRegex(), "").lowercase()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        homeViewModel.onActivityDestroyed()
    }
}