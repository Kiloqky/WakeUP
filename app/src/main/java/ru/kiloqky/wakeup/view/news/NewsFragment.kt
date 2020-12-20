package ru.kiloqky.wakeup.view.news

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ru.kiloqky.wakeup.R
import ru.kiloqky.wakeup.databinding.FragmentNewsBinding
import ru.kiloqky.wakeup.view.news.adapters.RecyclerNewsAdapter
import ru.kiloqky.wakeup.viewmodels.NewsViewModel

class NewsFragment : Fragment(R.layout.fragment_news) {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding
    private val newsViewModel: NewsViewModel by viewModels({ requireActivity() })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        refreshData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVM()
    }

    fun startBrowser(intent: Intent) {
        startActivity(intent)
    }

    private fun initVM() {
        newsViewModel.recyclerNews.observe(viewLifecycleOwner, {
            when (it.state) {
                NewsViewModel.LoadState.LOADING -> {
                    _binding!!.progressBarNews.visibility = ProgressBar.VISIBLE
                }
                NewsViewModel.LoadState.SUCCESS -> {
                    _binding!!.progressBarNews.visibility = ProgressBar.INVISIBLE
                    _binding!!.recyclerNews.layoutManager = LinearLayoutManager(context)
                    _binding!!.recyclerNews.adapter = RecyclerNewsAdapter(it.data!!, this)
                }
                NewsViewModel.LoadState.ERROR -> {
                    Toast.makeText(context, it.error, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun refreshData() {
        newsViewModel.fetchNews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}