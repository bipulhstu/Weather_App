package com.bipulhstu.weatherapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bipulhstu.weatherapp.R
import com.bipulhstu.weatherapp.databinding.FragmentMapsBinding
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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = args.city.name


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
                (abs(response.main.temp - 273).toInt()).toString() + "\u00B0" + "c"
            binding.humidity.text = "Humidity: " + response.main.humidity
            binding.windSpeed.text = "Wind Speed: " + response.wind.speed
            binding.maxTemp.text =
                (abs(response.main.temp_max - 273).toInt()).toString() + "\u00B0" + "c"
            binding.minTemp.text =
                (abs(response.main.temp_min - 273).toInt()).toString() + "\u00B0" + "c"

        })




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