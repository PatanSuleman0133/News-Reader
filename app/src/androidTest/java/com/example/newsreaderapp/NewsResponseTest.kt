package com.example.newsreaderapp


import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.newsreaderapp.model.NewsResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsResponseTest {

    @Test
    fun testJsonParsingOnAndroid() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter(NewsResponse::class.java)

        val json = """{"status": "ok", "totalResults": 1, "articles": [{"title": "Test News", "description": "Description", "urlToImage": null, "url": "https://example.com"}]}"""
        val newsResponse = adapter.fromJson(json)

        assertEquals("ok", newsResponse?.status)
        assertEquals(1, newsResponse?.totalResults)
    }
}
