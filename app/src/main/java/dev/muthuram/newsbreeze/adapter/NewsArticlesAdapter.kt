package dev.muthuram.newsbreeze.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.muthuram.newsbreeze.R
import dev.muthuram.newsbreeze.constants.DATE_FORMAT
import dev.muthuram.newsbreeze.data.model.ArticleDetails
import dev.muthuram.newsbreeze.helper.formatTo
import kotlinx.android.synthetic.main.model_news_article_items.view.*

class NewsArticlesAdapter(
    val contexts : Context,
    val onItemClickListener: (ArticleDetails) -> Unit,
    val onSaveClickListener: (ArticleDetails) -> Unit,
    ) : RecyclerView.Adapter<NewsArticlesAdapter.NewsArticleViewHolder>() {

    val articles : ArrayList<ArticleDetails> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsArticleViewHolder =
        NewsArticleViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.model_news_article_items, parent, false)
        )

    override fun onBindViewHolder(holder: NewsArticleViewHolder, position: Int) {
        val article = articles[position]
        with(holder) {
            tvNewsTitle.text = article.title
            tvNewsDescription.text = article.description
            tvDate.text = article.publishedAt?.formatTo(DATE_FORMAT)
            if (article.urlToImage?.trim()?.length != 0 && article.urlToImage?.isNotBlank() == true) {
                Picasso.with(contexts)
                    .load(article.urlToImage.trim())
                    .fit()
                    .into(ivNewsImage)
            }
        }
    }

    override fun getItemCount(): Int = articles.size

    fun updateList(articleList : ArrayList<ArticleDetails>){
        articles.clear()
        articles.addAll(articleList)
        notifyDataSetChanged()
    }

    inner class NewsArticleViewHolder(view : View) :  RecyclerView.ViewHolder(view) {
        val ivNewsImage : AppCompatImageView = view.uiIvNewsImage
        private val ivSave : AppCompatImageView = view.uiIvSave
        val tvNewsTitle : AppCompatTextView = view.uiTvNewsTitle
        val tvNewsDescription : AppCompatTextView = view.uiTvNewsDescription
        val tvDate : AppCompatTextView = view.uiTvDate
        private val btRead : AppCompatButton =  view.uiBtRead
        private val btSave : AppCompatButton = view.uiBtSave

        init {
            btSave.setOnClickListener { onSaveClickListener.invoke(articles[adapterPosition]) }
            ivSave.setOnClickListener { onSaveClickListener.invoke(articles[adapterPosition]) }
            ivNewsImage.setOnClickListener { onItemClickListener.invoke(articles[adapterPosition]) }
            btRead.setOnClickListener { onItemClickListener.invoke(articles[adapterPosition]) }
        }
    }
}