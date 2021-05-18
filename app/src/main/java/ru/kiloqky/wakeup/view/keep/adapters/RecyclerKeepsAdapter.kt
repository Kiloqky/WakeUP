package ru.kiloqky.wakeup.view.keep.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.kiloqky.wakeup.R
import ru.kiloqky.wakeup.rest.room.keep.model.Keep

class RecyclerKeepsAdapter(
    val onItemClick: ((Keep) -> Unit)? = null,
    val onLongItemClick: ((Keep) -> Unit)? = null
) :
    RecyclerView.Adapter<RecyclerKeepsAdapter.ViewHolder>() {

    var data: List<Keep> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_keeps, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(keep: Keep) = with(itemView) {
            findViewById<TextView>(R.id.keepsBody).text = keep.keepBody
            findViewById<TextView>(R.id.keepsTitle).text = keep.keepTitle
            setOnLongClickListener {
                onLongItemClick?.invoke(keep)
                return@setOnLongClickListener false
            }
            setOnClickListener {
                onItemClick?.invoke(keep)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}
