package dev.muthuram.newsbreeze.repository

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import androidx.test.core.app.ApplicationProvider
import dev.muthuram.newsbreeze.data.MockServiceApiForFailure
import dev.muthuram.newsbreeze.data.MockServiceApiForSuccess
import dev.muthuram.newsbreeze.data.mapper.ArticlesMapper
import dev.muthuram.newsbreeze.handler.CustomResponse
import dev.muthuram.newsbreeze.manager.PreferenceManager
import dev.muthuram.newsbreeze.myApplication.MyApplication
import dev.muthuram.newsbreeze.ui.home.HomeActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class ArticleMapperTest {

    @Test
    fun news_mapper_return_success_response() = runBlockingTest {
        val preferenceManager = mock(PreferenceManager::class.java)
        val serviceApi by lazy { MockServiceApiForSuccess() }
        val repository = NewsRepository(serviceApi,preferenceManager)
        val response = repository.getNewsHeadLinesArticles() as CustomResponse.Success
        val responseFromServer = serviceApi.getNewsHeadlines()
        val expectedResponse = ArticlesMapper.map(responseFromServer)

        Assert.assertNotNull(responseFromServer)
        Assert.assertEquals(expectedResponse,response)
    }

    @Test
    fun news_mapper_return_failure_response() = runBlockingTest {
        val preferenceManager = mock(PreferenceManager::class.java)
        val serviceApi by lazy { MockServiceApiForFailure() }
        val repository = NewsRepository(serviceApi, preferenceManager)
        val response = repository.getNewsHeadLinesArticles() as CustomResponse.Failure
        val responseFromServer = serviceApi.getNewsHeadlines()
        val expectedResponse = ArticlesMapper.map(responseFromServer)

        Assert.assertNotNull(responseFromServer)
        Assert.assertEquals(responseFromServer.code(),400)
        Assert.assertEquals(response,expectedResponse)
    }
}