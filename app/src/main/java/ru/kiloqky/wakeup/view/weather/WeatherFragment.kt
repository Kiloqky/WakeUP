package ru.kiloqky.wakeup.view.weather

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
import ru.kiloqky.wakeup.R
import ru.kiloqky.wakeup.databinding.FragmentWeatherBinding
import ru.kiloqky.wakeup.view.weather.adapters.RecyclerViewMoreAdapter
import ru.kiloqky.wakeup.view.weather.adapters.RecyclerViewTodayAdapter
import ru.kiloqky.wakeup.viewmodels.WeatherViewModel

class WeatherFragment : Fragment(R.layout.fragment_weather) {

    private val weatherViewModel: WeatherViewModel by viewModels({ requireActivity() })

    private lateinit var weatherFont: Typeface

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initFont()
        initObservers()
        weatherViewModel.refreshCity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initFont() {
        weatherFont = Typeface.createFromAsset(requireContext().assets, "fonts/weather.ttf")
        _binding!!.iconMain.typeface = weatherFont
    }

    private fun initObservers() {
        weatherViewModel.cityName.observe(viewLifecycleOwner, {
            when (it.state) {
                WeatherViewModel.LoadState.LOADING -> {
                    _binding!!.progressBar.visibility = ProgressBar.VISIBLE
                }
                WeatherViewModel.LoadState.SUCCESS -> {
                    _binding!!.progressBar.visibility = ProgressBar.INVISIBLE
                    _binding!!.cityName.text = it.data
                }
                WeatherViewModel.LoadState.ERROR -> {
                    _binding!!.cityName.text = it.error
                }
            }

        })
        weatherViewModel.icon.observe(viewLifecycleOwner, {
            _binding!!.iconMain.text = it
        })
        weatherViewModel.temp.observe(viewLifecycleOwner, {
            _binding!!.tempMainNow.text = it
        })
        weatherViewModel.weather.observe(viewLifecycleOwner, {
            _binding!!.weatherMain.text = it
        })
        weatherViewModel.feel.observe(viewLifecycleOwner, {
            _binding!!.feel.text = it
        })
        weatherViewModel.recyclerViewToday.observe(viewLifecycleOwner, {
            _binding!!.recyclerViewToday.adapter = RecyclerViewTodayAdapter(it)
        })
        weatherViewModel.recyclerViewMoreDays.observe(viewLifecycleOwner, {
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