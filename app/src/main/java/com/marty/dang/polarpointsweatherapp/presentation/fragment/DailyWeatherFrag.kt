package com.marty.dang.polarpointsweatherapp.presentation.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.marty.dang.polarpointsweatherapp.MyApplication
import com.marty.dang.polarpointsweatherapp.R
import com.marty.dang.polarpointsweatherapp.databinding.FragmentDailyWeatherBinding
import com.marty.dang.polarpointsweatherapp.presentation.viewmodel.DailyWeatherViewModelFactory
import com.marty.dang.polarpointsweatherapp.presentation.viewmodel.DailyWeatherViewModel
import com.marty.dang.polarpointsweatherapp.utils.Constants
import javax.inject.Inject


class DailyWeatherFrag : Fragment() {

    @Inject lateinit var viewModelFactory: DailyWeatherViewModelFactory
    private val viewModel by viewModels<DailyWeatherViewModel> { viewModelFactory }
    private lateinit var binding: FragmentDailyWeatherBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_daily_weather, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (ActivityCompat.checkSelfPermission(requireContext(), Constants.locationPermissionsArray[0]) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Constants.locationPermissionsArray[1]) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission()
        } else {
            viewModel.displayWeather()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == Constants.locationCode){
            // Checking whether user granted the permission or not.
            if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.displayWeather()
            }
            else {
                requestLocationPermission()
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
                .setPositiveButton(getString(R.string.ok)) { _, _ ->
                    requestPermissions(Constants.locationPermissionsArray, Constants.locationCode)
                }
                .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
                .create().show()
        } else {
            requestPermissions(Constants.locationPermissionsArray, Constants.locationCode)
        }
    }
}