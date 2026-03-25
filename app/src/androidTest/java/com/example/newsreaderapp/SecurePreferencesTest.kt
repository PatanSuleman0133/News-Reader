package com.example.newsreaderapp


import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.newsreaderapp.utils.SecurePreferences
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SecurePreferencesTest {
    private lateinit var securePreferences: SecurePreferences
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        securePreferences = SecurePreferences(context)
        securePreferences.clearAllData()
    }

    @Test
    fun testSaveAndRetrieveData() {
        securePreferences.saveData("email", "suleman@gmail.com")
        securePreferences.saveData("password", "123456789")

        val email = securePreferences.getData("email")
        val password = securePreferences.getData("password")

        assertEquals("suleman@gmail.com", email)
        assertEquals("123456789", password)
    }

    @Test
    fun testDeleteData() {
        securePreferences.saveData("email", "suleman@gmail.com")
        securePreferences.deleteData("email")

        val email = securePreferences.getData("email")
        assertNull(email)
    }

    @Test
    fun testClearAllData() {
        securePreferences.saveData("email", "suleman@gmail.com")
        securePreferences.saveData("password", "123456789")
        securePreferences.clearAllData()

        val email = securePreferences.getData("email")
        val password = securePreferences.getData("password")

        assertNull(email)
        assertNull(password)
    }
}
