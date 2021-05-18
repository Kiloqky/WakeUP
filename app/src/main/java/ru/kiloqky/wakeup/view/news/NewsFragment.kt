package ru.kiloqky.wakeup.view.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import ru.kiloqky.wakeup.R
import ru.kiloqky.wakeup.databinding.FragmentNewsBinding
import ru.kiloqky.wakeup.helpers.gone
import ru.kiloqky.wakeup.helpers.launchWhenStarted
import ru.kiloqky.wakeup.helpers.visible
import ru.kiloqky.wakeup.view.news.adapters.RecyclerNewsAdapter
import ru.kiloqky.wakeup.viewmodels.NewsViewModel

class NewsFragment : Fragment(R.layout.fragment_news) {

    private lateinit var binding: FragmentNewsBinding
    private val newsViewModel: NewsViewModel by inject()
    lateinit var adapter: RecyclerNewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsViewModel.fetchNews(true)
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
        binding.swipeRefreshLayout.setOnRefreshListener {
            newsViewModel.fetchNews(false)
            binding.progressBarNews.gone()
        }
        initVM()
    }

    private fun initVM() {
        newsViewModel.recyclerNews.onEach {
            when (it.state) {
                NewsViewModel.LoadState.LOADING -> {
                    if (!binding.swipeRefreshLayout.isRefreshing)
                        binding.progressBarNews.visible()
                    adapter.data
                }
                NewsViewModel.LoadState.SUCCESS -> {
                    binding.progressBarNews.gone()
                    adapter.data = it.data!!
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                NewsViewModel.LoadState.ERROR -> {
                    Toast.makeText(context, it.error, Toast.LENGTH_LONG).show()
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        }.launchWhenStarted(lifecycleScope)
    }
}
