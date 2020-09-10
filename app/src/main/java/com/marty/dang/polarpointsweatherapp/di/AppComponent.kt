package com.marty.dang.polarpointsweatherapp.di

import android.content.Context
import com.marty.dang.polarpointsweatherapp.data.di.NetModule
import com.marty.dang.polarpointsweatherapp.presentation.fragment.DailyWeatherFrag
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 *   Created by Marty Dang on 9/8/20
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */

@Singleton
@Component
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
    fun inject(fragment: DailyWeatherFrag)
}