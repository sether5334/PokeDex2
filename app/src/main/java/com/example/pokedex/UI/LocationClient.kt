package com.example.pokedex.UI

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import android.Manifest
import android.content.Context.*
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService

class LocationClient (private val context: Context, private val locationCallback: (Location) -> Unit) {
    private var locationManager: LocationManager? = null
    private var locationListener: LocationListener? = null
    private var lastKnownLocation: Location? = null
    private var originalLocation: Location? = null

    init {
        locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                if (originalLocation == null) {
                    // Guarda la posición original y muestra un Toast
                    originalLocation = location
                    showToast("Posición original: Latitud: ${location.latitude}, Longitud: ${location.longitude}")
                } else if (lastKnownLocation == null || location.distanceTo(lastKnownLocation!!) >= 2.0f) {
                    // Verifica si la distancia entre la ubicación anterior y la nueva es mayor o igual a 10 metros, se cambiara el valor de  >= 2.0f a 10.0f
                    lastKnownLocation = location
                    showToast("Te has movido 10 metros desde tu posición original")
                    VibrationPhone()
                }
               // println("sigues en un parametro dentro delos 10 metros  Latitud: ${location.latitude}, Longitud: ${location.longitude}")
            }

            override fun onStatusChanged(
                provider: String?,
                status: Int,
                extras: android.os.Bundle?
            ) {
            }

            override fun onProviderEnabled(provider: String) {}

            override fun onProviderDisabled(provider: String) {
                showToast("El proveedor de ubicación está deshabilitado. Habilita la ubicación en el dispositivo.")
                stopLocationUpdates()
            }
        }
    }

    fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as AppCompatActivity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
        } else {
            locationManager?.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0f,
                locationListener!!
            )
        }
    }

    fun stopLocationUpdates() {
        locationManager?.removeUpdates(locationListener!!)
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    }

     private fun VibrationPhone(){
         val vibrato=context?.getSystemService(Context.VIBRATOR_SERVICE)as Vibrator
         if(Build.VERSION.SDK_INT>=26){
            vibrato.vibrate(VibrationEffect.createOneShot(1000,VibrationEffect.DEFAULT_AMPLITUDE))
         }else{
             vibrato.vibrate(1000)
         }


        }
    }

