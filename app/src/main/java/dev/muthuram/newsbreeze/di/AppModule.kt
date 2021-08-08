package dev.muthuram.newsbreeze.di

import dev.muthuram.newsbreeze.repository.NewsRepository
import dev.muthuram.newsbreeze.service.ApiProvider
import dev.muthuram.newsbreeze.ui.articleDetails.NewsArticleDetailsViewModel
import dev.muthuram.newsbreeze.ui.home.HomeViewModel
import dev.muthuram.newsbreeze.ui.savedArticle.SavedArticleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {

    private val viewModelModules = module {
        viewModel { HomeViewModel(get()) }
        viewModel { SavedArticleViewModel(get()) }
        viewModel { NewsArticleDetailsViewModel(get()) }
    }

    private val repoModules = module {
        single { NewsRepository(get()) }
    }

    private val commonModules = module {
        single { ApiProvider.client }
    }

    fun appModules() = viewModelModules + repoModules + commonModules
}