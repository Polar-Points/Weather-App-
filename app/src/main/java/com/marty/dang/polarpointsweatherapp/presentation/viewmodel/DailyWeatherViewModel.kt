package com.marty.dang.polarpointsweatherapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marty.dang.polarpointsweatherapp.R
import com.marty.dang.polarpointsweatherapp.data.repository.LocationDataRepository
import com.marty.dang.polarpointsweatherapp.data.repository.WeatherRepository
import com.marty.dang.polarpointsweatherapp.presentation.model.DataSourceModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DailyWeatherViewModel @Inject constructor(
    private val weatherRepo: WeatherRepository,
    private val locationRepo: LocationDataRepository) : ViewModel() {

    val tempObservable: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val weatherDescriptionObservable: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val humidityObservable: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val precipObservable: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val windSpeedObservable: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    val iconObservable: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val dateObservable: MutableLiveData<String> by lazy {MutableLiveData<String>() }
    val locationObservable: MutableLiveData<String> by lazy {MutableLiveData<String>() }

    private lateinit var  dataSource: DataSourceModel

    fun displayWeather() {
        // get latitude and longitude from location repo
        getCoordinatesFromLocationRepo().let {
            val latitude = it["latitude"] ?: 35.0
            val longitude = it["longitude"] ?: 35.0
            getWeather(latitude, longitude)
            locationObservable.postValue(locationRepo.convertCoordinatesToLocation(latitude, longitude))
        }
    }

    fun displayNewHourlyWeather(tickValue: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherDescriptionObservable.postValue(dataSource.weatherDescriptionList[tickValue])
            tempObservable.postValue(dataSource.currentTempList[tickValue])
            humidityObservable.postValue(" Humidity: ${dataSource.humidityList[tickValue+1]}%")
            precipObservable.postValue("Precipitation: ${dataSource.precipitationList[tickValue]}%")
            windSpeedObservable.postValue("Wind: ${dataSource.windList[tickValue]} mph")
            dateObservable.postValue(dataSource.dateList[tickValue])
            determineWeatherIcon(dataSource.weatherDescriptionList[tickValue])
        }
    }

    private fun getWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            dataSource = weatherRepo.getCurrentWeather(latitude, longitude)
            weatherDescriptionObservable.postValue(dataSource.weatherDescriptionList[0])
            tempObservable.postValue(dataSource.currentTempList[0])
            humidityObservable.postValue(" Humidity: ${dataSource.humidityList[0]}%")
            precipObservable.postValue("Precipitation: ${dataSource.precipitationList[0]}%")
            windSpeedObservable.postValue("Wind: ${dataSource.windList[0]} mph")
            dateObservable.postValue(dataSource.dateList[0])
            determineWeatherIcon(dataSource.weatherDescriptionList[0])
        }
    }

    private fun getCoordinatesFromLocationRepo(): Map<String, Double> {
        return locationRepo.getCurrentLocationCoordinates()
    }

    private fun determineWeatherIcon(weatherDescription: String)  {

       val icon = when {
           "thunderstorm" in weatherDescription.toLowerCase() -> R.drawable.thunderstorm_icon
           "drizzle" in weatherDescription.toLowerCase() -> R.drawable.rain_icon
           "rain" in weatherDescription.toLowerCase() -> R.drawable.rain_icon
           "snow" in weatherDescription.toLowerCase() -> R.drawable.snow_icon
           "clear" in weatherDescription.toLowerCase() -> R.drawable.sun_icon
           else -> R.drawable.cloudy_icon
        }
        iconObservable.postValue(icon)
    }
}