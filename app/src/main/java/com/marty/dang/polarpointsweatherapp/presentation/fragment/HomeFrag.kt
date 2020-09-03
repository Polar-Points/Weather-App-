package com.marty.dang.polarpointsweatherapp.presentation.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.marty.dang.polarpointsweatherapp.R
import com.marty.dang.polarpointsweatherapp.presentation.viewmodel.HomeViewModel
import com.marty.dang.polarpointsweatherapp.utils.Constants
import timber.log.Timber
import java.util.*


class HomeFrag : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var tempTextView: TextView
    private lateinit var weatherDescriptionTextView: TextView
    private lateinit var locationTextView: TextView
    private lateinit var lastTimeUpdatedTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.home_fragment, container, false)
        setUpUI(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCurrentWeather()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == Constants.locationCode){
            getCurrentWeather()
        }
    }

    private fun setUpUI(view: View){
        tempTextView = view.findViewById(R.id.homeFrag_temperature_text_view)
        weatherDescriptionTextView = view.findViewById(R.id.homeFrag_weather_description)
        locationTextView = view.findViewById(R.id.homeFrag_location)
        lastTimeUpdatedTextView = view.findViewById(R.id.homeFrag_last_time_updated)
    }

    // query view model to get current weather
    private fun getCurrentWeather(){
        if (ActivityCompat.checkSelfPermission(requireContext(), Constants.locationPermissionsArray[0]) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Constants.locationPermissionsArray[1]) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission()
        } else {
            val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).let { location ->
                // TODO: Use last known location if can't get current location
                val latitude = location?.latitude ?: 0.0
                val longitude = location?.longitude ?: 0.0
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
                    ActivityCompat.requestPermissions(requireActivity(), Constants.locationPermissionsArray, Constants.locationCode)
                }
                .setNegativeButton(getString(R.string.cancel)) { dialog, which -> dialog.dismiss() }
                .create().show()
        } else {
            ActivityCompat.requestPermissions(requireActivity(), Constants.locationPermissionsArray,
                Constants.locationCode)
        }
    }

    // get address from lat and long
    private fun getCurrentLocation(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(requireActivity(), Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        locationTextView.text = getString(R.string.home_frag_location_string, addresses[0].locality,addresses[0].adminArea)
    }
}