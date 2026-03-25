package com.example.newsreaderapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.newsreaderapp.model.NewsResponse
import com.example.newsreaderapp.network.NewsApiService
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NewsApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var newsApiService: NewsApiService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        newsApiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/")) // Mock URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getNews should return valid response`() = runBlocking {
        // Mock API Response
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(Gson().toJson(NewsResponse(status = "ok", totalResults = 1, articles = emptyList())))

        mockWebServer.enqueue(mockResponse)

        // Perform API call
        val response = newsApiService.getNews(category = "technology")

        // Assertions
        assertNotNull(response)
        assertEquals(200, response.code())
        assertEquals("ok", response.body()?.status)
    }

    @Test
    fun `getNews should handle server error`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        val response = newsApiService.getNews(category = "business")

        assertEquals(500, response.code())
        assertNotNull(response.errorBody())
    }
}
