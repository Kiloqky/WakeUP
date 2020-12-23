package ru.kiloqky.wakeup.view.news.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.kiloqky.wakeup.R
import ru.kiloqky.wakeup.rest.retrofit.news.entitiesNews.Articles

class RecyclerNewsAdapter(val onItemClick: ((Articles) -> Unit)? = null) :
    RecyclerView.Adapter<RecyclerNewsAdapter.ViewHolder>() {

    var data: List<Articles> = emptyList()
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
            findViewById<TextView>(R.id.title).text = articles.title
            findViewById<TextView>(R.id.namePublisher).text = articles.source.name
            Picasso.get().load(articles.urlToImage)
                .into(findViewById<ImageView>(R.id.newsImage))
            setOnClickListener {
                onItemClick?.invoke(articles)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}