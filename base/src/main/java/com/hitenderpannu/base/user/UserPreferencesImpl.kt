package com.hitenderpannu.base.user

import android.content.Context
import com.hitenderpannu.common.domain.UserPreferences

class UserPreferencesImpl(private val context: Context) : UserPreferences {

    companion object {
        private const val USER_PREFERENCES_FILE = "userPreferences"

        // KEYS
        private const val USER_NAME = "userName";
        private const val USER_EMAIL = "userEmail";
        private const val USER_Id = "userId";
        private const val USER_TOKEN = "userToken";
    }

    private val preferences by lazy { context.getSharedPreferences(USER_PREFERENCES_FILE, Context.MODE_PRIVATE) }

    override var userName: String?
        get() = preferences.getString(USER_NAME, null)
        set(value) = preferences.edit().putString(USER_NAME, value).apply()

    override var userEmail: String?
        get() = preferences.getString(USER_EMAIL, null)
        set(value) = preferences.edit().putString(USER_EMAIL, value).apply()

    override var userId: String?
        get() = preferences.getString(USER_Id, null)
        set(value) = preferences.edit().putString(USER_Id, value).apply()

    override var userToken: String?
        get() = preferences.getString(USER_TOKEN, null)
        set(value) = preferences.edit().putString(USER_TOKEN, value).apply()
}
