package ru.kiloqky.wakeup.view.keep.adapters

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ru.kiloqky.wakeup.R
import ru.kiloqky.wakeup.rest.room.model.Keep
import ru.kiloqky.wakeup.view.keep.KeepFragment

class RecyclerKeepsAdapter(private val keepFragment: KeepFragment) :
    RecyclerView.Adapter<RecyclerKeepsAdapter.ViewHolder>() {

    var data: ArrayList<Keep> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_keeps, parent, false)
        return ViewHolder(view, data, keepFragment)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(
        itemView: View,
        data: ArrayList<Keep>,
        keepFragment: KeepFragment
    ) :
        RecyclerView.ViewHolder(itemView) {
        private val cardView = itemView.findViewById<CardView>(R.id.keepsView)
        val keepsBodyTextView = itemView.findViewById<TextView>(R.id.keepsBody)!!
        val keepsTitleTextView = itemView.findViewById<TextView>(R.id.keepsTitle)!!

        init {
            cardView.setOnLongClickListener {
                val builder = AlertDialog.Builder(itemView.context)
                builder.setTitle("delete this keep?")
                    .setMessage("You want to delete this keep?")
                    .setPositiveButton("Yes") { _: DialogInterface, _: Int ->
                        keepFragment.deleteKeep(data[adapterPosition])
                    }.setNegativeButton("No") { _: DialogInterface, _: Int -> }
                builder.show()
                return@setOnLongClickListener false
            }
            cardView.setOnClickListener {
                keepFragment.editKeep(data[adapterPosition])
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val keep = data[position]
        holder.keepsBodyTextView.text = keep.keepBody
        holder.keepsTitleTextView.text = keep.keepTitle
    }
}
