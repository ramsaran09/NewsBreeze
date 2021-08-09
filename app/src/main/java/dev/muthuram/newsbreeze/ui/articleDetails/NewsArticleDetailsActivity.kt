package dev.muthuram.newsbreeze.ui.articleDetails

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import dev.muthuram.newsbreeze.R
import dev.muthuram.newsbreeze.constants.DATE_FORMAT
import dev.muthuram.newsbreeze.data.model.ArticleDetails
import dev.muthuram.newsbreeze.data.model.Trigger
import dev.muthuram.newsbreeze.helper.formatTo
import dev.muthuram.newsbreeze.helper.observeLiveData
import kotlinx.android.synthetic.main.activity_article_details.*
import org.koin.android.ext.android.inject

class NewsArticleDetailsActivity : AppCompatActivity() {

    private val  newsArticleDetailsViewModel : NewsArticleDetailsViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_details)
        newsArticleDetailsViewModel.processIntent(intent)
        setUpUi()
        setUpListeners()
    }

    private fun setUpUi() {
        newsArticleDetailsViewModel.showArticleInfo.observeLiveData(this,::displayArticleDetails)
        newsArticleDetailsViewModel.articleSaved.observeLiveData(this, ::onArticleSaved)
    }

    private fun setUpListeners() {
        uiIvBack.setOnClickListener { onBackPressed() }
        uiBtSave.setOnClickListener { newsArticleDetailsViewModel.saveArticle() }
    }

    private fun displayArticleDetails(articleDetails: ArticleDetails) {
        uiTvNewsTitle.text = articleDetails.title
        uiTvDate.text = articleDetails.publishedAt?.formatTo(DATE_FORMAT)
        uiTvAuthorName.text = articleDetails.author
        uiTvDescription.text = articleDetails.description
        uiTvAuthorRole.text = articleDetails.source.name
        if (articleDetails.urlToImage?.trim()?.length != 0 && articleDetails.urlToImage?.isNotBlank() == true) {
            Picasso.with(this)
                .load(articleDetails.urlToImage.trim())
                .into(uiIvNewsImage)
        }
    }

    private fun onArticleSaved(t : Trigger) {
        Toast.makeText(this, getString(R.string.str_saved_successfully), Toast.LENGTH_LONG).show()
    }
}