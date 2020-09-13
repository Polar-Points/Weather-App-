package com.marty.dang.polarpointsweatherapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marty.dang.polarpointsweatherapp.R
import com.marty.dang.polarpointsweatherapp.data.repository.CurrentWeatherCache
import com.marty.dang.polarpointsweatherapp.data.repository.LocationDataRepository
import com.marty.dang.polarpointsweatherapp.data.repository.WeatherRepository
import com.marty.dang.polarpointsweatherapp.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

class DailyWeatherViewModel @Inject constructor(
    private val weatherRepo: WeatherRepository,
    private val locationRepo: LocationDataRepository,
    private val cache: CurrentWeatherCache) : ViewModel() {

    val tempObservable: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val weatherDescriptionObservable: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val requestMadeTimeObservable: MutableLiveData<String> by lazy {MutableLiveData<String>() }
    val iconObservable: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val locationObservable: MutableLiveData<String> by lazy {MutableLiveData<String>() }

    fun displayWeather() {
        // get latitude and longitude from location repo
        getCoordinatesFromLocationRepo().let {
            val latitude = it["latitude"] ?: 35.0
            val longitude = it["longitude"] ?: 35.0
            getWeather(latitude, longitude)
            locationObservable.postValue(locationRepo.convertCoordinatesToLocation(latitude, longitude))
        }
    }

    private fun getCoordinatesFromLocationRepo(): Map<String, Double> {
        return locationRepo.getCurrentLocationCoordinates()
    }

    private fun getWeather(latitude: Double, longitude: Double) {

        // if cache is empty make a network request
        if(cache.isCurrentWeatherCacheEmpty){
            getCurrentWeatherFromRepo(latitude, longitude)
        } else {
            // return stuff in cache
            val cacheValues = cache.getCurrentWeatherFromCache()
            tempObservable.postValue(cacheValues[Constants.cacheTempKey] + "\u2109")
            weatherDescriptionObservable.postValue(cacheValues[Constants.cacheWeatherDescriptionKey])
            requestMadeTimeObservable.postValue(getDate(cache.lastTimeAccessed,"dd/MM/yyyy hh:mm"))
            determineWeatherIcon(cacheValues[Constants.cacheWeatherIconKey] ?: "")

            // make a new network request if we need to refresh cache values
            if(cache.refreshInMilliseconds <= (System.currentTimeMillis() - cache.lastTimeAccessed)) {
                getCurrentWeatherFromRepo(latitude, longitude)
            }
        }
    }

    // get weather data from our repo
    private fun getCurrentWeatherFromRepo(latitude: Double, longitude: Double){
        viewModelScope.launch(Dispatchers.IO) {
            val currentWeatherModel = weatherRepo.getCurrentWeather(latitude, longitude)
            tempObservable.postValue(currentWeatherModel.current?.temp?.roundToInt().toString()+ "\u2109")
            weatherDescriptionObservable.postValue(currentWeatherModel.current?.weather?.get(0)?.description)
            requestMadeTimeObservable.postValue(getDate(System.currentTimeMillis(),"dd/MM/yyyy hh:mm"))
            determineWeatherIcon(currentWeatherModel.current?.weather?.get(0)?.main ?: "")

            val cacheValues = mutableMapOf<String,String>()
            cacheValues[Constants.cacheTempKey] = currentWeatherModel.current?.temp?.roundToInt().toString()
            cacheValues[Constants.cacheWeatherIconKey] = currentWeatherModel.current?.weather?.get(0)?.main ?: "Clouds"
            cacheValues[Constants.cacheWeatherDescriptionKey] = currentWeatherModel.current?.weather?.get(0)?.description ?: "NA"

            cache.saveCurrentWeatherToCache(cacheValues)
            cache.lastTimeAccessed = System.currentTimeMillis()
        }
    }

    // date util function which formats our date
    private fun getDate(milliSeconds: Long, dateFormat: String): String {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat, Locale.US)

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }

    private fun determineWeatherIcon(weatherDescription: String)  {
       val icon = when(weatherDescription){
            "Thunderstorm" -> R.drawable.thunderstorm_icon
            "Drizzle" -> R.drawable.rain_icon
            "Rain" -> R.drawable.rain_icon
            "Snow" -> R.drawable.snow_icon
            "Clear" -> R.drawable.sun_icon
            "Clouds" -> R.drawable.cloudy_icon
            else -> R.drawable.cloudy_icon
        }
        iconObservable.postValue(icon)
    }
}