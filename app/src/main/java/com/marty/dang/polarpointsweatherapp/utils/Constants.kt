package com.marty.dang.polarpointsweatherapp.utils

import android.Manifest

/**
 *   Created by Marty Dang on 9/3/20
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
object Constants {

    // permissions
    val locationPermissionsArray = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    // request codes
    const val locationCode = 100
}