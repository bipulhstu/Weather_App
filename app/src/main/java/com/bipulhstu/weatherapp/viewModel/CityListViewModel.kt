package com.bipulhstu.weatherapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bipulhstu.weatherapp.model.CityListResponse
import com.bipulhstu.weatherapp.repository.CityListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CityListViewModel(private val cityListRepository: CityListRepository) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            cityListRepository.getCityList(23.68, 90.35, 50, "e384f9ac095b2109c751d95296f8ea76")
        }
    }


    val cityList: LiveData<CityListResponse>
        get() = cityListRepository.cityList
}