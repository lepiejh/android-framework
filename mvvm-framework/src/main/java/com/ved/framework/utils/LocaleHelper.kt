package com.ved.framework.utils

import android.content.Context
import com.ved.framework.utils.LocaleHelper
import java.util.Locale
import android.util.Log
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.content.res.Resources

object LocaleHelper {
    private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"
    fun onAttach(context: Context): Context {
        val lang = getPersistedData(context, Locale.getDefault().language)
        return setLocale(context, lang)
    }

    fun onAttach(context: Context, defaultLanguage: String?): Context {
        val lang = getPersistedData(context, defaultLanguage)
        return setLocale(context, lang)
    }

    fun getLanguage(context: Context?): String? {
        val language = getPersistedData(context, Locale.getDefault().language)
        Log.d("", "Get Language: $language")
        return language
    }

    fun setLocale(context: Context, language: String?): Context {
        persist(context, language)
        return updateResourcesLegacy(context, language)
    }

    private fun persist(context: Context, language: String?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putString(SELECTED_LANGUAGE, language)
        editor.apply()
    }

    fun getPersistedData(context: Context?, defaultLanguage: String?): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage)
    }

    private fun updateResourcesLegacy(context: Context, language: String?): Context {
        val locale = language?.let { Locale(it) }
        if (locale != null) {
            Locale.setDefault(locale)
        }
        val resources = context.resources
        val configuration = resources.configuration
        if (language == "TW") {
            configuration.locale = Locale.TAIWAN
        } else {
            configuration.locale = locale
        }
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}