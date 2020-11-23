package com.marty.dang.polarpointsweatherapp.presentation.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.*
import com.marty.dang.polarpointsweatherapp.R


class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val darkModePrefs = findPreference<SwitchPreferenceCompat>("dark_mode_toggle")
        darkModePrefs?.isEnabled = false
        darkModePrefs?.onPreferenceChangeListener = this
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        enableDarkMode(newValue)
        return true
    }

    private fun enableDarkMode(value: Any?) {
        if(value == true){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

}