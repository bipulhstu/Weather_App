package com.bipulhstu.weatherapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bipulhstu.weatherapp.adapter.CityListAdapter
import com.bipulhstu.weatherapp.databinding.FragmentCityListBinding
import com.bipulhstu.weatherapp.model.City
import com.bipulhstu.weatherapp.repository.CityListRepository
import com.bipulhstu.weatherapp.retrofit.ApiConfig
import com.bipulhstu.weatherapp.retrofit.RetrofitClient
import com.bipulhstu.weatherapp.viewModel.CityListViewModel
import com.bipulhstu.weatherapp.viewModel.CityListViewModelFactory


class CityListFragment : Fragment() {
    lateinit var binding: FragmentCityListBinding
    lateinit var cityListViewModel: CityListViewModel
    lateinit var adapter: CityListAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCityListBinding.inflate(inflater, container, false)

        val apiConfig = RetrofitClient.getInstance().create(ApiConfig::class.java)
        val cityListRepository = CityListRepository(apiConfig)

        cityListViewModel =
            ViewModelProvider(
                this,
                CityListViewModelFactory(cityListRepository)
            )[CityListViewModel::class.java]


        cityListViewModel.cityList.observe(viewLifecycleOwner, { response ->
            Log.d("result", response.toString())
            adapter = CityListAdapter(response.list)
            binding.cityListRecyclerview.adapter = adapter
            binding.cityListRecyclerview.layoutManager =
                LinearLayoutManager(requireContext())

            adapter.setOnItemClickListener(object :
                CityListAdapter.OnItemClickListener {
                override fun onItemClick(
                    city: City
                ) {
                    val action =
                        CityListFragmentDirections.actionCityListFragmentToMapsFragment(
                            city
                        )
                    Navigation.findNavController(binding.root).navigate(action)
                }
            })

        })

        return binding.root
    }


}