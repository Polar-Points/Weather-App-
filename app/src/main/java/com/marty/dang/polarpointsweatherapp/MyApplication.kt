package com.marty.dang.polarpointsweatherapp

import android.app.Application
import com.marty.dang.polarpointsweatherapp.di.AppComponent
import com.marty.dang.polarpointsweatherapp.di.DaggerAppComponent
import timber.log.Timber

/**
 *   Created by Marty Dang on 9/8/20
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
open class MyApplication: Application() {

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}