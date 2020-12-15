package ru.kiloqky.weather.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.kiloqky.weather.R
import ru.kiloqky.weather.databinding.FragmentGalleryBinding
import ru.kiloqky.weather.databinding.FragmentMainCityBinding
import ru.kiloqky.weather.viewmodels.GalleryViewModel

class GalleryFragment : Fragment(R.layout.fragment_gallery) {
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding
    private val galleryViewModel: GalleryViewModel by viewModels({ requireActivity() })

    private lateinit var tv1: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        initViews()
        initVM()
        refreshData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initVM() {
        galleryViewModel.location.observe(viewLifecycleOwner,
            {
                when (it.state) {
                    GalleryViewModel.LoadState.LOADING -> {

                    }
                    GalleryViewModel.LoadState.SUCCESS -> {
                        val location =
                            it.data?.location?.lat.toString() + it.data?.location?.lng.toString()
                        _binding!!.textGallery.text = location
                    }
                    GalleryViewModel.LoadState.ERROR -> {
                        _binding!!.textGallery.text = "Error"
                    }
                }
            })
    }

    private fun refreshData() {
        galleryViewModel.fetchGallery()
    }
}