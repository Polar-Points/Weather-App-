package com.marty.dang.polarpointsweatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        getWeather()
    }

    private fun getWeather(){
        Timber.plant(Timber.DebugTree())
        Timber.d("Getting weather")
        viewModel.weather.observe(this, Observer {
            Timber.d("YO %s", it)
        })
    }
}