package com.bipulhstu.weatherapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bipulhstu.weatherapp.R
import com.bipulhstu.weatherapp.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.math.abs

class MapsFragment : Fragment() {
    lateinit var binding: FragmentMapsBinding
    private val args by navArgs<MapsFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = args.city.name


        binding.cityName.text = args.city.name
        binding.tempStatus.text = args.city.weather?.get(0)!!.main
        binding.temp.text =
            (abs(args.city.main!!.temp - 273.15).toInt()).toString() + "\u00B0" + "c"

        binding.humidity.text = "Humidity: " + args.city.main!!.humidity
        binding.windSpeed.text = "Wind Speed: " + args.city.wind!!.speed
        binding.maxTemp.text =
            "Max. Temp.: " + (abs(args.city.main!!.temp_max - 273.15).toInt()).toString() + "\u00B0" + "c"
        binding.minTemp.text =
            "Min. Temp.: " + (abs(args.city.main!!.temp_min - 273.15).toInt()).toString() + "\u00B0" + "c"




        return binding.root
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