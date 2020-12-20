package ru.kiloqky.wakeup.view.weather.adapters

import android.app.AlertDialog
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.kiloqky.wakeup.R
import ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.forecast.entitiesOpenWeather.WeatherList
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RecyclerViewMoreAdapter(
    private val fullData: ArrayList<WeatherList>
) :
    RecyclerView.Adapter<RecyclerViewMoreAdapter.ViewHolder>() {
    private val data = ArrayList<WeatherList>()

    init {
        var i = 8
        while (i < fullData.size) {
            data.add(fullData[i])
            i += 8
        }
    }

    private val dateFormat: SimpleDateFormat = SimpleDateFormat("EEEE", Locale.getDefault())

    class ViewHolder(itemView: View, data: ArrayList<WeatherList>) :
        RecyclerView.ViewHolder(itemView) {
        val dateTV: TextView = itemView.findViewById(R.id.time)
        val tempTV: TextView = itemView.findViewById(R.id.temp_today)
        val iconTV: TextView = itemView.findViewById(R.id.icon_today)

        init {
            val weatherFont: Typeface =
                Typeface.createFromAsset(itemView.context.assets, "fonts/weather.ttf")
            iconTV.typeface = weatherFont
            itemView.setOnClickListener {
                val builder = AlertDialog.Builder(itemView.context)
                builder.setTitle(dateTV.text)
                val dataDialog =
                    ArrayList(data.subList(adapterPosition * 8, adapterPosition * 8 + 8))
                val recyclerView = RecyclerView(itemView.context)
                recyclerView.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                recyclerView.adapter = RecyclerViewTodayAdapter(dataDialog)
                recyclerView.addItemDecoration(
                    DividerItemDecoration(itemView.context, DividerItemDecoration.HORIZONTAL)
                )
                builder.setView(recyclerView)
                builder.show()
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_list_weather_more, parent, false
                )
        return ViewHolder(view, fullData)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.iconTV.text = getIcon(data[position].weatherRestModel[0].icon)
        holder.dateTV.text = (dateFormat.format(data[position].dt * 1000)).toString()
        val temp = String.format(
            Locale.getDefault(),
            "%.0f",
            data[position].mainRestModel.temp - 272
        ) + "\u2103"
        holder.tempTV.text = temp
    }

    override fun getItemCount(): Int {
        return 4
    }

    private fun getIcon(icon: String): String {
        when (icon) {
            "01d" -> return "\uF06E"
            "01n" -> return "\uF0A3"
            "02d" -> return "\uF002"
            "02n" -> return "\uF031"
            "03d" -> return "\uF041"
            "03n" -> return "\uF041"
            "04d" -> return "\uF013"
            "04n" -> return "\uF013"
            "09d" -> return "\uF015"
            "09n" -> return "\uF015"
            "10d" -> return "\uF009"
            "10n" -> return "\uF027"
            "11d" -> return "\uF005"
            "11n" -> return "\uF02C"
            "13d" -> return "\uF00A"
            "13n" -> return "\uF02A"
            "50d" -> return "\uF082"
            "50n" -> return "\uF082"
        }
        return "\uF075"
    }

}