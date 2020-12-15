package ru.kiloqky.weather.ui.mainCity

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.kiloqky.weather.R
import ru.kiloqky.weather.databinding.FragmentMainCityBinding
import ru.kiloqky.weather.ui.mainCity.adapters.RecyclerViewMoreAdapter
import ru.kiloqky.weather.ui.mainCity.adapters.RecyclerViewTodayAdapter
import ru.kiloqky.weather.viewmodels.MainCityViewModel

class MainCityFragment : Fragment(R.layout.fragment_main_city) {

    private val mainCityViewModel: MainCityViewModel by viewModels({ requireActivity() })

    private lateinit var weatherFont: Typeface

    private var _binding: FragmentMainCityBinding? = null
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initFont()
        initObservers()
        mainCityViewModel.refreshCity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainCityBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initFont() {
        weatherFont = Typeface.createFromAsset(requireContext().assets, "fonts/weather.ttf")
        _binding!!.iconMain.typeface = weatherFont
//        _binding!!.wind.typeface = weatherFont
//        _binding!!.humidity.typeface = weatherFont
//        _binding!!.pressure.typeface = weatherFont
    }

    private fun initObservers() {
        mainCityViewModel.cityName.observe(viewLifecycleOwner, {
            when (it.state) {
                MainCityViewModel.LoadState.LOADING -> {
                    _binding!!.progressBar.visibility = ProgressBar.VISIBLE
                }
                MainCityViewModel.LoadState.SUCCESS -> {
                    _binding!!.progressBar.visibility = ProgressBar.INVISIBLE
                    _binding!!.cityName.text = it.data
                }
                MainCityViewModel.LoadState.ERROR -> {
                    _binding!!.cityName.text = "error"
                }
            }

        })
        mainCityViewModel.icon.observe(viewLifecycleOwner, {
            _binding!!.iconMain.text = it
        })
        mainCityViewModel.temp.observe(viewLifecycleOwner, {
            _binding!!.tempMainNow.text = it
        })
        mainCityViewModel.weather.observe(viewLifecycleOwner, {
            _binding!!.weatherMain.text = it
        })
        mainCityViewModel.feel.observe(viewLifecycleOwner, {
            _binding!!.feel.text = it
        })
//        mainCityViewModel.wind.observe(viewLifecycleOwner, {
//            _binding!!.wind.text = it
//        })
//        mainCityViewModel.pressure.observe(viewLifecycleOwner, {
//            _binding!!.pressure.text = it
//        })
//        mainCityViewModel.humidity.observe(viewLifecycleOwner, {
//            _binding!!.humidity.text = it
//        })
        mainCityViewModel.recyclerViewToday.observe(viewLifecycleOwner, {
            _binding!!.recyclerViewToday.adapter = RecyclerViewTodayAdapter(it)
        })
        mainCityViewModel.recyclerViewMoreDays.observe(viewLifecycleOwner, {
            _binding!!.recyclerViewMore.adapter = RecyclerViewMoreAdapter(it)
        })
    }

    private fun initViews() {
        _binding!!.recyclerViewToday.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        _binding!!.recyclerViewMore.layoutManager =
            LinearLayoutManager(context)
        _binding!!.recyclerViewMore.setHasFixedSize(true)
        _binding!!.recyclerViewMore.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
        _binding!!.recyclerViewToday.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
        )


    }
}