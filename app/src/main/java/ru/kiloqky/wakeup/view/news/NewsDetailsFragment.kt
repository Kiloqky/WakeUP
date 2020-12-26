package ru.kiloqky.wakeup.view.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject
import ru.kiloqky.wakeup.R
import ru.kiloqky.wakeup.databinding.FragmentNewsDetailsBinding
import ru.kiloqky.wakeup.rest.retrofit.news.entitiesNews.Articles
import ru.kiloqky.wakeup.viewmodels.NewsViewModel

class NewsDetailsFragment : Fragment(R.layout.fragment_news_details) {
    private val newsViewModel: NewsViewModel by inject()
    private lateinit var binding: FragmentNewsDetailsBinding
    lateinit var articles: Articles

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
            TransitionInflater.from(context).inflateTransition(android.R.transition.explode)
                .setDuration(100)
        initVM()
        binding.learnMore.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(articles.url))
            startActivity(intent)
        }
    }

    private fun initVM() {
        newsViewModel.broadcastNewsLD.observe(viewLifecycleOwner, {
            articles = it
            binding.namePublisher.text = it.source.name
            binding.title.text = it.title
            binding.description.text = it.description
            Picasso.get().load(it.urlToImage).into(binding.newsImage)
        })
    }

}