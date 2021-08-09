package dev.muthuram.newsbreeze.manager

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.muthuram.newsbreeze.data.model.ArticleDetails

class PreferenceManager(
    context: Context
) {

    companion object{
        const val KEY_ARTICLES = "key.articles"
    }

    private val appPreference = context.getSharedPreferences("AppData", Context.MODE_PRIVATE)
    private val savedNewsArticle : ArrayList<ArticleDetails> = arrayListOf()

    fun saveArticles(articleDetailsList : ArticleDetails) {
        appPreference.edit {
            val gson = Gson()
            savedNewsArticle.add(articleDetailsList)
            val json = gson.toJson(savedNewsArticle)
            putString(KEY_ARTICLES, json)
        }
    }

    fun getSavedArticles(): ArrayList<ArticleDetails>? {
        val gson = Gson()
        val json = appPreference.getString(KEY_ARTICLES, null)
        val type = object : TypeToken<ArrayList<ArticleDetails?>?>() {}.type
        return gson.fromJson(json, type)
    }

    fun clearPreferences() {
        appPreference.edit {
            clear()
        }
    }
}