package com.marty.dang.polarpointsweatherapp.presentation.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marty.dang.polarpointsweatherapp.R
import com.marty.dang.polarpointsweatherapp.presentation.viewmodel.WeatherDisplayViewModel

class WeatherDisplayFrag : Fragment() {

    private lateinit var viewModel: WeatherDisplayViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.weather_display_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WeatherDisplayViewModel::class.java)
    }
}