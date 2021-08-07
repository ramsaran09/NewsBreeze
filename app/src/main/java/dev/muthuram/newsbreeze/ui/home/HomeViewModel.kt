package dev.muthuram.newsbreeze.ui.home

import androidx.lifecycle.ViewModel
import dev.muthuram.newsbreeze.repository.NewsRepository

class HomeViewModel(
    private val newsRepository: NewsRepository,
) : ViewModel() {
}