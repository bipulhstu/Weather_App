package com.bipulhstu.weatherapp.view.fragment

import android.os.Bundle
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
import com.bipulhstu.weatherapp.viewModel.CityListViewModel


class CityListFragment : Fragment() {
    lateinit var binding: FragmentCityListBinding
    lateinit var cityListViewModel: CityListViewModel
    lateinit var adapter: CityListAdapter
    lateinit var bundle: Bundle


    private var view2: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (view2 == null) {
            binding = FragmentCityListBinding.inflate(inflater, container, false)
            view2 = binding.root

            cityListViewModel = ViewModelProvider(this)[CityListViewModel::class.java]
            cityListViewModel.getCityList(23.68, 90.35, 50, "e384f9ac095b2109c751d95296f8ea76")

            cityListViewModel.cityListLiveData.observe(viewLifecycleOwner, { response ->
                adapter = CityListAdapter(response.list)
                binding.cityListRecyclerview.adapter = adapter
                binding.cityListRecyclerview.layoutManager =
                    LinearLayoutManager(requireContext())

                adapter.setOnItemClickListener(object :
                    CityListAdapter.OnItemClickListener {
                    override fun onItemClick(
                        city: City
                    ) {
                        bundle = Bundle()
                        //val b =  bun
                        bundle.putString("city_name", city.name)
                        val action =
                            CityListFragmentDirections.actionCityListFragmentToMapsFragment(
                                city
                            )
                        Navigation.findNavController(binding.root).navigate(action)
                    }
                })

            })
        }



        return view2 as View
    }


}