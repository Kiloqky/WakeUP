package ru.kiloqky.wakeup.view.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.inject
import ru.kiloqky.wakeup.R
import ru.kiloqky.wakeup.databinding.FragmentNewsBinding
import ru.kiloqky.wakeup.view.news.adapters.RecyclerNewsAdapter
import ru.kiloqky.wakeup.viewmodels.NewsViewModel

class NewsFragment : Fragment(R.layout.fragment_news) {

    private lateinit var binding: FragmentNewsBinding
    private val newsViewModel: NewsViewModel by inject()
    lateinit var adapter: RecyclerNewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        refreshData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerNews.layoutManager = LinearLayoutManager(context)
        adapter = RecyclerNewsAdapter { article, extras ->
            newsViewModel.broadcastNews(article)
            findNavController().navigate(R.id.action_nav_news_to_news_details, null, null, extras)
        }
        binding.recyclerNews.adapter = adapter
        initVM()
    }

    private fun initVM() {
        newsViewModel.recyclerNews.observe(viewLifecycleOwner, {
            when (it.state) {
                NewsViewModel.LoadState.LOADING -> {
                    binding.progressBarNews.visibility = ProgressBar.VISIBLE
                }
                NewsViewModel.LoadState.SUCCESS -> {
                    binding.progressBarNews.visibility = ProgressBar.INVISIBLE
                    adapter.data = it.data!!
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
}