package com.bipulhstu.weatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bipulhstu.weatherapp.databinding.CityItemLayoutBinding
import com.bipulhstu.weatherapp.model.City
import kotlin.math.abs

class CityListAdapter(
    var cityList: List<City>
) :
    RecyclerView.Adapter<CityListAdapter.ViewHolder>() {
    private lateinit var clickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(
            city: City
        )
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        clickListener = listener
    }

    inner class ViewHolder(val binding: CityItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CityItemLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(cityList[position]) {

                binding.cityName.text = this.name
                binding.status.text = this.weather?.get(0)?.main
                binding.temperature.text =
                    (abs(this.main!!.temp - 273).toInt()).toString() + "\u00B0"+"c"


                binding.mainCL.setOnClickListener {
                    clickListener.onItemClick(
                        this
                    )
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return cityList.size
    }
}