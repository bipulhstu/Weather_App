package com.bipulhstu.weatherapp.view.fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bipulhstu.weatherapp.R
import com.bipulhstu.weatherapp.databinding.FragmentMapsBinding
import com.bipulhstu.weatherapp.view.activity.MainActivity
import com.bipulhstu.weatherapp.viewModel.CityListViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.math.abs

class MapsFragment : Fragment() {
    lateinit var binding: FragmentMapsBinding
    lateinit var cityListViewModel: CityListViewModel
    private val args by navArgs<MapsFragmentArgs>()
    private val CHANNEL_ID = "channel_id_example"
    private val notificationId = 101
    var currentTemp = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = args.city.name


        createNotificationChannel()

        cityListViewModel =
            ViewModelProvider(
                this
            )[CityListViewModel::class.java]

        cityListViewModel.getCityWeather(
            args.city.coord!!.lat,
            args.city.coord!!.lon,
            "e384f9ac095b2109c751d95296f8ea76"
        )
        cityListViewModel.cityWeatherLiveData.observe(viewLifecycleOwner, { response ->
            Log.d("result", response.toString())

            binding.cityName.text = response.name
            binding.tempStatus.text = response.weather[0].main
            binding.temp.text =
                (abs(response.main.temp - 273.15).toInt()).toString() + "\u00B0" + "c"
            currentTemp = (abs(response.main.temp - 273.15).toInt()).toString() + "\u00B0" + "c"
            sendNotification()
            binding.humidity.text = "Humidity: " + response.main.humidity
            binding.windSpeed.text = "Wind Speed: " + response.wind.speed
            binding.maxTemp.text =
                "Max. Temp.: " + (abs(response.main.temp_max - 273.15).toInt()).toString() + "\u00B0" + "c"
            binding.minTemp.text =
                "Min. Temp.: " + (abs(response.main.temp_min - 273.15).toInt()).toString() + "\u00B0" + "c"

        })




        return binding.root
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "WeatherApp"
            val descriptionText = "Current Temperature: $currentTemp"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }

    private fun sendNotification() {
        val intent = Intent(requireContext(), MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(requireContext(), 0, intent, 0)
        val bitmap = BitmapFactory.decodeResource(
            requireContext().applicationContext.resources,
            R.drawable.icon
        )


        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("WeatherApp")
            .setContentText("Current Temperature: $currentTemp")
            .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
            .setLargeIcon(bitmap)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        with(NotificationManagerCompat.from(requireContext())) {
            notify(notificationId, builder.build())
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }


    private val callback = OnMapReadyCallback { googleMap ->
        val latLng = LatLng(args.city.coord!!.lat, args.city.coord!!.lon)
        googleMap.addMarker(MarkerOptions().position(latLng).title(args.city.name))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
    }
}