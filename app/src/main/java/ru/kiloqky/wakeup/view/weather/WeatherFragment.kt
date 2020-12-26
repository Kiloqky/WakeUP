package ru.kiloqky.wakeup.view.weather

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.viewmodel.ext.android.viewModel
import ru.kiloqky.wakeup.R
import ru.kiloqky.wakeup.databinding.FragmentWeatherBinding
import ru.kiloqky.wakeup.view.weather.adapters.RecyclerViewMoreAdapter
import ru.kiloqky.wakeup.view.weather.adapters.RecyclerViewTodayAdapter
import ru.kiloqky.wakeup.viewmodels.WeatherViewModel

class WeatherFragment : Fragment(R.layout.fragment_weather) {

    private val weatherViewModel: WeatherViewModel by viewModel()
    private lateinit var weatherFont: Typeface
    private lateinit var binding: FragmentWeatherBinding
    private val recyclerViewTodayAdapter = RecyclerViewTodayAdapter()
    private val recyclerViewMoreAdapter = RecyclerViewMoreAdapter()

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
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initFont() {
        weatherFont = Typeface.createFromAsset(requireContext().assets, "fonts/weather.ttf")
        binding.iconMain.typeface = weatherFont
    }

    private fun initObservers() {
        weatherViewModel.cityName.observe(viewLifecycleOwner, {
            when (it.state) {
                WeatherViewModel.LoadState.LOADING -> {
                    binding.progressBar.visibility = ProgressBar.VISIBLE
                }
                WeatherViewModel.LoadState.SUCCESS -> {
                    binding.progressBar.visibility = ProgressBar.INVISIBLE
                    binding.cityName.text = it.data
                }
                WeatherViewModel.LoadState.ERROR -> {
                    binding.cityName.text = it.error
                }
            }
        })
        weatherViewModel.icon.observe(viewLifecycleOwner, {
            binding.iconMain.text = it
        })
        weatherViewModel.temp.observe(viewLifecycleOwner, {
            binding.tempMainNow.text = it
        })
        weatherViewModel.weather.observe(viewLifecycleOwner, {
            binding.weatherMain.text = it
        })
        weatherViewModel.feel.observe(viewLifecycleOwner, {
            binding.feel.text = it
        })
        weatherViewModel.recyclerViewToday.observe(viewLifecycleOwner, {
            recyclerViewTodayAdapter.data = it
        })
        weatherViewModel.recyclerViewMoreDays.observe(viewLifecycleOwner, {
            recyclerViewMoreAdapter.data = it
        })
    }

    private fun initViews() {
        binding.recyclerViewToday.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewMore.layoutManager =
            LinearLayoutManager(context)
        binding.recyclerViewMore.setHasFixedSize(true)
        binding.recyclerViewMore.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
        binding.recyclerViewToday.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
        )
        binding.recyclerViewToday.adapter = recyclerViewTodayAdapter
        binding.recyclerViewMore.adapter = recyclerViewMoreAdapter
    }
}