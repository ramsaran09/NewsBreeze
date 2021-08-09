package dev.muthuram.newsbreeze.data.mapper

import com.google.gson.Gson
import dev.muthuram.newsbreeze.constants.SUCCESS_STATUS
import dev.muthuram.newsbreeze.data.model.ArticleDetails
import dev.muthuram.newsbreeze.data.model.NewsArticleResponseModel
import dev.muthuram.newsbreeze.data.model.SourceDetails
import dev.muthuram.newsbreeze.handler.CustomResponse
import dev.muthuram.newsbreeze.handler.ServiceException
import dev.muthuram.newsbreeze.helper.defaultValue
import retrofit2.Response

class ArticlesMapper {
    companion object {
        fun map(responseModel: Response<NewsArticleResponseModel>) : CustomResponse<NewsArticleResponseModel, ServiceException> {
            val response = responseModel.body()
            val error = responseModel.errorBody()
            val errorResponse = Gson().fromJson(error?.string(), ServiceException::class.java)
            return if (responseModel.isSuccessful && responseModel.code() == 200) {
                if (response?.status == SUCCESS_STATUS) {
                    CustomResponse.Success(
                        NewsArticleResponseModel(
                            status = response.status,
                            articles = response.articles.map { article ->
                                ArticleDetails(
                                    source = SourceDetails(
                                        article.source.id.defaultValue(),
                                        article.source.name.defaultValue(),
                                    ),
                                    author = article.author.defaultValue(),
                                    title = article.title.defaultValue(),
                                    description = article.description.defaultValue(),
                                    urlToImage = article.urlToImage.defaultValue(),
                                    publishedAt = article.publishedAt,
                                    content = article.content.defaultValue(),
                                )
                            }
                        )
                    )
                }else CustomResponse.Failure(
                    ServiceException(
                        errorResponse.code,
                        errorResponse.message,
                    )
                )
            }else CustomResponse.Failure(
                    ServiceException(
                        errorResponse.code,
                        errorResponse.message,
                    )
                )

        }
    }
}