package ru.kiloqky.wakeup.view.news.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.kiloqky.wakeup.R
import ru.kiloqky.wakeup.rest.NewsData
import ru.kiloqky.wakeup.rest.retrofit.news.entitiesNews.Articles
import ru.kiloqky.wakeup.rest.room.news.model.News

class RecyclerNewsAdapter(val onItemClick: ((NewsData, FragmentNavigator.Extras) -> Unit)? = null) :
    RecyclerView.Adapter<RecyclerNewsAdapter.ViewHolder>() {

    var data: List<NewsData> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_news, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(articles: Articles) = with(itemView) {
            val titleTV = findViewById<TextView>(R.id.title)
            titleTV.text = articles.title
            val namePub = findViewById<TextView>(R.id.namePublisher)
            namePub.text = articles.source?.name
            val newsImage = findViewById<ImageView>(R.id.newsImage)
            Picasso.get().load(articles.urlToImage)
                .into(newsImage)

            setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    titleTV to "title",
                    namePub to "namePublisher",
                    newsImage to "newsImage"
                )
                onItemClick?.invoke(articles, extras)
            }
        }

        fun bind(articles: News) = with(itemView) {
            val titleTV = findViewById<TextView>(R.id.title)
            titleTV.text = articles.title
            val namePub = findViewById<TextView>(R.id.namePublisher)
            namePub.text = articles.name
            val newsImage = findViewById<ImageView>(R.id.newsImage)
            Picasso.get().load(articles.urlToImage)
                .into(newsImage)

            setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    titleTV to "title",
                    namePub to "namePublisher",
                    newsImage to "newsImage"
                )
                onItemClick?.invoke(articles, extras)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (data[position] is News) {
            holder.bind(data[position] as News)
        } else {
            holder.bind(data[position] as Articles)
        }
    }
}