package com.example.newsreaderapp.utils

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SecurePreferences(context: Context) {

    private val PREFS_NAME = "secure_prefs"

    private val sharedPrefs = EncryptedSharedPreferences.create(
        context,
        PREFS_NAME,
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveData(key: String, value: String) {
        sharedPrefs.edit()
            .putString(key, value)
            .apply()
    }

    fun getData(key: String): String? {
        return sharedPrefs.getString(key, null)
    }

    fun deleteData(key: String) {
        sharedPrefs.edit()
            .remove(key)
            .apply()
    }

    fun clearAllData() {
        sharedPrefs.edit().clear().apply()
    }
}
