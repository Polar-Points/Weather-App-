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
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.marty.dang.polarpointsweatherapp.Constants
import com.marty.dang.polarpointsweatherapp.R
import com.marty.dang.polarpointsweatherapp.presentation.viewmodel.HomeViewModel
import timber.log.Timber
import java.util.*


class HomeFrag : Fragment() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        getCurrentWeather()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.home_fragment, container, false)
        return view
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == Constants.locationCode){
            getCurrentWeather()
        }
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
        if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),Constants.locationPermissionsArray[0]) &&
            ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),Constants.locationPermissionsArray[1])){

            AlertDialog.Builder(requireContext())
                .setTitle(Constants.permissionDialogTitle)
                .setMessage(Constants.locationPermissionExplanation)
                .setPositiveButton(Constants.ok) { dialog, which ->
                    ActivityCompat.requestPermissions(requireActivity(), Constants.locationPermissionsArray, Constants.locationCode)
                }
                .setNegativeButton(Constants.cancel) { dialog, which -> dialog.dismiss() }
                .create().show()
        } else {
            ActivityCompat.requestPermissions(requireActivity(), Constants.locationPermissionsArray,1)
        }
    }

    // get current location from lat and long
    private fun getCurrentLocation(latitude: Double, longitude: Double): String{
        val geocoder = Geocoder(requireActivity(), Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        Timber.d("YOO %s", addresses)
        return addresses.toString()
    }
}