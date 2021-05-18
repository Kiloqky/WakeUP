package ru.kiloqky.wakeup.view.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import ru.kiloqky.wakeup.R
import ru.kiloqky.wakeup.databinding.FragmentNewsDetailsBinding
import ru.kiloqky.wakeup.helpers.launchWhenStarted
import ru.kiloqky.wakeup.rest.NewsData
import ru.kiloqky.wakeup.rest.retrofit.news.entitiesNews.Articles
import ru.kiloqky.wakeup.rest.room.news.model.News
import ru.kiloqky.wakeup.viewmodels.NewsViewModel

class NewsDetailsFragment : Fragment(R.layout.fragment_news_details) {
    private val newsViewModel: NewsViewModel by inject()
    private lateinit var binding: FragmentNewsDetailsBinding
    lateinit var articles: NewsData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.slide_right)
                .setDuration(100)
        initVM()
        binding.learnMore.setOnClickListener {
            if (articles is Articles) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse((articles as Articles).url))
                startActivity(intent)
            } else {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse((articles as News).url))
                startActivity(intent)
            }

        }
    }

    private fun initVM() {
        newsViewModel.broadcastNewsLD.onEach {
            articles = it
            if (articles is Articles) {
                binding.namePublisher.text = (articles as Articles).source?.name
                binding.title.text = (articles as Articles).title
                binding.description.text = (articles as Articles).description
                Picasso.get().load((articles as Articles).urlToImage).into(binding.newsImage)
            } else {
                binding.namePublisher.text = (articles as News).name
                binding.title.text = (articles as News).title
                binding.description.text = (articles as News).description
                Picasso.get().load((articles as News).urlToImage).into(binding.newsImage)
            }

        }.launchWhenStarted(lifecycleScope)
    }

}