package com.example.myapplication.data.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    application: Application,
    private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient
) : AndroidViewModel(application) {

    private val locationLiveData = LocationLiveData(application)

    private val _locationDetails = MutableLiveData<LocationDetails>()
    val locationDetails: LiveData<LocationDetails> = _locationDetails

    fun getLocationLiveData() = locationLiveData

    fun startLocationUpdates() {
        locationLiveData.startLocationUpdates()
    }

    fun fetchLocationDetails(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(getApplication(), Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            val locationName = if (!addresses.isNullOrEmpty()) {
                addresses[0].getAddressLine(0) ?: "Unknown location"
            } else {
                "Unknown location"
            }
            _locationDetails.postValue(LocationDetails(latitude, longitude, locationName))
        } catch (e: Exception) {
            e.printStackTrace()
            _locationDetails.postValue(LocationDetails(latitude, longitude, "Unknown location"))
        }
    }

    fun getDeviceLocation() {
        val context = getApplication<Application>().applicationContext

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                fetchLocationDetails(it.latitude, it.longitude)
            } ?: run {
                _locationDetails.postValue(LocationDetails(0.0, 0.0, "Location not available"))
            }
        }.addOnFailureListener {
            _locationDetails.postValue(LocationDetails(0.0, 0.0, "Failed to get location"))
        }
    }
}