package ru.kiloqky.wakeup.view.news.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.kiloqky.wakeup.R
import ru.kiloqky.wakeup.rest.retrofit.news.entitiesNews.Articles
import ru.kiloqky.wakeup.view.news.NewsFragment

class RecyclerNewsAdapter(private val data: ArrayList<Articles>, private val newsFragment: NewsFragment) :
    RecyclerView.Adapter<RecyclerNewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_news, parent, false)
        return ViewHolder(view, data, newsFragment)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = data[position].title
        holder.namePublisher.text = data[position].source.name
        Picasso.get().load(data[position].urlToImage).into(holder.newsImage)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View, data: ArrayList<Articles>, newsFragment: NewsFragment) :
        RecyclerView.ViewHolder(itemView) {
        private val cardView: CardView = itemView.findViewById(R.id.cardView)
        val title: TextView = itemView.findViewById(R.id.title)
        val namePublisher: TextView = itemView.findViewById(R.id.namePublisher)
        val newsImage: ImageView = itemView.findViewById(R.id.newsImage)

        init {
            cardView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data[adapterPosition].url))
                newsFragment.startBrowser(intent)
            }
        }
    }

}