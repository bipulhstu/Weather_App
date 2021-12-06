package com.bipulhstu.weatherapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bipulhstu.weatherapp.repository.CityListRepository

class CityListViewModelFactory(
    private val cityListRepository: CityListRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CityListViewModel(cityListRepository) as T
    }
}