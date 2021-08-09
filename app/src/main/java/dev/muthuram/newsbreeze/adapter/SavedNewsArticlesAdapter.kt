package dev.muthuram.newsbreeze.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.muthuram.newsbreeze.R
import dev.muthuram.newsbreeze.constants.DATE_FORMAT
import dev.muthuram.newsbreeze.data.model.ArticleDetails
import dev.muthuram.newsbreeze.helper.formatTo
import kotlinx.android.synthetic.main.model_saved_news.view.*

class SavedNewsArticlesAdapter(
    private val contexts : Context,
    val onItemClickListener: (ArticleDetails) -> Unit,
): RecyclerView.Adapter<SavedNewsArticlesAdapter.SavedNewsArticleViewHolder>()  {

    val articles : ArrayList<ArticleDetails> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedNewsArticleViewHolder {
        return SavedNewsArticleViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.model_saved_news,parent,false)
        )
    }

    override fun onBindViewHolder(holder: SavedNewsArticleViewHolder, position: Int) {
        Log.d("savedArticleInHolder",articles.toString())
        val savedArticle = articles[position]
        Log.d("positionedSavedArticle",savedArticle.toString())
        with(holder) {
            tvSavedTitle.text = savedArticle.title
            tvSavedNewDate.text = savedArticle.publishedAt?.formatTo(DATE_FORMAT)
            tvSavedNewsAuthor.text = savedArticle.author
            if (savedArticle.urlToImage?.trim()?.length != 0 && savedArticle.urlToImage?.isNotBlank() == true) {
                Picasso.with(contexts)
                    .load(savedArticle.urlToImage.trim())
                    .fit()
                    .into(ivSavedNewsImage)
            }
        }
    }

    override fun getItemCount(): Int = articles.size

    fun updateList(articleList : ArrayList<ArticleDetails>){
        articles.clear()
        articles.addAll(articleList)
        Log.d("savedArticle",articles.toString())
        notifyDataSetChanged()
    }

    inner class SavedNewsArticleViewHolder(view : View) :  RecyclerView.ViewHolder(view) {
        val ivSavedNewsImage : AppCompatImageView = view.uiIvSavedNewsImage
        val tvSavedTitle : AppCompatTextView = view.uiTvSavedTitle
        val tvSavedNewDate : AppCompatTextView = view.uiTvSavedNewDate
        val tvSavedNewsAuthor : AppCompatTextView = view.uiTvSavedNewsAuthor
        val tvHashTag : AppCompatTextView = view.uiTvHashTag
        private val clSavedNews : ConstraintLayout = view.uiClSavedNews

        init {
            clSavedNews.setOnClickListener { onItemClickListener.invoke(articles[adapterPosition]) }
        }
    }
}