package com.example.newsreaderapp

import com.example.newsreaderapp.model.NewsResponse
import com.example.newsreaderapp.network.NewsApiService
import com.example.newsreaderapp.repository.NewsRepository
import com.google.gson.Gson
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class NewsRepositoryTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var newsApiService: NewsApiService
    private lateinit var newsRepository: NewsRepository

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        newsApiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)


        newsRepository = NewsRepository()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getNews should return valid response`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(Gson().toJson(NewsResponse(status = "ok", totalResults = 1, articles = emptyList())))

        mockWebServer.enqueue(mockResponse)

        val response = newsRepository.getNews("technology")

        assertNotNull(response.body())
        assertEquals(200, response.code())
        assertEquals("ok", response.body()?.status)
    }


    @Test
    fun `getNews should handle API error`() = runBlocking {
        // Mock server error response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
                .setBody("{ \"message\": \"Internal Server Error\" }")
        )

        val response = newsApiService.getNews("business")

        assertEquals(500, response.code())
        assertNotNull(response.errorBody())
    }

    @Test
    fun `getNews should handle empty response`() = runBlocking {

        val mockResponse = NewsResponse(status = "ok", totalResults = 0, articles = emptyList())

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(Gson().toJson(mockResponse))
        )

        val response = newsApiService.getNews(category = "general")

        assertNotNull(response)
        assertEquals(200, response.code())
        assertEquals(0, response.body()?.articles?.size)
    }

    @Test
    fun `getNews should handle network failure`() = runBlocking {
        mockWebServer.shutdown()

        try {
            val response = newsApiService.getNews("sports")
            fail("Expected an exception but got response: $response")
        } catch (e: Exception) {
            assertTrue(e is java.net.ConnectException || e is java.net.UnknownHostException)
        }
    }

    @Test
    fun `getNews should parse correct JSON response`() = runBlocking {
        val jsonResponse = """
        {
            "status": "ok",
            "totalResults": 1,
            "articles": [
                {
                    "title": "Breaking News",
                    "description": "This is a sample news article",
                    "author": "John Doe",
                    "url": "https://example.com/news"
                }
            ]
        }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(jsonResponse)
        )

        val response = newsApiService.getNews("world")

        assertNotNull(response)
        assertEquals(200, response.code())
        assertEquals("ok", response.body()?.status)
        assertEquals(1, response.body()?.articles?.size)
        assertEquals("Breaking News", response.body()?.articles?.get(0)?.title)
    }
}
