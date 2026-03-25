package com.example.newsreaderapp


import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.newsreaderapp.database.ArticleDao
import com.example.newsreaderapp.database.NewsDatabase
import com.example.newsreaderapp.model.ArticleEntity
import com.example.newsreaderapp.utils.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ArticleDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: NewsDatabase
    private lateinit var dao: ArticleDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, NewsDatabase::class.java)
            .allowMainThreadQueries() // For testing purposes only
            .build()
        dao = database.articleDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertArticleAndRetrieve() = runBlocking {
        val article = ArticleEntity(
            userid = "user123",
            title = "Test Article",
            description = "This is a test article",
            url = "https://example.com/article",
            urlToImage = "https://example.com/image.jpg"
        )

        dao.insertArticle(article)
        val savedArticles = dao.getAllSavedArticles("user123").getOrAwaitValue()

        Assert.assertEquals(1, savedArticles.size)
        Assert.assertEquals("Test Article", savedArticles[0].title)
    }

    @Test
    fun deleteArticleAndVerify() = runBlocking {
        val article = ArticleEntity(
            userid = "user123",
            title = "Article to Delete",
            description = "Delete me",
            url = "https://example.com/delete",
            urlToImage = "https://example.com/image.jpg"
        )

        dao.insertArticle(article)
        dao.deleteArticle(article)

    }
}
