package com.marty.dang.polarpointsweatherapp.presentation.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.marty.dang.polarpointsweatherapp.MyApplication
import com.marty.dang.polarpointsweatherapp.R
import com.marty.dang.polarpointsweatherapp.databinding.HomeFragmentBinding
import com.marty.dang.polarpointsweatherapp.presentation.DailyWeatherViewModelFactory
import com.marty.dang.polarpointsweatherapp.presentation.viewmodel.DailyWeatherViewModel
import com.marty.dang.polarpointsweatherapp.utils.Constants
import java.util.*
import javax.inject.Inject


class DailyWeatherFrag : Fragment() {

    @Inject lateinit var viewModelFactory: DailyWeatherViewModelFactory
    private val viewModel by viewModels<DailyWeatherViewModel> { viewModelFactory }
    private lateinit var binding: HomeFragmentBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.home_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        getCurrentWeather()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == Constants.locationCode){
            getCurrentWeather()
        }
    }

    // set up view model
    private fun setupViewModel(){
        binding.viewModel = viewModel
        viewModel.iconTypeObservable.observe(viewLifecycleOwner, Observer {
            binding.weatherIcon = determineWeatherImage(it)
        })
    }

    // query view model to get current weather
    private fun getCurrentWeather(){
        if (ActivityCompat.checkSelfPermission(requireContext(), Constants.locationPermissionsArray[0]) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Constants.locationPermissionsArray[1]) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission()
        } else {
            //TODO: handle case where not able to get location
            val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).let { location ->
                val latitude = location?.latitude ?: 40.0
                val longitude = location?.longitude ?: -74.0
                viewModel.getWeather(latitude, longitude)
                getCurrentLocation(latitude, longitude)
            }
        }
    }

    // handling case if user denies permission access
    private fun requestLocationPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                Constants.locationPermissionsArray[0]) &&
            ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                Constants.locationPermissionsArray[1])){

            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.permission_dialog_title))
                .setMessage(getString(R.string.location_permission_explanation))
                .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                    requestPermissions(Constants.locationPermissionsArray, Constants.locationCode)
                }
                .setNegativeButton(getString(R.string.cancel)) { dialog, which -> dialog.dismiss() }
                .create().show()
        } else {
            requestPermissions(Constants.locationPermissionsArray, Constants.locationCode)
        }
    }

    // get address from lat and long
    private fun getCurrentLocation(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(requireActivity(), Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        binding.location = getString(R.string.home_frag_location_string, addresses[0].locality,addresses[0].adminArea)
    }

    // depending on weather description, change image shown
    private fun determineWeatherImage(weatherDescription: String): Drawable? {
        when(weatherDescription){
            "Thunderstorm" -> return ContextCompat.getDrawable(requireContext(), R.drawable.thunderstorm_icon)
            "Drizzle" -> return ContextCompat.getDrawable(requireContext(), R.drawable.rain_icon)
            "Rain" -> return ContextCompat.getDrawable(requireContext(), R.drawable.rain_icon)
            "Snow" -> return ContextCompat.getDrawable(requireContext(), R.drawable.snow_icon)
            "Clear" -> return ContextCompat.getDrawable(requireContext(), R.drawable.sun_icon)
            "Clouds" -> return ContextCompat.getDrawable(requireContext(), R.drawable.cloudy_icon)
        }
        return ContextCompat.getDrawable(requireContext(), R.drawable.cloudy_icon)
    }
}